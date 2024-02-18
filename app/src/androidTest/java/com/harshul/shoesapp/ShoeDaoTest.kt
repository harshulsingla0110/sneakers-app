package com.harshul.shoesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harshul.shoesapp.data.db.ShoeDao
import com.harshul.shoesapp.data.db.ShoesDatabase
import com.harshul.shoesapp.data.models.Shoe
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ShoeDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var shoesDatabase: ShoesDatabase
    lateinit var shoeDao: ShoeDao

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        shoeDao = shoesDatabase.getShoesDao()
    }

    @Test
    fun insertShoes_expectedTwoShoes() = runBlocking {
        val shoeOne = Shoe(shoeId = 1, name = "Adidas", category = 3, brand = 1, currPrice = 100, actualPrice = 140, rating = 4.7, imageUrl = "url_one", isCartAdded = false, isFavourite = true, year = 2001)
        val shoeTwo = Shoe(shoeId = 2, name = "Nike", category = 1, brand = 2, currPrice = 300, actualPrice = 270, rating = 3.4, imageUrl = "url_two", isCartAdded = true, isFavourite = true, year = 2008)

        shoeDao.insertShoeList(listOf(shoeOne, shoeTwo))

        val result = shoeDao.getAllShoes().getOrAwaitValue()

        assertEquals(2, result.size)
    }

    @Test
    fun insertShoe_expectedSingleShoe() = runBlocking {
        val shoe = Shoe(shoeId = 1, name = "Adidas", category = 3, brand = 1, currPrice = 100, actualPrice = 140, rating = 4.7, imageUrl = "url_one", isCartAdded = false, isFavourite = true, year = 2001)
        shoeDao.insertShoe(shoe)

        val result = shoeDao.getAllShoes().getOrAwaitValue()

        assertEquals(1, result.size)
        assertEquals(shoe.actualPrice, result[0].actualPrice)
    }

    @Test
    fun updateShoe_expectedUpdatedValue() = runBlocking {
        val shoe = Shoe(shoeId = 1, name = "Adidas", category = 3, brand = 1, currPrice = 100, actualPrice = 140, rating = 4.7, imageUrl = "url_one", isCartAdded = false, isFavourite = true, year = 2001)
        shoeDao.insertShoe(shoe)

        val updatedPrice = 78
        shoeDao.updateShoe(shoe.copy(currPrice = updatedPrice))
        val result = shoeDao.getAllShoes().getOrAwaitValue()

        assertEquals(updatedPrice, result[0].currPrice)
    }

    @Test
    fun getShoe_fromId_expectedSingleValue() = runBlocking {
        val shoeId = 100
        val shoeName = "Adidas Ultra Blaze"

        val shoe = Shoe(shoeId = shoeId, name = shoeName, category = 3, brand = 1, currPrice = 100, actualPrice = 140, rating = 4.7, imageUrl = "url_one", isCartAdded = false, isFavourite = true, year = 2001)
        shoeDao.insertShoe(shoe)

        val result = shoeDao.getShoes(shoeId).getOrAwaitValue()
        assertEquals(shoeId, result.shoeId)
        assertEquals(shoeName, result.name)
    }

    @Test
    fun getShoes_addedToCart_expectedSingleValue() = runBlocking {
        val shoeOne = Shoe(shoeId = 1, name = "Adidas", category = 3, brand = 1, currPrice = 100, actualPrice = 140, rating = 4.7, imageUrl = "url_one", isCartAdded = false, isFavourite = true, year = 2001)
        val shoeTwo = Shoe(shoeId = 2, name = "Nike", category = 1, brand = 2, currPrice = 300, actualPrice = 270, rating = 3.4, imageUrl = "url_two", isCartAdded = true, isFavourite = true, year = 2008)

        shoeDao.insertShoeList(listOf(shoeOne, shoeTwo))

        val result = shoeDao.getCartShoesData().getOrAwaitValue()
        assertEquals(1, result.size)
    }

    @After
    fun tearDown() {
        shoesDatabase.close()
    }

}