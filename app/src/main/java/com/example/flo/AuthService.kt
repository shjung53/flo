package com.example.flo

import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(user: User){

        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(GsonConverterFactory.create()).build()
//        이거 맞는지 잘 모르겠음 ㅠ
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        signUpView.onSignUpLoading()

        authService.signUp(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                val response = response.body()!!

                when(response.code){
                    1000 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure(response.code, response.message)
                    }
                }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                signUpView.onSignUpFailure(400,"네트워크 오류가 발생했습니다")
            }
        })
    }

    fun login(user: User){

        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(GsonConverterFactory.create()).build()
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        loginView.onLoginLoading()

        authService.login(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                val response = response.body()!!

                when(response.code){
                    1000 -> loginView.onLoginSuccess()
                    else -> loginView.onLoginFailure(response.code, response.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                loginView.onLoginFailure(400,"네트워크 오류가 발생했습니다")
            }
        })
    }



}