package com.harshul.shoesapp.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.util.fastSumBy
import androidx.core.content.ContextCompat.getString
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.harshul.shoesapp.R
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.databinding.FragmentCheckoutBinding
import com.harshul.shoesapp.ui.adapter.MyCartAdapter
import com.harshul.shoesapp.ui.adapter.MyCartListener
import com.harshul.shoesapp.ui.view.viewmodel.MainViewModel
import com.harshul.shoesapp.utils.formatToIndianCurrency

class CheckoutFragment : Fragment(), MyCartListener {

    private lateinit var binding: FragmentCheckoutBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: CheckoutFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckoutBinding.bind(view)

        val adapter = MyCartAdapter(listener = this)
        binding.rvShoes.adapter = adapter

        args.buyNowShoe?.let {
            adapter.hideDeleteButton()
            adapter.submitList(listOf(it))
            binding.cartFilled(isFromBuyNow = true, grandTotal = it.currPrice)
        } ?: run {
            viewModel.getCartShoesData().observe(viewLifecycleOwner) {
                adapter.submitList(it)
                val cartSize = it.size
                if (cartSize == 0) {
                    binding.cartFilled(isCartFilled = false)
                } else {
                    val grandTotal = it.fastSumBy { item -> item.currPrice }
                    binding.cartFilled(grandTotal = grandTotal)
                }
            }
        }

        binding.btnBrowse.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutFragment_to_displayShoesFragment)
        }
        
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun deleteShoe(shoe: Shoe) {
        viewModel.removeFromCart(shoe.copy(isCartAdded = false))
    }

}

private fun FragmentCheckoutBinding.cartFilled(
    isCartFilled: Boolean = true, isFromBuyNow: Boolean = false, grandTotal: Int? = null
) {
    clFilledCart.visibility = if (isCartFilled) View.VISIBLE else View.GONE
    clEmptyCart.visibility = if (!isCartFilled) View.VISIBLE else View.GONE
    tvGrandTotal.text = grandTotal?.formatToIndianCurrency() ?: "-"
    btnCart.apply {
        text = getString(
            context, if (isFromBuyNow) R.string.proceed_to_payment else R.string.proceed_to_checkout
        )
    }
}