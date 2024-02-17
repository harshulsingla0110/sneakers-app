package com.harshul.shoesapp.data.repos

import com.harshul.shoesapp.data.db.ShoeDao
import com.harshul.shoesapp.data.models.Shoe
import javax.inject.Inject

class MainRepository @Inject constructor(private val shoesDAO: ShoeDao) {

    suspend fun insertShoes(shoesList: List<Shoe>) = shoesDAO.insertShoeList(shoesList)

}