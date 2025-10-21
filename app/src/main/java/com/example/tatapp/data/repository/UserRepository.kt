package com.example.tatapp.data.repository

import com.example.tatapp.data.remote.services.RegisterRequest
import com.example.tatapp.data.remote.services.RegisterResponse
import com.example.tatapp.data.remote.services.UserService

class UserRepository(private val service: UserService) {
    suspend fun register(uid: String?, email: String, name: String?): RegisterResponse =
        service.register(RegisterRequest(uid, email, name))
}
