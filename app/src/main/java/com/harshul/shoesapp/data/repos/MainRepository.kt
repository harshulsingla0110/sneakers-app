package com.harshul.shoesapp.data.repos

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.harshul.shoesapp.data.models.Shoe
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun shoesApiCall()

    fun getShoesPagingData(): Flow<PagingData<Shoe>>

    suspend fun addToCart(shoe: Shoe)

    suspend fun removeFromCart(shoe: Shoe)

    fun getCartShoes(): LiveData<List<Shoe>>

    fun getShoe(shoeId: Int): LiveData<Shoe>
}