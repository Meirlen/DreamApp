package com.example.myapplication

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.myapplication.data.AnimValue
import com.example.myapplication.data.Filter
import com.example.myapplication.manager.Constans
import com.example.myapplication.manager.Constans.ALPHA_END
import com.example.myapplication.manager.Constans.ALPHA_START
import com.example.myapplication.manager.Constans.PROPERTY_ALPHA
import com.example.myapplication.manager.Constans.PROPERTY_X

class FilterView : View {


    private var padding: Int = 0
    private val filterSize = Filter.values().size
    private val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val selectedTitlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val selectedCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var selectedPosition = 0
    private var lastPosition = 0
    private var animValue: AnimValue? = null


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

        val sellWidth = getSellWidth()

        var x: Float
        val y = height / 2f

        if (animValue != null) {
            drawSelectedItemBackground(canvas)
        }

        repeat(filterSize) { position ->

            x = (sellWidth * position) + sellWidth / 2f

            val filterTitle = Filter.values()[position].title

            if (position == selectedPosition) {
                //  drawSelectedItemBackground(canvas, sellWidth, x, y)
                drawTextCentred(canvas, selectedTitlePaint, filterTitle, x, y)
            } else
                drawTextCentred(canvas, titlePaint, filterTitle, x, y)

        }


    }


    private fun drawSelectedItemBackground(canvas: Canvas) {
        val sellSize = getSellWidth()
        val cy = height / 2f
        val cx = animValue!!.x.toFloat()
        val rectF =
            RectF(cx + sellSize / 3, cy + sellSize / 4, cx - sellSize / 3, cy - sellSize / 4)
        canvas.drawRoundRect(rectF, 10f, 10f, selectedCirclePaint)
        selectedCirclePaint.alpha = animValue?.alpha!!
    }


    private val textBounds = Rect()
    private fun drawTextCentred(canvas: Canvas, paint: Paint, text: String, cx: Float, cy: Float) {
        paint.getTextBounds(text, 0, text.length, textBounds)
        canvas.drawText(text, cx, cy - textBounds.exactCenterY(), paint)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            lastPosition = selectedPosition
            selectedPosition = getSelectedPositionFromLocation(event.x)
            createAnimator(lastPosition, selectedPosition).start()

            invalidate()
        }
        return true
    }

    private fun getSelectedPositionFromLocation(ex: Float) = (ex / getSellWidth()).toInt()

    private fun getSellWidth() = (width - padding) / filterSize


    private fun createAnimator(fromPosition: Int, toPosition: Int): Animator {


        val startX = fromPosition * getSellWidth() + getSellWidth() / 2f
        val stopX = toPosition * getSellWidth() + getSellWidth() / 2f

        val propertyX = PropertyValuesHolder.ofFloat(PROPERTY_X, startX, stopX)
        val propertyAlpha = PropertyValuesHolder.ofInt(PROPERTY_ALPHA, ALPHA_END, ALPHA_START)

        val animator = ValueAnimator()
        animator.setValues(propertyX, propertyAlpha)
        animator.duration = Constans.FILTER_ANIMATION_DURATION
        animator.interpolator = AccelerateDecelerateInterpolator()

        animator.addUpdateListener { valueAnimator ->
            animValue = AnimValue(0, 0)
            onAnimationUpdate(valueAnimator)
        }

        return animator
    }


    private fun onAnimationUpdate(valueAnimator: ValueAnimator) {
        val x = valueAnimator.getAnimatedValue(PROPERTY_X) as Float
        val alpha = valueAnimator.getAnimatedValue(PROPERTY_ALPHA) as Int
        animValue!!.x = x.toInt()
        animValue!!.alpha = alpha

        invalidate()
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