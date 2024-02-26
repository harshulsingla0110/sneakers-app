package com.harshul.shoesapp.data.models

enum class Gender(val id: Int, val tag: String) {
    MAN(id = 1, tag = "Men's Shoes"),
    WOMEN(id = 2, tag = "Women's Shoes"),
    UNISEX(id = 3, tag = "Unisex Shoes");

    companion object {
        fun fromId(id: Int): Gender {
            return entries.find { it.id == id } ?: run {
                throw IllegalArgumentException("No value matching supplied id = $id")
            }
        }

        init {
            val ids = entries.map { it.id }
            if (ids.size != ids.toSet().size) {
                throw IllegalArgumentException("Duplicate IDs are not allowed in Gender enum")
            }
        }
    }
}