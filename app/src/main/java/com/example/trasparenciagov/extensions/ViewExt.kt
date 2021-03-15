package com.example.trasparenciagov.extensions

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.trasparenciagov.R


fun View.setOnClickListenerAnim(listener: () -> Unit) {
    setOnClickListener {
        if (isEnabled) {
            isEnabled = false
            val pulse: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
            startAnimation(pulse)
            pulse.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    isEnabled = true
                    listener.invoke()
                }

                override fun onAnimationStart(animation: Animation?) {
                    isEnabled = false
                }

            })
        }
    }
}