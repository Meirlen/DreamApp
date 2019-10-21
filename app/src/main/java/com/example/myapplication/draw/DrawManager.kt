package com.example.myapplication.draw

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.myapplication.data.AnimValue
import com.example.myapplication.data.Graph

class DrawManager {

    val graph = Graph()
    private val drawController = DrawController(graph)


    fun draw(canvas: Canvas) {
        drawController.draw(canvas)
    }

    fun update(value: AnimValue) {
        drawController.updateValue(value)
    }

}