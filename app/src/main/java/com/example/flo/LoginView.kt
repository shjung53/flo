package com.example.flo

interface LoginView {

    fun onLoginLoading()
    fun onLoginSuccess()
    fun onLoginFailure(code: Int, message: String)
}