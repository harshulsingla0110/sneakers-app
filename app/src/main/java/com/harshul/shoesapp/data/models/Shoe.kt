package com.harshul.shoesapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "shoes")
@Parcelize
data class Shoe(
    @PrimaryKey val shoeId: Int,
    val name: String,
    val category: Int,
    val brand: Int,
    val currPrice: Int,
    val actualPrice: Int,
    val rating: Double,
    val imageUrl: String,
    val isCartAdded: Boolean = false,
    val isFavourite: Boolean = false,
    val year: Int
) : Parcelable
