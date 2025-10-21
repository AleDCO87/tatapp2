package com.example.tatapp.ui.screens.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tatapp.core.net.toUserMessage
import com.example.tatapp.core.ui.UiState
import com.example.tatapp.data.repository.CartRepository
import com.example.tatapp.domain.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartNetworkViewModel(
    private val repo: CartRepository
) : ViewModel() {

    private val _listState = MutableStateFlow<UiState<List<CartItem>>>(UiState.Idle)
    val listState: StateFlow<UiState<List<CartItem>>> = _listState

    private val _actionState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val actionState: StateFlow<UiState<Unit>> = _actionState

    fun loadCart() {
        viewModelScope.launch {
            _listState.value = UiState.Loading
            runCatching { repo.getItems() }
                .onSuccess { _listState.value = UiState.Success(it) }
                .onFailure { _listState.value = UiState.Error(it.toUserMessage()) }
        }
    }

    fun addToCart(productId: String, qty: Int) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            runCatching { repo.addItem(productId, qty) }
                .onSuccess {
                    _actionState.value = UiState.Success(Unit)
                    loadCart() // refresca listado
                }
                .onFailure { _actionState.value = UiState.Error(it.toUserMessage()) }
        }
    }
}

class CartNetworkVMFactory(
    private val repo: CartRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartNetworkViewModel(repo) as T
    }
}
