package com.s.diabetesfeeding.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragment
import com.s.diabetesfeeding.ui.home.fragment.ProfileFragment
import com.s.diabetesfeeding.ui.home.fragment.ScoreBoardFragment
import com.s.diabetesfeeding.util.setFullScreen
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var bottomNavigation: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(window)
        setContentView(R.layout.activity_home)
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeScreenFragment.newInstance("",  R.color.colorAccent))
    }


    fun openFragment(fragment: Fragment?) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(R.id.container, fragment!!)
        transaction.addToBackStack("homeFragment")
        transaction.commit()
    }

    private val navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        openFragment(HomeScreenFragment.newInstance("", R.color.colorAccent))
                        return true
                    }
                    R.id.navigation_appointment -> {
                        openFragment(HomeScreenFragment.newInstance("",  R.color.colorAccent))
                        return true
                    }
                    R.id.navigation_progress -> {
                        openFragment(HomeScreenFragment.newInstance("",  R.color.colorAccent))
                        return true
                    }
                    R.id.navigation_scoreborad -> {
                        openFragment(ScoreBoardFragment.newInstance("",   R.color.colorAccent))
                        return true
                    }
                    R.id.navigation_profile -> {
                        openFragment(ProfileFragment.newInstance("",  R.color.colorAccent))
                        return true
                    }
                }
                return false
            }
        }

}