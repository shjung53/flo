package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.databinding.ActivitySignupBinding

class LoginActivity : AppCompatActivity() {
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

    private fun login() {
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

        val userDB = SongDatabase.getInstance(this)!!

        val user = userDB.UserDao().getUser(email,password)


        user?.let {
            Log.d("유저 인덱스", "userId: ${user.id}, $user")
            saveJwt(user.id)
            startMainActivity()
        }

        if(userDB.UserDao().getExistUser(email, password).isEmpty()){
        Toast.makeText(this, "회원정보가 존재하지 않습니다", Toast.LENGTH_SHORT).show()}

    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun saveJwt(jwt: Int){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt("jwt", jwt)
        editor.apply()
    }


}