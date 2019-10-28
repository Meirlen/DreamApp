package com.example.myapplication.data


enum class Filter(var position: Int, var title: String) {

    MINUTE(0, "1M"),
    HOUR(1, "1H"),
    DAY(2, "1D"),
    WEEK(3, "1W"),
    MONTH(4, "1M"),
    THREE_MONTH(5, "3M"),
    ALL(6, "ALL"), ;

}