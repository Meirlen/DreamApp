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
    private val selectedTitlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
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
        val sellSize = (width - padding) / filterSize

        var x: Float
        val y = height / 2f

        repeat(filterSize) { position ->

            x = padding.toFloat()
            x += (sellSize * position) + sellSize / 2
            val filterTitle = Filter.values()[position].title

            if (position == 3) {
                drawSelectedItemBackground(canvas, sellSize, x, y)
                drawTextCentred(canvas, selectedTitlePaint, filterTitle, x, y)
            } else
                drawTextCentred(canvas, titlePaint, filterTitle, x, y)

        }
    }

    private fun drawSelectedItemBackground(canvas: Canvas, sellSize: Int, cx: Float, cy: Float) {
        val rectF =
            RectF(cx + sellSize / 3, cy + sellSize / 4, cx - sellSize / 3, cy - sellSize / 4)
        canvas.drawRoundRect(rectF, 10f, 10f, selectedCirclePaint)
    }


    private val textBounds = Rect()
    private fun drawTextCentred(canvas: Canvas, paint: Paint, text: String, cx: Float, cy: Float) {
        paint.getTextBounds(text, 0, text.length, textBounds)
        canvas.drawText(text, cx, cy - textBounds.exactCenterY(), paint)
    }

    private fun init() {

        val res = context.resources

        padding = res.getDimension(R.dimen.graph_view_padding).toInt()
        titlePaint.textSize = res.getDimension(R.dimen.filter_text_size)
        titlePaint.color = res.getColor(R.color.colorPrimaryText)
        titlePaint.textAlign = Paint.Align.CENTER
        titlePaint.typeface = Typeface.DEFAULT_BOLD


        selectedTitlePaint.textSize = res.getDimension(R.dimen.filter_text_size)
        selectedTitlePaint.color = res.getColor(R.color.colorSelectedItemTitle)
        selectedTitlePaint.textAlign = Paint.Align.CENTER
        selectedTitlePaint.typeface = Typeface.DEFAULT_BOLD

        selectedCirclePaint.isFakeBoldText = true
        selectedCirclePaint.isAntiAlias = true
        selectedCirclePaint.alpha = 1
        selectedCirclePaint.color = res.getColor(R.color.colorSelectedItemBackground)
        selectedCirclePaint.textAlign = Paint.Align.CENTER
        selectedCirclePaint.style = Paint.Style.FILL

    }

}