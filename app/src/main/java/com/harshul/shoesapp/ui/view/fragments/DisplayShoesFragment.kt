package com.harshul.shoesapp.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.harshul.shoesapp.R
import com.harshul.shoesapp.data.models.Brand
import com.harshul.shoesapp.data.models.Shoe
import com.harshul.shoesapp.databinding.FragmentDisplayShoesBinding
import com.harshul.shoesapp.ui.adapter.ShoeAdapter
import com.harshul.shoesapp.ui.adapter.ShoeListener
import com.harshul.shoesapp.utils.lightTheme
import com.harshul.shoesapp.utils.setSpannableTxt
import kotlin.random.Random

class DisplayShoesFragment : Fragment(), ShoeListener {

    private lateinit var binding: FragmentDisplayShoesBinding

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

        binding.rvShoes.adapter = ShoeAdapter(dummyData(), this)
//        binding.rvShoes.apply {
//            layoutManager = GridLayoutManager(context, 2)
//        }

    }

    //todo: to be removed
    private fun dummyData(): MutableList<Shoe> {
        val dummyShoes = mutableListOf<Shoe>()

        val imagesList = listOf(
            "https://i.ibb.co/5hCfpCP/1-nike.png",
            "https://i.ibb.co/xqNPyVg/22-addidas.png",
            "https://i.ibb.co/xXx8YHc/15-puma.png",
            "https://i.ibb.co/606WD8s/2-nike.png",
            "https://i.ibb.co/v3CSRXy/3-nike.png",
            "https://i.ibb.co/7ndGwZN/16-puma.png",
            "https://i.ibb.co/7z9KTV9/19-addidas.png",
            "https://i.ibb.co/r5PVkBb/4-nike.png",
            "https://i.ibb.co/7xc51CH/5-nike.png",
            "https://i.ibb.co/KjTvcS6/17-puma.png",
            "https://i.ibb.co/v3CSRXy/3-nike.png",
            "https://i.ibb.co/yyTrjDG/7-nike.png",
            "https://i.ibb.co/Pmbbh3p/8-nike.png",
            "https://i.ibb.co/10QjQ4c/21-addidas.png",
            "https://i.ibb.co/yfNXvtF/20-addidas.png",
            "https://i.ibb.co/yhVtXQG/9-nike.png",
            "https://i.ibb.co/fd1nWtx/10-nike.png",
            "https://i.ibb.co/w0tQFTY/11-nike.png",
            "https://i.ibb.co/zNMvRM6/12-nike.png",
            "https://i.ibb.co/5vs1ZnP/18-puma.png",
            "https://i.ibb.co/Hn0QZyc/13-nike.png",
            "https://i.ibb.co/Wyxzwkt/14-nike.png",
        )

        imagesList.forEachIndexed { index, shoeUrl ->
            val shoeBrand =
                if (shoeUrl.contains("nike")) Brand.NIKE else if (shoeUrl.contains("addidas")) Brand.ADIDAS else Brand.PUMA
            val currPrice = (3000..20000).random()
            dummyShoes.add(
                Shoe(
                    name = "${shoeBrand.name} $index",
                    category = (1..3).random(),
                    brand = shoeBrand.id,
                    currPrice = currPrice,
                    actualPrice = (currPrice * Random.nextDouble(1.0, 2.5)).toInt(),
                    rating = Random.nextDouble(3.0, 5.0),
                    imageUrl = shoeUrl
                )
            )
        }

        return dummyShoes
    }

    override fun onShoeClick(shoeDetail: Shoe) {
        val action =
            DisplayShoesFragmentDirections.actionDisplayShoesFragmentToShoeDetailFragment(shoeDetail)
        findNavController().navigate(action)
    }

}