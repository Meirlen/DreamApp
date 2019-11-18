package com.example.myapplication.draw

import android.content.Context
import android.graphics.*
import com.example.myapplication.R
import com.example.myapplication.data.AnimValue
import com.example.myapplication.data.Graph
import com.example.myapplication.custom.GraphGradientDrawable
import com.example.myapplication.data.CHART_PARTS
import com.example.myapplication.data.Filter
import com.example.myapplication.manager.getCorrectedMaxValue
import com.example.myapplication.manager.max


class DrawController(private var graph: Graph, private var context: Context) {

    companion object {
        private const val TAG = "DrawController"
    }

    private val pathFillPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private val pathLinePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private val pathAddLinePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private val paintValueBarTitle = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintHorizontalLines = Paint(Paint.ANTI_ALIAS_FLAG)


    private var bottomX: Float = 0f
    private var bottomY: Float = 0F


    private var gdBoundsRect = Rect()
    private val gradientDrawable = GraphGradientDrawable()
    private var graphBackgroundPath = Path()
    private var graphLinePath = Path()
    private var graphAddLinePath = Path()
    private var animValue: AnimValue? = null


    init {
        init()
    }

    fun draw(canvas: Canvas) {

        drawVerticalBar(canvas)
        drawGradient(canvas)
        drawGraph(canvas)

    }

    private fun drawGradient(canvas: Canvas) {

        gradientDrawable.setUp(graphBackgroundPath)

        val topY = graph.padding
        val leftX = graph.padding + graph.valueBarWidth * 2
        val rightX = graph.width
        val bottomY = graph.height - graph.padding

        gdBoundsRect.set(leftX, topY, rightX, bottomY)
        gradientDrawable.bounds = gdBoundsRect
        gradientDrawable.draw(canvas)

    }

    fun updateValue(animValue: AnimValue) {
        this.animValue = animValue
    }


    private fun drawVerticalBar(canvas: Canvas) {
        val inputDataList = graph.inputDataList
        if (inputDataList == null || inputDataList.isEmpty()) {
            return
        }

        val maxValue = max(inputDataList)
        val correctedMaxValue = getCorrectedMaxValue(maxValue)
        val value = correctedMaxValue.toFloat() / maxValue

        val heightOffset = graph.padding
        val padding = graph.padding
        val height = graph.height - padding
        val width = graph.width.toFloat()


        val chartPartHeight = (height - heightOffset) * value / CHART_PARTS


        var currHeight = height.toFloat()
        var currTitle = 0



        for (i in 0..CHART_PARTS) {
            val titleY = currHeight

            if (i > 0) {
                canvas.drawLine(
                    (graph.valueBarWidth * 2).toFloat(),
                    currHeight,
                    width,
                    currHeight,
                    paintHorizontalLines
                )
            }

            val title = currTitle.toString()
            canvas.drawText(title, padding.toFloat(), titleY, paintValueBarTitle)

            currHeight -= chartPartHeight
            currTitle += correctedMaxValue / CHART_PARTS
        }

    }


    private fun drawGraph(canvas: Canvas) {

        graphBackgroundPath.reset()
        graphLinePath.reset()
        graphAddLinePath.reset()

        bottomX = calculateX(0)
        bottomY = calculateY(0)

        if (animValue != null) {
            graphBackgroundPath.moveTo(bottomX, bottomY)
            val runningAnimationPosition = animValue!!.runningAnimationPosition
            drawPathGraph(runningAnimationPosition!!)
        }

        canvas.drawPath(graphAddLinePath, pathAddLinePaint)
        canvas.drawPath(graphBackgroundPath, pathFillPaint)
        canvas.drawPath(graphLinePath, pathLinePaint)


    }


    private fun drawPathGraph(runningAnimationPosition: Int) {

        val drawDataList = graph.drawDataList?.subList(0, runningAnimationPosition)

        val startIndex = 0
        val endIndex = drawDataList?.size?.minus(1) as Int

        if (endIndex >= 0) {

            drawDataList.forEachIndexed { index, drawData ->

                val startX = drawData.startX
                val startY = drawData.startY

                val stopX = drawData.stopX
                val stopY = drawData.stopY

                if (index == startIndex) {
                    graphBackgroundPath.lineTo(startX.toFloat(), startY.toFloat())
                    graphLinePath.moveTo(startX.toFloat(), startY.toFloat())
                    graphAddLinePath.moveTo(startX.toFloat(), startY.toFloat())
                }
                graphBackgroundPath.lineTo(stopX.toFloat(), stopY.toFloat())
                graphLinePath.lineTo(stopX.toFloat(), stopY.toFloat())
                graphAddLinePath.lineTo(stopX.toFloat(), stopY.toFloat())
            }

        }

        if (runningAnimationPosition == 0) {
            val startX = graph.drawDataList!![0].startX.toFloat()
            val startY = graph.drawDataList!![0].startY.toFloat()
            graphBackgroundPath.lineTo(startX, startY)
            graphLinePath.moveTo(startX, startY)
            graphAddLinePath.moveTo(startX, startY)
        }
        playAnim()

    }

    private fun playAnim() {

        val stopX = animValue!!.x
        val stopY = animValue!!.y

        graphBackgroundPath.lineTo(stopX.toFloat(), stopY.toFloat())
        graphBackgroundPath.lineTo(stopX.toFloat(), bottomY)
        graphLinePath.lineTo(stopX.toFloat(), stopY.toFloat())
        graphAddLinePath.lineTo(stopX.toFloat(), stopY.toFloat())


    }

    private fun calculateY(y: Int) = ((graph.height - graph.padding) - y).toFloat()

    private fun calculateX(x: Int) = ((graph.padding + graph.valueBarWidth) + x).toFloat()


    private fun init() {

        val res = context.resources

        graph.padding = res.getDimension(R.dimen.graph_view_padding).toInt()
        graph.valueBarWidth = res.getDimension(R.dimen.value_bar_width).toInt()
        graph.textSize = res.getDimension(R.dimen.graph_text_size)
        graph.strokeHeight = res.getDimension(R.dimen.graph_stroke_height)

        pathFillPaint.color = Color.TRANSPARENT
        pathLinePaint.style = Paint.Style.STROKE
        pathLinePaint.color = res.getColor(R.color.colorAccent)

        pathAddLinePaint.style = Paint.Style.STROKE
        pathAddLinePaint.color = res.getColor(R.color.colorPrimary)
        pathAddLinePaint.strokeWidth = res.getDimension(R.dimen.additional_line_height)


        paintHorizontalLines.color = res.getColor(R.color.colorAccent)
        pathLinePaint.strokeWidth = graph.strokeHeight

        paintHorizontalLines.color = res.getColor(R.color.colorPrimaryText)
        paintHorizontalLines.style = Paint.Style.STROKE
        paintHorizontalLines.strokeWidth = res.getDimension(R.dimen.line_height)

        paintValueBarTitle.style = Paint.Style.FILL
        paintValueBarTitle.textSize = graph.textSize
        paintValueBarTitle.color = res.getColor(R.color.colorPrimaryText)

    }
}