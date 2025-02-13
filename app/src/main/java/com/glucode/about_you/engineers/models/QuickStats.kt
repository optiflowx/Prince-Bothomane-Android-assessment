package com.glucode.about_you.engineers.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuickStats(
    val years: Int,
    val coffees: Int,
    val bugs: Int
) : Parcelable