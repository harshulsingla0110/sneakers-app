package com.harshul.shoesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harshul.shoesapp.data.models.Gender
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.databinding.ItemShoeBinding

class ShoeAdapter(
    private val listener: ShoeListener
) : PagingDataAdapter<Shoe, ShoeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Shoe>() {
            override fun areItemsTheSame(oldItem: Shoe, newItem: Shoe): Boolean =
                oldItem.shoeId == newItem.shoeId

            override fun areContentsTheSame(oldItem: Shoe, newItem: Shoe): Boolean =
                oldItem == newItem
        }
    }

    inner class MyViewHolder(private val binding: ItemShoeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoeDetail: Shoe) {
            binding.apply {
                tvShoeName.text = shoeDetail.name
                val category = Gender.fromId(shoeDetail.category)
                tvShoeCategory.text = category.tag
                tvRating.text = String.format("⭐ %.1f", shoeDetail.rating)
                tvShoeCurrPrice.text = shoeDetail.currPrice.toString()
                Glide.with(ivShoe.context).load(shoeDetail.imageUrl).into(ivShoe)

                clRoot.setOnClickListener { listener.onShoeClick(shoeDetail) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemShoeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

interface ShoeListener {
    fun onShoeClick(shoeDetail: Shoe)
}