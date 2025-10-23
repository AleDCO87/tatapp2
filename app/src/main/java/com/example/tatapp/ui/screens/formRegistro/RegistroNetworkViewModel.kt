package com.example.tatapp.ui.screens.formRegistro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tatapp.core.net.toUserMessage
import com.example.tatapp.core.ui.UiState
import com.example.tatapp.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistroNetworkViewModel(
    private val repo: UserRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val state: StateFlow<UiState<Unit>> = _state

    fun register(email: String, pass: String, name: String?) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            runCatching {
                val result = auth.createUserWithEmailAndPassword(email, pass).await()
                val uid = result.user?.uid
                repo.register(uid, email, name)
            }.onSuccess { _state.value = UiState.Success(Unit) }
                .onFailure { _state.value = UiState.Error(it.toUserMessage()) }
        }
    }
}

class RegistroNetworkVMFactory(
    private val repo: UserRepository,
    private val auth: FirebaseAuth
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistroNetworkViewModel(repo, auth) as T
    }
}
