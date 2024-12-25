package com.example.e_commerce.repository

import com.example.e_commerce.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getProducts(): Flow<List<Product>> = callbackFlow {
        val subscription = firestore.collection("products")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    trySend(emptyList())  // if error occurs
                } else {
                    val products = snapshot?.documents?.map { doc ->
                        Product(
                            id = doc.id,
                            name = doc.getString("name") ?: "",
                            price = (doc.get("price") as? Number)?.toDouble() ?: 0.0,
                            imageUrl = doc.getString("imageUrl") ?: "",
                            description = doc.getString("description") ?: ""
                        )
                    } ?: emptyList()


                    trySend(products)
                }
            }
        awaitClose { subscription.remove() }
    }
}
