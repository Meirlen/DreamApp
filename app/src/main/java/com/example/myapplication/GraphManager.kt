package com.example.myapplication

import android.content.Context
import android.util.Log
import com.example.myapplication.animation.AnimationManager
import com.example.myapplication.draw.DrawManager

class GraphManager(context: Context, listener: AnimationListener) {


    companion object {
        const val TAG = "AnimationManager"
    }

    private var animationManager: AnimationManager = AnimationManager()
    private val drawManager = DrawManager()

    init {
        animationManager.setListener { x, y ->
            drawManager.update(x, y)
            listener.onAnimationUpdated()
        }
    }

    interface AnimationListener {
        fun onAnimationUpdated()
    }

    fun getDrawManager() = drawManager

}