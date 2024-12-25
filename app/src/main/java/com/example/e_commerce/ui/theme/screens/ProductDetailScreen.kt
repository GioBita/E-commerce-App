package com.example.e_commerce.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.e_commerce.model.Product
import com.example.e_commerce.viewmodel.CartViewModel

@Composable
fun ProductDetailScreen(
    productId: String,
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val product = fetchProductDetails(productId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Image Section
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 16.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            )

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Product Price
            Text(
                text = "Price: \$${product.price}",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Description
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 8.dp),
                lineHeight = 20.sp
            )
        }

        // Add to Cart Button
        Button(
            onClick = {
                cartViewModel.addToCart(product)
                navController.navigate("cart")
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Add to Cart",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )
        }
    }
}

// Mock Product Details
fun fetchProductDetails(productId: String): Product {
    return when (productId) {
        "rtx3050" -> Product(
            id = productId,
            name = "RTX 3050",
            price = 250.0,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734629876/RTX3050_alvug4.jpg",
            description = "The NVIDIA RTX 3050 is a powerful graphics card designed for modern gaming and multimedia tasks. Experience next-gen performance in a compact design."
        )
        "rtx3060" -> Product(
            id = productId,
            name = "RTX 3060",
            price = 350.0,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734633153/RTX3060_f4tbnw.jpg",
            description = "The NVIDIA RTX 3060 offers unmatched performance and stunning visuals. Ideal for gaming, content creation, and high-end computing."
        )
        "ryzen5600x" -> Product(
            id = productId,
            name = "AMD Ryzen 5 5600X",
            price = 199.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734971556/Processor_AMD_CPU_-_Ryzen_5-5600X__6_Cores__3.7_GHz__1__b9y4bf.jpg",
            description = "A 6-core, 12-thread processor with exceptional gaming and productivity performance, featuring a base clock of 3.7 GHz and boost up to 4.6 GHz."
        )
        "intel12700k" -> Product(
            id = productId,
            name = "Intel Core i7-12700K",
            price = 349.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734971633/intel-core-i7-12700k-5-00-ghz_1-515x515_klhunw.jpg",
            description = "12-core, 20-thread powerhouse built for gaming and multitasking, with a max turbo frequency of 5.0 GHz."
        )
        "rtx3080ti" -> Product(
            id = productId,
            name = "NVIDIA RTX 3080 Ti",
            price = 999.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734971806/81UBILsWwiS._AC_UF894_1000_QL80__fsbz51.jpg",
            description = "The ultimate gaming GPU for 4K resolution and ray-traced performance, packed with 12GB GDDR6X memory."
        )
        "radeon6800xt" -> Product(
            id = productId,
            name = "AMD Radeon RX 6800 XT",
            price = 649.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734971990/pexels-vladimirsrajber-19228384_f4sbqn.jpg",
            description = "Delivers stunning visuals and performance with 16GB GDDR6 memory for 4K gaming and VR experiences."
        )
        "msib550" -> Product(
            id = productId,
            name = "MSI MPG B550 Gaming Edge WiFi",
            price = 189.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734974151/MSI_B550-A_PRO_forubs.jpg",
            description = "ATX motherboard supporting Ryzen 5000 CPUs, PCIe 4.0, Wi-Fi 6, and Mystic Light RGB."
        )
        "asusz690" -> Product(
            id = productId,
            name = "ASUS ROG Strix Z690-E Gaming WiFi",
            price = 399.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734974339/ROG-Strix-Z690-E-Gaming_jta2fa.png",
            description = "Premium motherboard for Intel 12th Gen CPUs with DDR5 support, advanced cooling, and Wi-Fi 6E."
        )
        "corsairVengeance" -> Product(
            id = productId,
            name = "Corsair Vengeance RGB Pro 16GB DDR4 3200MHz",
            price = 89.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734974642/Vengeance_RGB_Pro_PDP_Black_04_q4wxbu.avif",
            description = "High-performance RAM with customizable RGB lighting for gamers and PC builders."
        )
        "gskillTridentZ5" -> Product(
            id = productId,
            name = "G.Skill Trident Z5 RGB 32GB DDR5 6000MHz",
            price = 279.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734975005/g_skill_qlfmww.jpg",
            description = "DDR5 memory designed for the latest Intel and AMD platforms with stunning RGB effects."
        )
        "samsung980pro" -> Product(
            id = productId,
            name = "Samsung 980 Pro 1TB NVMe SSD",
            price = 149.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734975329/980_pro_ssd_fgi9pu.jpg",
            description = "Ultra-fast NVMe SSD with PCIe 4.0 support and sequential read speeds up to 7,000 MB/s."
        )
        "crucialMX500" -> Product(
            id = productId,
            name = "Crucial MX500 2TB SATA SSD",
            price = 129.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v123456789/crucial_mx500.jpg",
            description = "Reliable and affordable SSD with sequential read speeds of up to 560 MB/s for everyday computing."
        )
        "evga850G5" -> Product(
            id = productId,
            name = "EVGA SuperNOVA 850 G5 (80+ Gold)",
            price = 149.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734975651/220-GA-0850-X1_LG_1_pv88ei.png",
            description = "Fully modular PSU with an 80+ Gold rating, offering reliable power delivery and efficiency."
        )
        "corsairRM750" -> Product(
            id = productId,
            name = "Corsair RM750 (80+ Gold)",
            price = 119.99,
            imageUrl = "https://res.cloudinary.com/domqfnvb8/image/upload/v1734975848/CP-9020179-NA-RM750x-PSU-01_zqqa8h.avif",
            description = "Quiet and efficient PSU designed for gaming builds with zero RPM fan mode."
        )
        else -> Product(
            id = "unknown",
            name = "Unknown Product",
            price = 0.0,
            imageUrl = "",
            description = "No description available for this product."
        )
    }
}
