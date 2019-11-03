package com.example.myapplication.animation

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.AnimatorSet
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.myapplication.data.AnimValue
import com.example.myapplication.data.DrawData
import com.example.myapplication.data.Graph
import java.util.ArrayList

class AnimationManager(var graph: Graph) {

    companion object {

        const val PROPERTY_X = "PROPERTY_X"
        const val PROPERTY_Y = "PROPERTY_Y"
        const val PROPERTY_ALPHA = "PROPERTY_ALPHA"
        const val ALPHA_START = 0
        const val ALPHA_END = 255
        const val ANIMATION_DURATION = 2000L
        const val VALUE_NONE = -1

    }

    private var listener: ((AnimValue) -> Unit)? = null
    private lateinit var animatorSet: AnimatorSet

    fun setListener(listener: (AnimValue) -> Unit) {
        this.listener = listener
    }

    fun animate() {
        animatorSet = AnimatorSet()
        animatorSet.playSequentially(createAnimatorList())
        animatorSet.start()
    }

    private fun createAnimatorList(): List<Animator> {

        val drawDataList = graph.drawDataList
        val animatorList = ArrayList<Animator>()

        drawDataList?.map {
            animatorList.add(createAnimator(it))
        }

        return animatorList

    }


    private fun createAnimator(drawData: DrawData): Animator {

        val propertyX = PropertyValuesHolder.ofInt(PROPERTY_X, drawData.startX, drawData.stopX)
        val propertyY = PropertyValuesHolder.ofInt(PROPERTY_Y, drawData.startY, drawData.stopY)
        val propertyAlpha = PropertyValuesHolder.ofInt(PROPERTY_ALPHA, ALPHA_START, ALPHA_END)

        val animator = ValueAnimator()
        animator.setValues(propertyX, propertyY, propertyAlpha)
        animator.duration = ANIMATION_DURATION
        animator.interpolator = AccelerateDecelerateInterpolator()

        animator.addUpdateListener { valueAnimator ->
            onAnimationUpdate(valueAnimator)
        }

        return animator
    }

    private fun onAnimationUpdate(valueAnimator: ValueAnimator) {
        if (listener == null) {
            return
        }

        val x = valueAnimator.getAnimatedValue(PROPERTY_X) as Int
        val y = valueAnimator.getAnimatedValue(PROPERTY_Y) as Int
        val runningAnimationPosition = getRunningAnimationPosition()

        val value = AnimValue(x, y, runningAnimationPosition)
        listener?.invoke(value)
    }

    private fun getRunningAnimationPosition(): Int {
        val childAnimations = animatorSet.childAnimations
        for (i in childAnimations.indices) {
            val animator = childAnimations[i]
            if (animator.isRunning) {
                return i
            }
        }

        return VALUE_NONE
    }

}