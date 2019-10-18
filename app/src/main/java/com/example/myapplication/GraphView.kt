package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class GraphView : View, GraphManager.AnimationListener {


    private val graphManager = GraphManager(context,this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec) / 2
        setMeasuredDimension(width, height)
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.BLACK)
        graphManager.getDrawManager().draw(canvas)
    }

    override fun onAnimationUpdated() {
        invalidate()
    }



}