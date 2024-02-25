package com.harshul.shoesapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SortBy(val notation: String, val displayName: String) : Parcelable {
    Popularity("rating DESC", "Popularity"),
    CurrPriceAsc("currPrice ASC", "Price -- Low to High"),
    CurrPriceDsc("currPrice DESC", "Price -- High to Low"),
    NewestFirst("year DESC", "Newest First")
}