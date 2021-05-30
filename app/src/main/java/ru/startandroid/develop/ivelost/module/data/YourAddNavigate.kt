package ru.startandroid.develop.ivelost.module.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YourAddNavigate(
    val topic: String?,
    val yourAdDistinction: Int,
    val size: Int
): Parcelable
