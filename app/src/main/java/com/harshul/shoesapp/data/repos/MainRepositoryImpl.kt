package com.harshul.shoesapp.data.repos

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.harshul.shoesapp.data.db.ShoeDao
import com.harshul.shoesapp.data.models.Brand
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.data.models.UiState
import com.harshul.shoesapp.data.pagination.ShoesPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.random.Random

class MainRepositoryImpl(private val shoesDAO: ShoeDao) : MainRepository {

    override suspend fun shoesApiCall(): UiState<Unit> = withContext(Dispatchers.IO) {
        try {
            val shoesList = shoesDataSource()
            shoesDAO.insertShoeList(shoesList)
            UiState.Success(Unit)
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> UiState.Error("Invalid query parameter supplied")
                500 -> UiState.Error("Unexpected error")
                else -> UiState.Error("Unexpected error, retry after some time")
            }
        } catch (e: Exception) {
            UiState.Error("Network Request failed")
        }
    }

    override fun getShoesPagingData(): Flow<PagingData<Shoe>> = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 4
        ),
    ) {
        ShoesPagingSource(shoesDAO)
    }.flow

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getShoesQueryData(searchQuery: MutableStateFlow<String>): Flow<PagingData<Shoe>> =
        searchQuery.flatMapLatest { query ->
            val newPager = Pager(
                config = PagingConfig(
                    pageSize = 2,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { shoesDAO.searchNotes("%$query%") }
            )
            newPager.flow
        }

    override suspend fun addToCart(shoe: Shoe) = withContext(Dispatchers.IO) {
        shoesDAO.updateShoe(shoe)
    }

    override suspend fun removeFromCart(shoe: Shoe) = withContext(Dispatchers.IO) {
        shoesDAO.updateShoe(shoe)
    }

    override fun getCartShoes(): LiveData<List<Shoe>> = shoesDAO.getCartShoesData()

    override fun getShoe(shoeId: Int): LiveData<Shoe> = shoesDAO.getShoes(shoeId)

}

private fun shoesDataSource(): MutableList<Shoe> {
    val shoesDataList = mutableListOf<Shoe>()

    val imagesList = listOf(
        "https://i.ibb.co/5hCfpCP/1-nike.png",
        "https://i.ibb.co/xqNPyVg/22-addidas.png",
        "https://i.ibb.co/xXx8YHc/15-puma.png",
        "https://i.ibb.co/606WD8s/2-nike.png",
        "https://i.ibb.co/7ndGwZN/16-puma.png",
        "https://i.ibb.co/7z9KTV9/19-addidas.png",
        "https://i.ibb.co/r5PVkBb/4-nike.png",
        "https://i.ibb.co/7xc51CH/5-nike.png",
        "https://i.ibb.co/KjTvcS6/17-puma.png",
        "https://i.ibb.co/yyTrjDG/7-nike.png",
        "https://i.ibb.co/Pmbbh3p/8-nike.png",
        "https://i.ibb.co/10QjQ4c/21-addidas.png",
        "https://i.ibb.co/yfNXvtF/20-addidas.png",
        "https://i.ibb.co/yhVtXQG/9-nike.png",
        "https://i.ibb.co/fd1nWtx/10-nike.png",
        "https://i.ibb.co/w0tQFTY/11-nike.png",
        "https://i.ibb.co/zNMvRM6/12-nike.png",
        "https://i.ibb.co/5vs1ZnP/18-puma.png",
        "https://i.ibb.co/Hn0QZyc/13-nike.png",
        "https://i.ibb.co/Wyxzwkt/14-nike.png"
    )

    val nameKeywords = listOf(
        "Boost", "Air", "Max", "Zoom", "Ultra", "Flex", "Elite", "Swift", "Run",
        "Force", "Tech", "Speed", "Fusion", "Fly", "Lite", "Runner", "Pro", "Blaze", "Wave", "Cloud"
    )

    imagesList.forEachIndexed { index, shoeUrl ->

        val shoeBrand = if (shoeUrl.contains("nike")) {
            Brand.NIKE
        } else if (shoeUrl.contains("addidas")) {
            Brand.ADIDAS
        } else Brand.PUMA

        val currPrice = (3000..20000).random()
        val nameKeywordShuffled = nameKeywords.shuffled()

        shoesDataList.add(
            Shoe(
                shoeId = index + 1,
                name = "${shoeBrand.brandName} ${nameKeywordShuffled[0]} ${nameKeywordShuffled[1]}",
                category = (1..3).random(),
                brand = shoeBrand.id,
                currPrice = currPrice,
                actualPrice = (currPrice * Random.nextDouble(1.0, 2.5)).toInt(),
                rating = Random.nextDouble(3.0, 5.0),
                imageUrl = shoeUrl,
                year = (2000..2024).random()
            )
        )
    }

    return shoesDataList
}