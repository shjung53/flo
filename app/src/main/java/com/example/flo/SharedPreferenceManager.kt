package com.example.flo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun saveJwt(context: Context, jwt: String){
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("jwt", jwt)
    editor.apply()
}

fun saveUserIdx(context: Context, jwt: Int){
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()
    editor.putInt("jwt", jwt)
    editor.apply()
}

fun getUserIdx(context: Context): Int{
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("userIdx", 0)
}