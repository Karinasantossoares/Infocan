package com.example.trasparenciagov.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.trasparenciagov.R


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handle = Handler(Looper.getMainLooper())
        val intent = Intent(this, MainActivity::class.java)
        handle.postDelayed(
            {
                startActivity(intent)
                finish()
            }, 800
        )

    }
}


