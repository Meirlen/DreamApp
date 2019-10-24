package com.example.myapplication.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.example.myapplication.R
import com.example.myapplication.data.AnimValue
import com.example.myapplication.data.Graph


class DrawController(private var graph: Graph, private var context: Context) {

    companion object {
        private const val TAG = "DrawController"
    }

    private val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private val paintTitle = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintHorizontalLines = Paint(Paint.ANTI_ALIAS_FLAG)

    private var animValue: AnimValue? = null
    private var graphPath = Path()

    private var bottomX: Float = 0f
    private var bottomY: Float = 0F

    private var verticalBarItemsCount = 7
    private var singleStepValue = 0

    init {
        init()
    }

    fun draw(canvas: Canvas) {


        graphPath.reset()

        drawGraph()
        canvas.drawPath(graphPath, pathPaint)

        drawVerticalBar(canvas)

    }

    fun updateValue(animValue: AnimValue) {
        this.animValue = animValue
    }


    private fun drawVerticalBar(canvas: Canvas) {

        singleStepValue = (graph.height - graph.padding * 2) / verticalBarItemsCount

        repeat(verticalBarItemsCount) {

            val step = it
            val x = (0 + graph.padding).toFloat()
            val y = calculateY(step * singleStepValue)

            val amount = step * 1000
            val title = "$ $amount"
            canvas.drawText(title, x, y, paintTitle)

            canvas.drawLine(x +  graph.valueBarWidth * 2  , y , graph.width.toFloat() , y , paintHorizontalLines)
        }

    }


    private fun drawGraph() {

        bottomX = calculateX(0)
        bottomY = calculateY(0)

        if (animValue != null) {

            graphPath.moveTo(bottomX, bottomY)
            val runningAnimationPosition = animValue!!.runningAnimationPosition
            drawPathGraph(runningAnimationPosition)
        }

    }


    private fun drawPathGraph(runningAnimationPosition: Int) {

        val drawDataList = graph.drawDataList?.subList(0, runningAnimationPosition)

        val startIndex = 0
        val endIndex = drawDataList?.size?.minus(1) as Int

        if (endIndex >= 0) {

            drawDataList.forEachIndexed { index, drawData ->

                val startX = calculateX(drawData.startX)
                val startY = calculateY(drawData.startY)

                val stopX = calculateX(drawData.stopX)
                val stopY = calculateY(drawData.stopY)

                if (index == startIndex) {
                    graphPath.lineTo(startX, startY)
                }
                graphPath.lineTo(stopX, stopY)

            }

        }

        playAnim()

    }

    private fun playAnim() {

        val stopX = calculateX(animValue!!.x)
        val stopY = calculateY(animValue!!.y)

        graphPath.lineTo(stopX, stopY)
        graphPath.lineTo(stopX, bottomY)

    }

    private fun calculateY(y: Int) = ((graph.height - graph.padding) - y).toFloat()

    private fun calculateX(x: Int) = ((graph.padding + graph.valueBarWidth) + x).toFloat()

    private fun init() {

        val res = context.resources

        graph.padding = res.getDimension(R.dimen.graph_view_padding).toInt()
        graph.valueBarWidth = res.getDimension(R.dimen.value_bar_width).toInt()

        pathPaint.color = res.getColor(R.color.colorAccent)
        pathPaint.style = Paint.Style.STROKE
        pathPaint.strokeWidth = 4f

        paintHorizontalLines.color = res.getColor(R.color.colorPrimaryText)
        paintHorizontalLines.style = Paint.Style.STROKE
        paintHorizontalLines.strokeWidth = 0.5f

        paintTitle.style = Paint.Style.FILL
        paintTitle.textSize = 30f
        paintTitle.color = res.getColor(R.color.colorPrimaryText)

    }
}