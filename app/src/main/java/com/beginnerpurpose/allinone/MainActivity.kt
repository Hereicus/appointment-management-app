package com.beginnerpurpose.allinone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.beginnerpurpose.allinone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var firstAppBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.customToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        homeMadeFindNavController(R.id.fragmentContainer)
        binding.navigationView.setupWithNavController(navController)

        drawerLayout = binding.drawerLayout
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        firstAppBarConfiguration = AppBarConfiguration.Builder(R.id.homeScreen, R.id.login).setOpenableLayout(drawerLayout).build()
        setupActionBarWithNavController(navController, firstAppBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(firstAppBarConfiguration)
    }

    private fun homeMadeFindNavController(fragmentId: Int){
        val navHostFragment = supportFragmentManager.findFragmentById(fragmentId) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun updateNavigationMenu(menuResId: Int) {
        binding.navigationView.menu.clear()
        binding.navigationView.inflateMenu(menuResId)
    }

    fun setToolbarInvisible(toolbarId: Int){
        binding.customToolbar.isVisible = false
    }
}