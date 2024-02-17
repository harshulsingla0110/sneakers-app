package com.harshul.shoesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harshul.shoesapp.data.models.Gender
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.databinding.ItemShoeBinding

class ShoeAdapter(
    private val shoesList: List<Shoe>,
    private val listener: ShoeListener
) : RecyclerView.Adapter<ShoeAdapter.MyViewHolder>() {

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

    override fun getItemCount(): Int = shoesList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(shoesList[position])
    }
}

interface ShoeListener {
    fun onShoeClick(shoeDetail: Shoe)
}