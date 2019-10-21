package com.example.myapplication

import android.content.Context
import android.util.Log
import com.example.myapplication.animation.AnimationManager
import com.example.myapplication.draw.DrawManager

class GraphManager(context: Context, listener: AnimationListener) {


    companion object {
        const val TAG = "AnimationManager"
    }

    private val drawManager = DrawManager()
    private var animationManager: AnimationManager = AnimationManager(drawManager.graph)

    init {
        animationManager.setListener { animValue ->
            drawManager.update(animValue)
            listener.onAnimationUpdated()
        }
    }

    fun animate() {
        drawManager.graph.drawDataList?.let {
            if (it.isNotEmpty()) {
                animationManager.animate()
            }
        }
    }

    fun graph() = drawManager.graph

    interface AnimationListener {
        fun onAnimationUpdated()
    }

    fun getDrawManager() = drawManager

}