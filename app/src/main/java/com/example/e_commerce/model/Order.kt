package com.example.e_commerce.model

import com.google.firebase.Timestamp

data class Order(
    val orderId: String = "",
    val userId: String = "",
    val totalPrice: Double = 0.0,
    val items: List<Product> = emptyList(),
    val timestamp: Timestamp = Timestamp.now()
)


