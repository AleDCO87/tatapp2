package com.example.tatapp.ui.screens.detalleProducto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tatapp.core.ui.UiState
import com.example.tatapp.data.repository.CatalogRepository
import com.example.tatapp.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetalleNetworkViewModel(
    private val repo: CatalogRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Product>>(UiState.Idle)
    val state: StateFlow<UiState<Product>> = _state

    fun loadProduct(id: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            runCatching { repo.getProduct(id) }
                .onSuccess { product ->
                    _state.value = UiState.Success<Product>(product)
                }
                .onFailure { e ->
                    _state.value = UiState.Error(e.message ?: "Error al cargar producto")
                }
        }
    }
}

class DetalleNetworkVMFactory(
    private val repo: CatalogRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetalleNetworkViewModel(repo) as T
}
