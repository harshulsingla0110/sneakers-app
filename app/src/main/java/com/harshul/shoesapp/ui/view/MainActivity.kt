package com.harshul.shoesapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.harshul.shoesapp.R
import com.harshul.shoesapp.databinding.ActivityMainBinding
import com.harshul.shoesapp.utils.gone
import com.harshul.shoesapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.navHostFragment)
        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener { /*No-OPERATION*/ }
            itemBackground = null
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.displayShoesFragment, R.id.favouriteShoesFragment -> {
                    binding.bottomNavigationView.visible()
                }

                else -> {
                    binding.bottomNavigationView.gone()
                }
            }
        }
    }
}