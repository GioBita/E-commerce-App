package com.example.e_commerce.viewmodel

import androidx.lifecycle.ViewModel
import com.example.e_commerce.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    private val _orderSuccess = MutableStateFlow<Boolean?>(null)
    val orderSuccess: StateFlow<Boolean?> = _orderSuccess

    private val _userMessage = MutableStateFlow("")
    val userMessage: StateFlow<String> get() = _userMessage

    fun calculateTotalPrice() {
        _totalPrice.value = _cartItems.value.sumOf { it.price }
    }

    fun addToCart(product: Product) {
        _cartItems.value += product
        calculateTotalPrice()
    }

    fun removeFromCart(product: Product) {
        _cartItems.value -= product
        calculateTotalPrice()
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        calculateTotalPrice()
    }

    fun placeOrder() {
        if (_cartItems.value.isNotEmpty()) {
            _orderSuccess.value = true
        } else {
            _orderSuccess.value = false
        }
    }

    fun resetOrderStatus() {
        _orderSuccess.value = null
    }

    fun handleContinueShopping() {
        clearCart()
        clearMessage()
    }

    fun showEmptyCartMessage() {
        _userMessage.value = "Your cart is empty! Add some products to proceed."
    }

    fun clearMessage() {
        _userMessage.value = ""
    }
}
