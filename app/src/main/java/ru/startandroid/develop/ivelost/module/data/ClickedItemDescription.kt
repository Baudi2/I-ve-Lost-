package ru.startandroid.develop.ivelost.module.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClickedItemDescription(
    val header: String,
    val description: String,
    val location: String,
    val northPoint: Double,
    val eastPoint: Double,
    val adType: String,
    val adTypeObject: String,
    val category: String
) : Parcelable