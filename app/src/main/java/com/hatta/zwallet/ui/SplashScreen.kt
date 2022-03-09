package com.hatta.zwallet.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hatta.zwallet.R
import com.hatta.zwallet.ui.auth.AuthActivity
import com.hatta.zwallet.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent =  Intent (this, AuthActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}