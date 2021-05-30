package ru.startandroid.develop.ivelost.module.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectDialogAdd(
        val typeOfAdd: Int,
        val headerText: String,
        val topic: String,
        val isLost: Boolean,
        val textViewDefaultText: String
) : Parcelable