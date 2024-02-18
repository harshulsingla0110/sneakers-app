package com.harshul.shoesapp.data.models

import androidx.annotation.DrawableRes
import com.harshul.shoesapp.R

enum class Brand(val id: Int,val brandName: String, @DrawableRes val logo: Int, @DrawableRes val banner: Int) {
    NIKE(id = 1,"Nike", logo = R.drawable.ic_nike_logo, banner = R.drawable.ic_nike_banner),
    ADIDAS(id = 2,"Adidas", logo = R.drawable.ic_adidas_logo, banner = R.drawable.ic_addidas_banner),
    PUMA(id = 3, "Puma",logo = R.drawable.ic_puma_logo, banner = R.drawable.ic_puma_banner);

    companion object {
        fun fromId(id: Int): Brand {
            return entries.find { it.id == id } ?: NIKE
        }

        init {
            val ids = entries.map { it.id }
            if (ids.size != ids.toSet().size) {
                throw IllegalArgumentException("Duplicate IDs are not allowed in Brand enum")
            }
        }
    }
}