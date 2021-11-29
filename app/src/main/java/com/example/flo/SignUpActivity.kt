package com.example.flo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupCompleteTv.setOnClickListener {
            signUp()
            finish()
        }
    }


    private fun getUser(): User {
        val email: String = binding.signupEmailTie.text.toString() + "@" + binding.signupAddressTie.text.toString()
        val password: String = binding.signupPasswordTie.text.toString()
        val name: String = binding.signupNameTie.text.toString()

        return User(email, password, name)
    }


//    private fun signUp(){
//        if(binding.signupEmailTie.text.toString().isEmpty() || binding.signupAddressTie.text.toString().isEmpty()){
//            Toast.makeText(this, "이메일 형식이 잘못되었습니다", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if(binding.signupPasswordTie.text.toString() != binding.signupCheckPasswordTie.text.toString()){
//            Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
//            return
//        }
//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.UserDao().insert(getUser())
//
//        val users = userDB.UserDao().getUsers()
//        Log.d("유저", users.toString())
//    }


    private fun signUp() {
        if (binding.signupEmailTie.text.toString().isEmpty() || binding.signupAddressTie.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signupNameTie.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다", Toast.LENGTH_SHORT).show()
            return
        }


        if (binding.signupPasswordTie.text.toString() != binding.signupCheckPasswordTie.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            return
        }
    }

}