package com.harshul.shoesapp.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.harshul.shoesapp.R
import com.harshul.shoesapp.databinding.FragmentIntroScreenBinding
import com.harshul.shoesapp.ui.components.GoButton
import com.harshul.shoesapp.utils.orangeTheme

class IntroScreenFragment : Fragment() {

    private lateinit var binding: FragmentIntroScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIntroScreenBinding.bind(view)

        requireActivity().orangeTheme()

        binding.goButton.setContent {
            GoButton {
                findNavController().navigate(R.id.action_introScreenFragment_to_displayShoesFragment)
            }
        }

    }
}