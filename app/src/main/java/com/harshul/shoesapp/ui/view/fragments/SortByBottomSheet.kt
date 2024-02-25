package com.harshul.shoesapp.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.harshul.shoesapp.data.models.SortBy
import com.harshul.shoesapp.databinding.BottomSheetSortByBinding
import com.harshul.shoesapp.ui.adapter.SortByAdapter
import com.harshul.shoesapp.ui.adapter.SortByListener

class SortByBottomSheet : BottomSheetDialogFragment(), SortByListener {

    companion object {
        const val SORT_KEY = "sort_key"
        const val SORT_BY = "sort_by"
    }

    private lateinit var binding: BottomSheetSortByBinding
    private val args: SortByBottomSheetArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetSortByBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sortByList = SortBy.entries
        val sortByAdapter = SortByAdapter(sortByList, args.sortBy, this)
        binding.rvSortBy.adapter = sortByAdapter

    }

    override fun onSortTypeClick(sortBy: SortBy) {
        setFragmentResult(SORT_KEY, bundleOf(SORT_BY to sortBy))
        findNavController().navigateUp()
    }

}