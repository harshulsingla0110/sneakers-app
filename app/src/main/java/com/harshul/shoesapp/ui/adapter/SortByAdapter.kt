package com.harshul.shoesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harshul.shoesapp.data.models.SortBy
import com.harshul.shoesapp.databinding.ItemSortByBinding
import com.harshul.shoesapp.utils.gone
import com.harshul.shoesapp.utils.visible

class SortByAdapter(
    private val sortByList: List<SortBy>,
    private val selectedSortBy: SortBy,
    private val listener: SortByListener
) : RecyclerView.Adapter<SortByAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemSortByBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sortItem: SortBy) {
            binding.tvSortBy.text = sortItem.displayName

            if (sortItem == selectedSortBy) {
                binding.ivCheckedMark.visible()
            } else binding.ivCheckedMark.gone()

            binding.clSortBy.setOnClickListener { listener.onSortTypeClick(sortItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSortByBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = sortByList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(sortByList[position])
    }
}

interface SortByListener {
    fun onSortTypeClick(sortBy: SortBy)
}