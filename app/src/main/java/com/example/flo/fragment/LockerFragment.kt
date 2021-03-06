package com.example.flo.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.LockerViewPagerAdapter
import com.example.flo.LoginActivity
import com.example.flo.MainActivity
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.getUserIdx
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding

    val menu = arrayListOf("저장한곡", "음악파일", "저장앨범")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)


        val lockerAdapter = LockerViewPagerAdapter(this)
        binding.lockerMenuVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerMenuTl, binding.lockerMenuVp){
            tab, position ->
            tab.text = menu[position]
        }.attach()

        binding.lockerLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return binding.root

    }

    private fun initView(){
        val userIdx = getUserIdx(requireContext())

        if(userIdx == 0){
            binding.lockerLoginTv.text = "로그인"

            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }else{
            binding.lockerLoginTv.text = "로그아웃"

            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity, MainActivity::class.java))
                logout()
            }
        }
    }


    private fun logout(){
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()

        editor.remove("userIdx")
        editor.remove("jwt")
        editor.apply()
    }


    override fun onStart() {
        super.onStart()
        initView()
    }

}
