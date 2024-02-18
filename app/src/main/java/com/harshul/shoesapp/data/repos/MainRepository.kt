package com.harshul.shoesapp.data.repos

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.data.models.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface MainRepository {

    suspend fun shoesApiCall() : UiState<Unit>

    fun getShoesPagingData(): Flow<PagingData<Shoe>>

    fun getShoesQueryData(searchQuery: MutableStateFlow<String>): Flow<PagingData<Shoe>>

    suspend fun addToCart(shoe: Shoe)

    suspend fun removeFromCart(shoe: Shoe)

    fun getCartShoes(): LiveData<List<Shoe>>

    fun getShoe(shoeId: Int): LiveData<Shoe>
}