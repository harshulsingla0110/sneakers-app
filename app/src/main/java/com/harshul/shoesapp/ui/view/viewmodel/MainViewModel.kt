package com.harshul.shoesapp.ui.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.data.models.UiState
import com.harshul.shoesapp.data.repos.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<UiState<Unit>> = MutableLiveData()
    val uiState: LiveData<UiState<Unit>> = _uiState

    init {
        viewModelScope.launch {
           val response = repository.shoesApiCall()
            _uiState.value = response
        }
    }

    val searchQuery = MutableStateFlow("")
    val searchQueryFlow = repository.getShoesQueryData(searchQuery).cachedIn(viewModelScope)

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