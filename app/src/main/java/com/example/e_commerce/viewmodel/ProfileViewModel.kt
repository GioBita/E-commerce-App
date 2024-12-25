package com.example.e_commerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val firestore: FirebaseFirestore) : ViewModel() {
    private val _itemsPurchased = MutableStateFlow(0)
    val itemsPurchased: StateFlow<Int> = _itemsPurchased

    private val _itemsBrowsed = MutableStateFlow(0)
    val itemsBrowsed: StateFlow<Int> = _itemsBrowsed

    fun fetchUserStats(userId: String) {
        viewModelScope.launch {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        _itemsPurchased.value = document.getLong("itemsPurchased")?.toInt() ?: 0
                        _itemsBrowsed.value = document.getLong("itemsBrowsed")?.toInt() ?: 0
                    } else {

                        initializeUserStats(userId)
                    }
                }
                .addOnFailureListener { exception ->

                    exception.printStackTrace()
                }
        }
    }

    private fun initializeUserStats(userId: String) {
        val initialData = mapOf(
            "itemsPurchased" to 0,
            "itemsBrowsed" to 0
        )

        firestore.collection("users").document(userId).set(initialData)
            .addOnSuccessListener {
                _itemsPurchased.value = 0
                _itemsBrowsed.value = 0
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
    fun updateItemsPurchased(userId: String, additionalItems: Int) {
        firestore.collection("users").document(userId)
            .update("itemsPurchased", FieldValue.increment(additionalItems.toLong()))
            .addOnSuccessListener {

            }
            .addOnFailureListener { exception ->

                exception.printStackTrace()
            }
    }

}
