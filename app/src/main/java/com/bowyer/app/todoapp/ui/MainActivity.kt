package com.bowyer.app.todoapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  @Inject
  lateinit var viewModel: MainViewModel

  private lateinit var appBarConfiguration: AppBarConfiguration
  private val binding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }
  private val navController by lazy {
    supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    setSupportActionBar(binding.toolbar)
    viewModel.onCreate()
    setupNavigation()
  }

  private fun setupNavigation() {
    val drawerLayout: DrawerLayout = binding.drawerLayout
    val navView: NavigationView = binding.navView
    appBarConfiguration = AppBarConfiguration(
      topLevelDestinationIds = setOf(
        R.id.destination_add,
        R.id.destination_list,
        R.id.destination_setting
      ),
      drawerLayout = drawerLayout
    )
    setupActionBarWithNavController(navController, appBarConfiguration)
    binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)
    navView.setNavigationItemSelectedListener {
      onNavigationItemSelected(it)
    }
    setUpDrawer()
  }

  private fun setUpDrawer() {
    navController.addOnDestinationChangedListener { _, destination, _ ->
      when (destination.id) {
        R.id.destination_list, R.id.destination_setting -> {
          binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
        else -> {
          binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
      }
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }

  private fun onNavigationItemSelected(item: MenuItem): Boolean {
    binding.drawerLayout.closeDrawer(GravityCompat.START)
    return try {
      val builder = NavOptions.Builder()
        .setLaunchSingleTop(true)
      val options = builder.build()
      navController.navigate(item.itemId, null, options)
      true
    } catch (e: IllegalArgumentException) {
      false
    }
  }
}
