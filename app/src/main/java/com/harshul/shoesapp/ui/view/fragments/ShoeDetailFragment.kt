package com.harshul.shoesapp.ui.view.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.harshul.shoesapp.R
import com.harshul.shoesapp.data.models.Brand
import com.harshul.shoesapp.data.models.Gender
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.databinding.FragmentShoeDetailBinding

class ShoeDetailFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding
    private val args: ShoeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shoe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentShoeDetailBinding.bind(view)

        args.shoeDetail?.let { binding.updateUi(it) }
    }
}

private fun FragmentShoeDetailBinding.updateUi(shoeDetail: Shoe) {
    tvShoeName.text = shoeDetail.name
    val category = Gender.fromId(shoeDetail.category)
    tvShoeCategory.text = category.tag
    tvRating.text = String.format("⭐ %.1f", shoeDetail.rating)
    tvShoeCurrPrice.text = shoeDetail.currPrice.toString()
    tvShoeActPrice.apply {
        text = shoeDetail.actualPrice.toString()
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    val discount = (shoeDetail.actualPrice - shoeDetail.currPrice) * 100 / shoeDetail.actualPrice
    tvShoeDiscount.text = "$discount%OFF"

    Glide.with(ivShoe.context).load(shoeDetail.imageUrl).into(ivShoe)

    val brand = Brand.fromId(shoeDetail.brand)
    Glide.with(ivShoeBrandLogo.context).load(brand.logo).into(ivShoeBrandLogo)
    Glide.with(ivShoeBrand.context).load(brand.banner).into(ivShoeBrand)
}