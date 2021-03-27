package com.nytimes.popular_articles.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.islamassem.utils.base.BaseActivity
import com.nytimes.popular_articles.R
import com.nytimes.popular_articles.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun doBinding() {
        dataBinding = DataBindingUtil.setContentView(this_, R.layout.activity_main)
    }

    override fun initViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = navHostFragment.navController
        setSupportActionBar(dataBinding.appBarMain.toolbar)
        appBarConfiguration = AppBarConfiguration(
            navController.graph, dataBinding.drawerLayout
        )

        dataBinding.navView.setupWithNavController(navController) //the second most important part
        dataBinding.appBarMain.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun saveInstanceState(savedInstanceState: Bundle) {
    }

    override fun initData(data: Bundle) {
    }

    override fun initVariables(context: Context) {
    }

    override fun initViewModel() {
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}