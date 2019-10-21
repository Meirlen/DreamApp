package com.example.myapplication.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.myapplication.data.AnimValue
import com.example.myapplication.data.Graph

class DrawManager(context: Context) {

    val graph = Graph()
    private val drawController = DrawController(graph, context)


    fun draw(canvas: Canvas) {
        drawController.draw(canvas)
    }

    fun update(value: AnimValue) {
        drawController.updateValue(value)
    }

}