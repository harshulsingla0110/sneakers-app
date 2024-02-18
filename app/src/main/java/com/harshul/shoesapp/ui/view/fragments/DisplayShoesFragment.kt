package com.harshul.shoesapp.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.harshul.shoesapp.R
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.data.models.UiState
import com.harshul.shoesapp.data.pagination.ShoesLoadStateAdapter
import com.harshul.shoesapp.databinding.FragmentDisplayShoesBinding
import com.harshul.shoesapp.ui.adapter.ShoeAdapter
import com.harshul.shoesapp.ui.adapter.ShoeListener
import com.harshul.shoesapp.ui.view.viewmodel.MainViewModel
import com.harshul.shoesapp.utils.lightTheme
import com.harshul.shoesapp.utils.setSpannableTxt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DisplayShoesFragment : Fragment(), ShoeListener {

    private lateinit var binding: FragmentDisplayShoesBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_shoes, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDisplayShoesBinding.bind(view)

        requireActivity().lightTheme()
        binding.tvName.setSpannableTxt(getString(R.string.hello), R.color.light_grey, 4, 7)

        val shoeAdapter = ShoeAdapter(listener = this)
        binding.rvShoes.adapter = shoeAdapter.withLoadStateFooter(ShoesLoadStateAdapter())

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> Unit // Loading state.. show loader
                is UiState.Success -> Unit // Success state
                is UiState.Error -> {
                    //Can show user error message
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchQueryFlow.collectLatest {
                    shoeAdapter.submitData(it)
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchQuery.value = it }
                return true
            }
        })

    }

    override fun onShoeClick(shoeDetail: Shoe) {
        val action =
            DisplayShoesFragmentDirections.actionDisplayShoesFragmentToShoeDetailFragment(shoeDetail.shoeId)
        findNavController().navigate(action)
    }

}