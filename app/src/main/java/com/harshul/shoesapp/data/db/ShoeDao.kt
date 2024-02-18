package com.harshul.shoesapp.data.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.harshul.shoesapp.data.models.Shoe

@Dao
interface ShoeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShoeList(shoeList: List<Shoe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoe(shoe: Shoe)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateShoe(shoe: Shoe)

    @Query("SELECT * FROM shoes ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<Shoe>

    @Query("SELECT * FROM shoes WHERE name LIKE :query ORDER BY name ASC")
    fun searchNotes(query: String): PagingSource<Int, Shoe>

    @Query("SELECT * FROM shoes WHERE shoeId = :shoeId")
    fun getShoes(shoeId: Int): LiveData<Shoe>

    @Query("SELECT * FROM shoes WHERE isCartAdded = 1")
    fun getCartShoesData(): LiveData<List<Shoe>>

    @Query("SELECT * FROM shoes WHERE isFavourite = 1")
    fun getFavouriteShoesData(): LiveData<List<Shoe>>

}