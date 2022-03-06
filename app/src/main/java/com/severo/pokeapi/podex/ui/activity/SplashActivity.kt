package com.severo.pokeapi.podex.ui.activity

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.severo.pokeapi.podex.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        delaySplash()
    }

    private fun delaySplash(){
        lifecycleScope.launch {
            delay(1500L)
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity).toBundle())
        }
    }
}