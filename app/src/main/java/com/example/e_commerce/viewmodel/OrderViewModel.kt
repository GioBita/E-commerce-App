package com.example.e_commerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.model.Order
import com.example.e_commerce.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _orderSuccess = MutableStateFlow<Boolean?>(null)
    val orderSuccess: StateFlow<Boolean?> get() = _orderSuccess

    fun placeOrder(order: Order) {
        viewModelScope.launch {
            val success = orderRepository.placeOrder(order)
            _orderSuccess.value = success
        }
    }
}


