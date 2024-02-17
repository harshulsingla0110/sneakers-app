package com.harshul.shoesapp.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harshul.shoesapp.data.models.Shoe

@Dao
interface ShoeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoeList(shoeList: List<Shoe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoe(shoe: Shoe)

    @Query("SELECT * FROM shoes ORDER BY name ASC LIMIT :limit OFFSET :offset")
     suspend fun getPagedList(limit: Int, offset: Int) : List<Shoe>

}