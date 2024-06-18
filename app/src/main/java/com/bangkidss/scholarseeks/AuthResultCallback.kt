package com.bangkidss.scholarseeks

interface AuthResultCallback {
    fun onAuthSuccess(userModel: UserModel)
    fun onAuthFailure()
}