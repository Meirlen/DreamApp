package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.data.DrawData
import android.graphics.Shader.TileMode
import android.graphics.LinearGradient
import android.graphics.Shader



class GradientView : View {


    private var erasePath = Path()

    private val erasePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val poinPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val gradienPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        linePaint.color = Color.MAGENTA
        linePaint.color = Color.TRANSPARENT
        erasePaint.color = Color.GRAY
        poinPaint.color = Color.RED
        poinPaint.strokeWidth = 20f
        textPaint.color = Color.WHITE
        textPaint.textSize = 38F
        textPaint.strokeWidth = 10f

    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec) / 2
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val shader = LinearGradient(0f, 0f, 0f, height.toFloat(),  Color.parseColor("#2BB3A4"), Color.parseColor("#0F1D2A"), TileMode.CLAMP)
        gradienPaint.shader = shader
        canvas.drawPaint(gradienPaint)

        val xCoordinates = listOf(0f,120f, 350f, 600f)
        val yCoordinates = listOf(250f,150f, 120f, 400f)
        var firstSet = false
        var x = 0F
        var y = 0F

        xCoordinates.forEachIndexed { index, fl ->
            x = xCoordinates[index]
            y = calculateY(yCoordinates[index])
            if (!firstSet) {
                erasePath.moveTo(x, calculateY(0f))
                erasePath.lineTo(x, y)
                firstSet = true
            } else {
                erasePath.lineTo(x, y)
            }
            canvas.drawPoint(x,y,poinPaint)
            canvas.drawText(index.toString(),x,y,textPaint)
        }

        erasePath.lineTo(width.toFloat(), y)
        erasePath.lineTo(width.toFloat(), calculateY(0f))
        erasePath.lineTo(0f, calculateY(0f))

        canvas.drawPath(erasePath,erasePaint)
    }

    private fun calculateY(y: Float) = y


}