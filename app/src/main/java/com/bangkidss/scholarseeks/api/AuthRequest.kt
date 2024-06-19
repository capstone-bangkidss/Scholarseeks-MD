package com.bangkidss.scholarseeks.api

data class AuthRequest(
    val id_token: String,
    val user_id: String? = null
)
