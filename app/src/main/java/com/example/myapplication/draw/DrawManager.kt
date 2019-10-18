package com.example.myapplication.draw

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class DrawManager {

    private val paintPoint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var x = 0
    private var y = 0

    init {
        setUpPaints()
    }

    fun draw(canvas: Canvas){
        canvas.drawPoint(x.toFloat(),y.toFloat(),paintPoint)
    }

    fun update(x:Int,y:Int){
        this.x = x
        this.y = y

    }

    private fun setUpPaints() {
        paintPoint.style = Paint.Style.FILL
        paintPoint.color = Color.YELLOW
        paintPoint.strokeWidth = 6f
    }
}