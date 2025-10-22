package com.example.tatapp.ui.screens.homeProductos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatapp.core.ui.UiState
import com.example.tatapp.data.repository.CatalogRepository
import com.example.tatapp.domain.model.Category
import com.example.tatapp.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatalogHomeViewModel(
    private val repo: CatalogRepository
) : ViewModel() {

    private val _products = MutableStateFlow<UiState<List<Product>>>(UiState.Idle)
    val products: StateFlow<UiState<List<Product>>> = _products

    private val _categories = MutableStateFlow<UiState<List<Category>>>(UiState.Idle)
    val categories: StateFlow<UiState<List<Category>>> = _categories

    fun loadAll() {
        // Productos
        viewModelScope.launch {
            _products.value = UiState.Loading
            runCatching { repo.getCatalog() }
                .onSuccess { _products.value = UiState.Success(it) }
                .onFailure { _products.value = UiState.Error(it.message ?: "Error productos") }
        }
        // Categorías
        viewModelScope.launch {
            _categories.value = UiState.Loading
            runCatching { repo.getCategories() }
                .onSuccess { _categories.value = UiState.Success(it) }
                .onFailure { _categories.value = UiState.Error(it.message ?: "Error categorías") }
        }
    }
}
