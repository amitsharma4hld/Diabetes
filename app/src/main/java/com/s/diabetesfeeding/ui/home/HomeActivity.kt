package com.s.diabetesfeeding.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragment
import com.s.diabetesfeeding.ui.profile.ProfileFragment
import com.s.diabetesfeeding.ui.scoreboard.ScoreBoardFragment
import com.s.diabetesfeeding.util.setFullScreen
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(window)
        setContentView(R.layout.activity_home)

        val navController = Navigation.findNavController(this,R.id.container)
        NavigationUI.setupWithNavController(bottom_navigation,navController)
        setupBottomNavMenu(navController)


    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this,R.id.container),
            null
        )
    }

}