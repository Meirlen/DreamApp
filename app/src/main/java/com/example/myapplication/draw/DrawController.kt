package com.example.myapplication.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.animation.AnimationManager
import com.example.myapplication.data.AnimValue
import com.example.myapplication.data.Graph
import android.graphics.Shader
import android.graphics.LinearGradient






class DrawController(private var graph: Graph, private var context: Context) {
    companion object {
        private const val TAG = "DrawController"
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private var animValue: AnimValue? = null
    private var coordPath = Path()
    private var testPath = Path()
    private var p = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        init()
    }

    fun draw(canvas: Canvas) {
        coordPath.reset()
        drawGraph(canvas)
        canvas.drawPath(coordPath, pathPaint)
        canvas.drawPath(coordPath, pathPaint)
        // угол
        testPath.moveTo(100f, 100f)
        testPath.lineTo(150f, 200f)
        testPath.lineTo(250f, 300f)
        canvas.drawPath(testPath, pathPaint)

    }

    fun updateValue(animValue: AnimValue) {
        this.animValue = animValue
    }

    private fun drawGraph(canvas: Canvas) {

        coordPath.moveTo(
            0 + graph.padding.toFloat(),
            graph.height.toFloat() + graph.padding.toFloat()
        )

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

        //  mainRect.set(0f, 0f, graph.width.toFloat(), graph.height.toFloat())
        //  canvas.drawRect(mainRect, fillPaint)

        val drawDataList = graph.drawDataList
        if (drawDataList == null || position > drawDataList.size - 1) {
            return
        }

        val drawData = drawDataList[position]

        val startX = calculateX(drawData.startX)
        val startY = calculateY(drawData.startY)

        val stopX: Int
        val stopY: Int

        if (isAnimation) {
            stopX = calculateX(animValue!!.x)
            stopY = calculateY(animValue!!.y)
            drawGraph(canvas, startX, startY, stopX, stopY)
        } else {
            stopX = calculateX(drawData.stopX)
            stopY = calculateY(drawData.stopY)
            drawGraph(canvas, startX, startY, stopX, stopY)
            drawPath(stopX, stopY)
        }
    }


    private fun drawPath(startX: Int, startY: Int) {
        Log.d(TAG, "x $startX y =$startY")
        coordPath.lineTo(startX.toFloat(), startY.toFloat())
        coordPath.lineTo(startX.toFloat(),graph.height.toFloat())
        coordPath.moveTo(
            0 + graph.padding.toFloat(),
            graph.height.toFloat() + graph.padding.toFloat()
        )
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


    private fun calculateY(y: Int) = (graph.height - graph.padding) - y

    private fun calculateX(x: Int) = (graph.padding + graph.valueBarWidth) + x

    private fun init() {
        val res = context.resources
        graph.padding = res.getDimension(R.dimen.graph_view_padding).toInt()
        graph.valueBarWidth = res.getDimension(R.dimen.value_bar_width).toInt()

        linePaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 4f

        pathPaint.color =ContextCompat.getColor(context, R.color.colorAccent)
        p.shader = LinearGradient(0f, 0f, 0f, graph.height.toFloat(), Color.YELLOW, Color.WHITE, Shader.TileMode.MIRROR)
        pathPaint.style = Paint.Style.FILL
        pathPaint.strokeWidth = 4f

        p.style = Paint.Style.FILL
        p.color = Color.WHITE
    }
}