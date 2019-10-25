package com.example.myapplication.custom

import android.graphics.*
import android.graphics.drawable.Drawable


class GraphGradientDrawable : Drawable() {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private var mPath = Path()


    override fun draw(canvas: Canvas) {
        canvas.drawPath(mPath, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        val width = bounds.width()
        val height = bounds.height()
        mPath.reset()
        mPath.moveTo(200f, 100f)
        mPath.lineTo(800f,100f)
        mPath.lineTo(800f,800f)
        mPath.lineTo(200f,800f)
        mPath.close()
    }

}