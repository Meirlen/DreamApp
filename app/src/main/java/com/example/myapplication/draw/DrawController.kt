package com.example.myapplication.draw

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.myapplication.animation.AnimationManager
import com.example.myapplication.data.AnimValue
import com.example.myapplication.data.Graph

class DrawController(var graph: Graph) {

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var animValue: AnimValue? = null

    init {
        init()
    }

    fun draw(canvas: Canvas) {
        drawGraph(canvas)
    }

    fun updateValue(animValue: AnimValue) {
        this.animValue = animValue
    }

    private fun drawGraph(canvas: Canvas) {

        animValue?.let {

            val runningAnimationPosition = it.runningAnimationPosition

            for (i in 0 until runningAnimationPosition) {
                drawGraph(canvas, i, false)
            }

            if (runningAnimationPosition > AnimationManager.VALUE_NONE) {
                //anim
                drawGraph(canvas, runningAnimationPosition, true)
            }
        }


    }

    private fun drawGraph(canvas: Canvas, position: Int, isAnimation: Boolean) {

        val drawDataList = graph.drawDataList
        if (drawDataList == null || position > drawDataList.size - 1) {
            return
        }

        val drawData = drawDataList[position]

        val startX = drawData.startX
        val startY = drawData.startY

        var stopX = drawData.stopX
        var stopY = drawData.stopY

        if (isAnimation) {
            stopX = animValue!!.x
            stopY = animValue!!.y
        }

        drawGraph(canvas, startX, startY, stopX, stopY)

    }


    private fun drawGraph(canvas: Canvas, startX: Int, startY: Int, stopX: Int, stopY: Int) {

        canvas.drawLine(
            startX.toFloat(),
            startY.toFloat(),
            stopX.toFloat(),
            stopY.toFloat(),
            linePaint
        )

    }


    private fun init() {

        linePaint.color = Color.YELLOW
        linePaint.strokeWidth = 6f

    }
}