package ru.startandroid.develop.ivelost.module.data

//? содержит информацию о полях которые нужно заполнить для gridAdapter
data class GridLayoutItem(
    val imageResource: Int,
    val headerText: String,
    val descriptionText: String,
    val timeText: String,
    val viewsCount: Int,
    val location: String,
    val northPoint: Double,
    val eastPoint: Double,
    val adType: String,
    val adTypeObject: String,
    val category: String
)