package com.bangkidss.scholarseeks.api

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("jwt_token")
	val jwtToken: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)
