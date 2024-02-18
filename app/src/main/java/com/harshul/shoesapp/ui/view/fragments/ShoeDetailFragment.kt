package com.harshul.shoesapp.ui.view.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.harshul.shoesapp.R
import com.harshul.shoesapp.data.models.Brand
import com.harshul.shoesapp.data.models.Gender
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.databinding.FragmentShoeDetailBinding
import com.harshul.shoesapp.ui.view.viewmodel.MainViewModel
import com.harshul.shoesapp.utils.formatToIndianCurrency

class ShoeDetailFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding
    private val args: ShoeDetailFragmentArgs by navArgs()
    private val viewModel: MainViewModel by activityViewModels()
    private var shoeItem: Shoe? = null

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

        viewModel.getShoe(args.shoeId).observe(viewLifecycleOwner) { shoeData ->
            shoeItem = shoeData
            binding.updateUi(shoeDetail = shoeData)
        }

        viewModel.getCartShoesData().observe(viewLifecycleOwner) {
            binding.tvCartCount.text = (it?.size ?: 0).toString()
        }

        binding.btnCart.setOnClickListener {
            shoeItem?.let {
                if (it.isCartAdded) {
                    findNavController().navigate(R.id.action_shoeDetailFragment_to_checkoutFragment)
                } else {
                    shoeItem = it.copy(isCartAdded = true)
                    viewModel.addToCart(shoe = shoeItem!!)
                    Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.ivCart.setOnClickListener {
            findNavController().navigate(R.id.action_shoeDetailFragment_to_checkoutFragment)
        }

        binding.btnBuyNow.setOnClickListener {
            shoeItem = shoeItem?.copy(isCartAdded = true)
            viewModel.addToCart(shoe = shoeItem!!)
            val action =
                ShoeDetailFragmentDirections.actionShoeDetailFragmentToCheckoutFragment(buyNowShoe = shoeItem)
            findNavController().navigate(action)
        }
    }
}

private fun FragmentShoeDetailBinding.updateUi(shoeDetail: Shoe) {
    tvShoeName.text = shoeDetail.name
    val category = Gender.fromId(shoeDetail.category)
    tvShoeCategory.text = category.tag
    tvRating.text = String.format("⭐ %.1f", shoeDetail.rating)
    tvShoeCurrPrice.text = shoeDetail.currPrice.formatToIndianCurrency()
    tvShoeActPrice.apply {
        text = shoeDetail.actualPrice.formatToIndianCurrency()
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    btnCart.apply {
        text = if (shoeDetail.isCartAdded) {
            getString(context, R.string.view_cart)
        } else getString(context, R.string.add_to_cart)
    }

    val discount = (shoeDetail.actualPrice - shoeDetail.currPrice) * 100 / shoeDetail.actualPrice
    tvShoeDiscount.apply {
        text = context.getString(R.string.discount_perc_off, discount)
    }

    tvShoeDate.apply {
        text = context.getString(R.string.release_year, shoeDetail.year)
    }

    Glide.with(ivShoe.context).load(shoeDetail.imageUrl).into(ivShoe)

    val brand = Brand.fromId(shoeDetail.brand)
    Glide.with(ivShoeBrandLogo.context).load(brand.logo).into(ivShoeBrandLogo)
    Glide.with(ivShoeBrand.context).load(brand.banner).into(ivShoeBrand)
}