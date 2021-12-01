package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignupTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.loginLoginBtn.setOnClickListener{
            login()
        }
    }


    private fun login(){
        if(binding.loginEmailTie.text.toString().isEmpty() || binding.loginAddressTie.text.toString().isEmpty()){
            Toast.makeText(this,"이메일 형식이 잘못되었습니다", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.loginPasswordTie.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email: String = binding.loginEmailTie.text.toString() + "@" + binding.loginAddressTie.text.toString()
        val password: String = binding.loginPasswordTie.text.toString()
        val user = User(email, password, "")
        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(user)


    }


    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginLoading() {
        binding.loginLoadingIv.visibility = View.VISIBLE
    }

    override fun onLoginSuccess(auth: Auth) {
        binding.loginLoadingIv.visibility = View.GONE

        saveJwt(this, auth.jwt)
        saveUserIdx(this, auth.userIdx)

        startMainActivity()
    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.loginLoadingIv.visibility = View.GONE

        when(code){
            2015, 2019, 3014 -> {
                binding.loginErrorTv.visibility = View.VISIBLE
                binding.loginErrorTv.text = message
            }
        }
    }

}