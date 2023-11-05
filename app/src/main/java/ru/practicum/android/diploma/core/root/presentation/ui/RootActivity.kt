package ru.practicum.android.diploma.core.root.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        // Пример использования access token для HeadHunter API
        //networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.filterFragment,
                R.id.filterLocationFragment,
                R.id.locationCountryFragment,
                R.id.locationRegionFragment,
                R.id.similarFragment -> showBottomNavigationBar(
                    isVisible = false
                )

                else -> showBottomNavigationBar(isVisible = true)
            }

        }

    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    private fun showBottomNavigationBar(isVisible: Boolean) {
        val viewVisibility = if (isVisible) View.VISIBLE else View.GONE
        binding.bottomNavigationView.visibility = viewVisibility
        binding.greyLine.visibility = viewVisibility
    }

}