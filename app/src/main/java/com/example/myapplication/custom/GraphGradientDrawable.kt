package com.example.myapplication.custom

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.Shader
import android.graphics.LinearGradient


class GraphGradientDrawable : Drawable() {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private lateinit var mPath: Path


    fun setUp(path: Path) {
        mPath = path
    }

    override fun draw(canvas: Canvas) {
        mPaint.alpha = 50
        mPaint.shader = LinearGradient(
            0f,
            0f,
            0f,
            600f,
            Color.parseColor("#2BB3A4"),
            Color.parseColor("#0F1D2A"),
            Shader.TileMode.MIRROR
        )

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
        mPath.close()
    }

}