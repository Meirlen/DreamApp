package com.example.myapplication.data


const val MAX_ITEMS_COUNT = 7
const val CHART_PART_VALUE = 10
const val  CHART_PARTS = 5

class Graph(

    var drawDataList: ArrayList<DrawData>? = null,
    var inputDataList: ArrayList<InputData>? = null,
    var width: Int = 0,
    var height: Int = 0,
    var padding: Int = 0,
    var valueBarWidth: Int = 0,
    var textSize: Float = 0f,
    var strokeHeight: Float = 0f
)