package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.data.DrawData
import com.example.myapplication.data.InputData
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
        val inputDataList = arrayListOf<InputData>()
        inputDataList.add(InputData(50))
        inputDataList.add(InputData(30))
        inputDataList.add(InputData(20))
        inputDataList.add(InputData(15))
        inputDataList.add(InputData(32))
        inputDataList.add(InputData(80))
        inputDataList.add(InputData(50))
        graphView.setData(inputDataList)
    }
}
