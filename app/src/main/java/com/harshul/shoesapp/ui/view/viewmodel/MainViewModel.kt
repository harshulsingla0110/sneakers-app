package com.harshul.shoesapp.ui.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.harshul.shoesapp.data.db.ShoeDao
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.data.repos.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: ShoeDao,
    private val repository: MainRepository
) : ViewModel() {

    init {
        viewModelScope.launch { repository.shoesApiCall() }
    }

    val shoesPagingData = repository.getShoesPagingData().cachedIn(viewModelScope)

    // Define a mutable state flow for the search query
    val searchQuery = MutableStateFlow("")

    // Debounce the search query changes
    @OptIn(FlowPreview::class)
    private val debouncedSearchQuery: Flow<String> = searchQuery.debounce(1000)

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchFlow = debouncedSearchQuery.flatMapLatest { query ->
        val newPager = Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
                initialLoadSize = 4
            ),
            pagingSourceFactory = { dao.searchNotes("%$query%") }
        )
        newPager.flow.cachedIn(viewModelScope)
    }

    fun addToCart(shoe: Shoe) {
        viewModelScope.launch {
            repository.addToCart(shoe)
        }
    }

    fun removeFromCart(shoe: Shoe) {
        viewModelScope.launch {
            repository.removeFromCart(shoe)
        }
    }

    fun getCartShoesData() = repository.getCartShoes()

    fun getShoe(shoeId: Int) = repository.getShoe(shoeId)
}