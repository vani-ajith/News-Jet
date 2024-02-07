package com.example.newsarticleapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.newsarticleapp.R
import com.example.newsarticleapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    lateinit var splashscreenBinding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashscreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = splashscreenBinding.root
        setContentView(view)

        splashscreenBinding.textviewSplash.animation = AnimationUtils.loadAnimation(this,R.anim.anim_splash_screen_txt)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
            startActivity(intent)
        },5000)
    }
}