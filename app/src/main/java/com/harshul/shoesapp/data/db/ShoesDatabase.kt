package com.harshul.shoesapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harshul.shoesapp.data.models.Shoe

@Database(entities = [Shoe::class], version = 1)
abstract class ShoesDatabase : RoomDatabase() {

    abstract fun getShoesDao(): ShoeDao

}