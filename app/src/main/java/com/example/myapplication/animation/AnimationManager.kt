package com.example.myapplication.animation

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator

import android.view.animation.AccelerateDecelerateInterpolator

class AnimationManager {

    companion object {

        const val TAG = "AnimationManager"
        const val PROPERTY_X = "PROPERTY_X"
        const val PROPERTY_Y = "PROPERTY_Y"
        const val PROPERTY_ALPHA = "PROPERTY_ALPHA"
        const val ALPHA_START = 0
        const val ALPHA_END = 255
        const val ANIMATION_DURATION = 2500L
    }

    private var listener: ((Int, Int) -> Unit)? = null

    fun setListener(listener: (Int, Int) -> Unit) {
        this.listener = listener
        createAnimator()
    }


    fun createAnimator() {

        val propertyX = PropertyValuesHolder.ofInt(PROPERTY_X, 120, 800)
        val propertyY = PropertyValuesHolder.ofInt(PROPERTY_Y, 30, 500)
        val propertyAlpha = PropertyValuesHolder.ofInt(PROPERTY_ALPHA, ALPHA_START, ALPHA_END)

        val animator = ValueAnimator()
        animator.setValues(propertyX, propertyY, propertyAlpha)
        animator.duration = ANIMATION_DURATION
        animator.interpolator = AccelerateDecelerateInterpolator()


        animator.addUpdateListener { valueAnimator ->
            onAnimationUpdate(animator)
        }
        animator.start()
    }

    private fun onAnimationUpdate(animator: ValueAnimator) {

        val x = animator.getAnimatedValue(PROPERTY_X) as Int
        val y = animator.getAnimatedValue(PROPERTY_Y) as Int

        listener?.invoke(x, y)
    }


}