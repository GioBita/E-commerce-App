
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce.model.Order
import com.example.e_commerce.navigation.BottomNavBar
import com.example.e_commerce.repository.OrderRepository
import com.example.e_commerce.repository.ProductRepository
import com.example.e_commerce.ui.theme.screens.CartScreen
import com.example.e_commerce.ui.theme.screens.OrderSuccessScreen
import com.example.e_commerce.ui.theme.screens.OrderSummaryScreen
import com.example.e_commerce.ui.theme.screens.ProductDetailScreen
import com.example.e_commerce.ui.theme.screens.ProductListScreen
import com.example.e_commerce.ui.theme.screens.ProfileScreen
import com.example.e_commerce.ui.theme.screens.SignInScreen
import com.example.e_commerce.ui.theme.screens.SignUpScreen
import com.example.e_commerce.viewmodel.AuthViewModel
import com.example.e_commerce.viewmodel.AuthViewModelFactory
import com.example.e_commerce.viewmodel.CartViewModel
import com.example.e_commerce.viewmodel.OrderViewModel
import com.example.e_commerce.viewmodel.OrderViewModelFactory
import com.example.e_commerce.viewmodel.ProductViewModel
import com.example.e_commerce.viewmodel.ProductViewModelFactory
import com.example.e_commerce.viewmodel.ProfileViewModel
import com.example.e_commerce.viewmodel.ProfileViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()
    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val productRepository = ProductRepository(firestore)
    val cartViewModel: CartViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route !in listOf(
                    "sign_in", "sign_up"
                )
            ) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "sign_in",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Sign In Screen
            composable("sign_in") {
                val authViewModel: AuthViewModel = viewModel(
                    factory = AuthViewModelFactory(firebaseAuth)
                )
                SignInScreen(
                    authViewModel = authViewModel,
                    onSignInSuccess = {
                        navController.navigate("product_list") {
                            popUpTo("sign_in") { inclusive = true }
                        }
                    },
                    onSignUpClick = { navController.navigate("sign_up") }
                )
            }

            // Sign Up Screen
            composable("sign_up") {
                val authViewModel: AuthViewModel = viewModel(
                    factory = AuthViewModelFactory(firebaseAuth)
                )
                SignUpScreen(
                    authViewModel = authViewModel,
                    onSignUpSuccess = {
                        navController.navigate("sign_in") {
                            popUpTo("sign_up") { inclusive = true }
                        }
                    }
                )
            }

            // Product List Screen
            composable("product_list") {
                val productViewModel: ProductViewModel = viewModel(
                    factory = ProductViewModelFactory(productRepository)
                )
                ProductListScreen(
                    viewModel = productViewModel,
                    cartViewModel = cartViewModel,
                    navController = navController
                )
            }

            // Product Detail Screen
            composable("product_detail/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                ProductDetailScreen(
                    productId = productId,
                    navController = navController,
                    cartViewModel = cartViewModel
                )
            }

            // Cart Screen
            composable("cart") {
                CartScreen(
                    viewModel = cartViewModel,
                    navController = navController
                )
            }

            // Profile Screen
            composable("profile") {
                ProfileScreen(
                    onSignOut = {
                        firebaseAuth.signOut()
                        navController.navigate("sign_in") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }
                )
            }

            // Order Summary Screen
            composable("order_summary") {
                val cartItems = cartViewModel.cartItems.collectAsState(initial = emptyList()).value
                val totalPrice = cartViewModel.totalPrice.collectAsState(initial = 0.0).value
                val orderRepository = OrderRepository(firestore)
                val orderViewModel: OrderViewModel = viewModel(factory = OrderViewModelFactory(orderRepository))

                val orderSuccess by orderViewModel.orderSuccess.collectAsState()

                // Navigate to OrderSuccessScreen when orderSuccess becomes true
                if (orderSuccess == true) {
                    navController.navigate("order_success") {
                        popUpTo("order_summary") { inclusive = true }
                    }
                }

                OrderSummaryScreen(
                    cartItems = cartItems,
                    totalPrice = totalPrice,
                    onPlaceOrder = {
                        val order = Order(
                            orderId = System.currentTimeMillis().toString(),
                            items = cartItems,
                            totalPrice = totalPrice,
                            userId = firebaseAuth.currentUser?.uid ?: ""
                        )
                        orderViewModel.placeOrder(order)
                    },
                    orderSuccess = orderSuccess,
                    navController = navController
                )
            }

            // Order Success Screen
            composable("order_success") {
                OrderSuccessScreen(
                    viewModel = cartViewModel,
                    navController = navController
                )
            }
            composable("profile") {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                val profileViewModel: ProfileViewModel = viewModel(
                    factory = ProfileViewModelFactory(FirebaseFirestore.getInstance())
                )

                // Fetch user stats when entering the profile screen
                LaunchedEffect(Unit) {
                    profileViewModel.fetchUserStats(userId)
                }

                // Collect the state for items purchased and browsed
                val itemsPurchased by profileViewModel.itemsPurchased.collectAsState()
                val itemsBrowsed by profileViewModel.itemsBrowsed.collectAsState()

                ProfileScreen(
                    onSignOut = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("sign_in") {
                            popUpTo("profile") { inclusive = true }
                        }
                    },
                    itemsPurchased = itemsPurchased,
                    itemsBrowsed = itemsBrowsed
                )
            }

        }
    }
}


