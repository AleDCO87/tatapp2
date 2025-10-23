package com.example.tatapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.POST

data class RegisterRequest(val uid: String?, val email: String, val name: String?)
data class RegisterResponse(val userId: String, val createdAt: String)

interface UserService {
    @POST("api/users/register")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse
}
