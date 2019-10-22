package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.data.DrawData

class GraphView : View, GraphManager.AnimationListener {


    private val graphManager = GraphManager(context, this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec) / 2
        graphManager.graph().width = width
        graphManager.graph().height = height
        setMeasuredDimension(width, height)
    }

    fun setData(drawDataList: ArrayList<DrawData>) {
        graphManager.animate()
        val graph = graphManager.graph()
        graph.drawDataList = drawDataList


        graphManager.animate()

    }

    override fun onDraw(canvas: Canvas) {
        graphManager.getDrawManager().draw(canvas)
    }

    override fun onAnimationUpdated() {
        invalidate()
    }

}