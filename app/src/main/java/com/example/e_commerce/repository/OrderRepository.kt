package com.example.e_commerce.repository

import com.example.e_commerce.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OrderRepository(private val firestore: FirebaseFirestore) {

    private val ordersCollection = firestore.collection("orders")

    suspend fun placeOrder(order: Order): Boolean {
        return try {
            ordersCollection.document(order.orderId).set(order).await()
            true // Order placed successfully
        } catch (e: Exception) {
            e.printStackTrace()
            false // Order placement failed
        }
    }
}

