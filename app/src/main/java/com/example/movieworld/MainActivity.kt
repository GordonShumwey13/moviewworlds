package com.example.movieworld

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movieworld.databinding.ActivityMainBinding
import androidx.core.view.GravityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawer = binding.drawerLayout
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.filmsFragment,
                R.id.favoritesFragment,
                R.id.ratingsFragment,
                R.id.helpFragment
            ),
            drawer
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.appBarMain.toolbar.title = destination.label
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_films -> navController.navigate(R.id.filmsFragment)
                R.id.nav_favorites -> navController.navigate(R.id.favoritesFragment)
            }
            true
        }

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.drawer_films -> navController.navigate(R.id.filmsFragment)
                R.id.drawer_favorites -> navController.navigate(R.id.favoritesFragment)
                R.id.drawer_ratings -> navController.navigate(R.id.ratingsFragment)
                R.id.drawer_help -> navController.navigate(R.id.helpFragment)
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return when (item.itemId) {

            R.id.toolbar_ratings -> {
                navController.navigate(R.id.ratingsFragment)
                true
            }

            R.id.toolbar_help -> {
                navController.navigate(R.id.helpFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
