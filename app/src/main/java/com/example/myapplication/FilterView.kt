package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.data.Filter

class FilterView : View {


    private var padding: Int = 0
    private val filterSize = Filter.values().size
    private val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val selectedCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        init()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val res = context.resources
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = res.getDimension(R.dimen.filter_height).toInt()
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        drawFilter(canvas)
    }


    private fun drawFilter(canvas: Canvas) {

        val sellSize = (width - padding) / filterSize

        var x: Float
        val y = height / 2f

        repeat(filterSize) { position ->

            x = padding.toFloat()
            x += sellSize * position
            val filterTitle = Filter.values()[position].title
            canvas.drawText(filterTitle, x, y, titlePaint)
            if (position == 3) {
                val rectF = RectF(x + 20, y + 20, x - 20, y - 20)
                canvas.drawRoundRect(rectF, 10f, 10f, selectedCirclePaint)
            }

        }

    }

    private fun init() {
        val res = context.resources
        padding = res.getDimension(R.dimen.graph_view_padding).toInt()
        titlePaint.textSize = res.getDimension(R.dimen.filter_text_size)
        titlePaint.color = res.getColor(R.color.colorPrimaryText)


        selectedCirclePaint.isFakeBoldText = true
        selectedCirclePaint.isAntiAlias = true
        selectedCirclePaint.alpha = 1
        selectedCirclePaint.color = res.getColor(R.color.colorPrimaryText)
        selectedCirclePaint.textAlign = Paint.Align.CENTER
        selectedCirclePaint.style = Paint.Style.FILL
    }

}