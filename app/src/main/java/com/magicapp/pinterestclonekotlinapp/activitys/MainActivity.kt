package com.magicapp.pinterestclonekotlinapp.activitys

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.magicapp.pinterestclonekotlinapp.R
import com.magicapp.pinterestclonekotlinapp.fragments.HomeFragment
import com.magicapp.pinterestclonekotlinapp.fragments.MessageFragment
import com.magicapp.pinterestclonekotlinapp.fragments.ProfileFragment
import com.magicapp.pinterestclonekotlinapp.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var context: Context

    companion object {
        const val FIRST_PAGE = 1
        const val PER_PAGE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        initViews()

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val messageFragment = MessageFragment()
        val profileFragment = ProfileFragment()
        bottomBar = findViewById(R.id.bottomBar)

        setCurrentFragment(homeFragment)

        bottomBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.search -> setCurrentFragment(searchFragment)
                R.id.message -> setCurrentFragment(messageFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
            }
            true

        }

    }

//    private fun initViews(){
//        context = this
//        bottomBar = findViewById(R.id.bottomBar)
//        replaceFragment(HomeFragment())
//    }

//    private fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.main_container, fragment).commit()
//        }
//    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, fragment)
            commit()
        }
}