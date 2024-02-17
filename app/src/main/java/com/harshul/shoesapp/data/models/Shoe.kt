package com.harshul.shoesapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shoe(
    val name: String,
    val category: Int,
    val brand: Int,
    val currPrice: Int,
    val actualPrice: Int,
    val rating: Double,
    val imageUrl: String
) : Parcelable
