package com.harshul.shoesapp.data.repos

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.harshul.shoesapp.data.db.ShoeDao
import com.harshul.shoesapp.data.models.Brand
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.data.pagination.ShoesPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainRepositoryImpl(private val shoesDAO: ShoeDao) : MainRepository {

    override suspend fun shoesApiCall() = withContext(Dispatchers.IO) {
        val shoesList = shoesDataSource()
        shoesDAO.insertShoeList(shoesList)
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
        "https://i.ibb.co/v3CSRXy/3-nike.png",
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

    imagesList.forEachIndexed { index, shoeUrl ->

        val shoeBrand = if (shoeUrl.contains("nike")) {
            Brand.NIKE
        } else if (shoeUrl.contains("addidas")) {
            Brand.ADIDAS
        } else Brand.PUMA
        val currPrice = (3000..20000).random()
        shoesDataList.add(
            Shoe(
                shoeId = index + 1,
                name = "${shoeBrand.name} $index",
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