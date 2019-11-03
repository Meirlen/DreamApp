package com.example.myapplication.manager

import com.example.myapplication.data.*


fun getDrawDataList(graph: Graph): ArrayList<DrawData> {

    val drawDataList = arrayListOf<DrawData>()

    if (graph.inputDataList == null) {
        return drawDataList
    }

    val dataList = graph.inputDataList
    correctDataListSize(dataList!!)

    return createDrawDataList(graph, createValueList(dataList))

}


private fun createDrawDataList(graph: Graph, valueList: ArrayList<Float>): ArrayList<DrawData> {

    val drawDataList = arrayListOf<DrawData>()
    for (i in 0 until valueList.size - 1) {
        val drawData = createDrawData(graph, valueList, i)
        drawDataList.add(drawData)
    }

    return drawDataList
}


fun createDrawData(graph: Graph, valueList: ArrayList<Float>, position: Int): DrawData {


    val drawData = DrawData()
    if (position > valueList.size - 1) {
        return drawData
    }

    val value = valueList[position]
    val startX = getCoordinateX(graph, position)
    val startY = getCoordinateY(graph, value)

    drawData.startX = startX
    drawData.startY = startY

    val nextPosition = position + 1
    if (nextPosition < valueList.size) {
        val nextValue = valueList[nextPosition]
        val stopX = getCoordinateX(graph, nextPosition)
        val stopY = getCoordinateY(graph, nextValue)

        drawData.stopX = stopX
        drawData.stopY = stopY
    }

    return drawData

}


private fun getCoordinateX(chart: Graph, index: Int): Int {
    val width = chart.width
    val titleWidth = chart.valueBarWidth + chart.padding

    val widthCorrected = width - titleWidth
    val partWidth = widthCorrected / (MAX_ITEMS_COUNT - 1)

    var coordinate = titleWidth + partWidth * index

    if (coordinate < 0) {
        coordinate = 0

    } else if (coordinate > width) {
        coordinate = width
    }

    return coordinate
}

fun getCoordinateY(graph: Graph, value: Float): Int {

    val height = graph.height
    val heightOffset = graph.padding + graph.filterHeight

    val heightCorrected = height - heightOffset

    var coordinate = (heightCorrected - heightCorrected * value).toInt()

    coordinate += heightOffset

    return coordinate

}


fun max(dataList: List<InputData>?): Int {
    var maxValue = 0

    if (dataList == null || dataList.isEmpty()) {
        return maxValue
    }

    for (data in dataList) {
        if (data.value > maxValue) {
            maxValue = data.value
        }
    }

    return maxValue
}

private fun correctDataListSize(dataList: ArrayList<InputData>) {
    val dataSize = dataList.size
    if (dataSize < MAX_ITEMS_COUNT) {
        for (i in dataList.size until MAX_ITEMS_COUNT) {
            dataList.add(InputData(0))
        }

    }
}

private fun createValueList(dataList: List<InputData>): ArrayList<Float> {

    val valueList = java.util.ArrayList<Float>()
    val topValue = max(dataList)

    for (data in dataList) {
        val value = data.value.toFloat() / topValue
        valueList.add(value)
    }

    return valueList
}

fun getCorrectedMaxValue(maxValue: Int): Int {
    for (value in maxValue downTo CHART_PART_VALUE) {
        if (isRightValue(value)) {
            return value
        }
    }

    return maxValue
}

private fun isRightValue(value: Int): Boolean {
    val valueResidual = value % CHART_PART_VALUE
    return valueResidual == 0
}

