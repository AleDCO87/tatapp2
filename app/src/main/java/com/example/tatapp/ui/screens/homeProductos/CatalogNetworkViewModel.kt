package com.example.tatapp.ui.screens.homeProductos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tatapp.core.ui.UiState
import com.example.tatapp.data.repository.CatalogRepository
import com.example.tatapp.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatalogNetworkViewModel(
    private val repo: CatalogRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Product>>>(UiState.Idle)
    val state: StateFlow<UiState<List<Product>>> = _state

    fun loadCatalog() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            runCatching { repo.getCatalog() }
                .onSuccess { _state.value = UiState.Success(it) }
                .onFailure { e -> _state.value = UiState.Error(e.message ?: "Error cargando catálogo") }
        }
    }
}

class CatalogNetworkVMFactory(
    private val repo: CatalogRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatalogNetworkViewModel(repo) as T
}
