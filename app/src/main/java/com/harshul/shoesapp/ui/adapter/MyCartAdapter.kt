package com.harshul.shoesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harshul.shoesapp.data.models.Gender
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.databinding.ItemShoeCheckoutBinding
import com.harshul.shoesapp.utils.formatToIndianCurrency
import com.harshul.shoesapp.utils.gone
import com.harshul.shoesapp.utils.visible

class MyCartAdapter(
    private val listener: MyCartListener
) : ListAdapter<Shoe, MyCartAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var toShowDeleteButton: Boolean = true

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Shoe>() {
            override fun areItemsTheSame(oldItem: Shoe, newItem: Shoe): Boolean =
                oldItem.shoeId == newItem.shoeId

            override fun areContentsTheSame(oldItem: Shoe, newItem: Shoe): Boolean =
                oldItem == newItem
        }
    }

    inner class MyViewHolder(private val binding: ItemShoeCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoeDetail: Shoe) {
            binding.apply {
                tvShoeName.text = shoeDetail.name
                val category = Gender.fromId(shoeDetail.category)
                tvShoeCurrPrice.text = shoeDetail.currPrice.formatToIndianCurrency()
                Glide.with(ivShoe.context).load(shoeDetail.imageUrl).into(ivShoe)

                ivDelete.setOnClickListener { listener.deleteShoe(shoeDetail) }

                if (toShowDeleteButton) {
                    ivDelete.visible()
                } else {
                    ivDelete.gone()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemShoeCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun hideDeleteButton() {
        toShowDeleteButton = false
    }

}

interface MyCartListener {
    fun deleteShoe(shoe: Shoe)
}