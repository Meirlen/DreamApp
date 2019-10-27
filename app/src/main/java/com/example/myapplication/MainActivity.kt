package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.data.DrawData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        graphView.setOnClickListener {
            playGraph()
        }

    }

    private fun playGraph() {
        val drawDataList = arrayListOf<DrawData>()
        drawDataList.add(DrawData(50, 100, 50, 100))
        drawDataList.add(DrawData(100, 200, 100, 100))
        drawDataList.add(DrawData(200, 400, 100, 290))
        drawDataList.add(DrawData(400, 490, 290, 390))
        drawDataList.add(DrawData(490, 530, 390, 250))
        drawDataList.add(DrawData(530, 600, 250, 320))
        graphView.setData(drawDataList)
    }
}
