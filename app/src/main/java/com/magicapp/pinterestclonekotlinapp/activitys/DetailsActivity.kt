package com.magicapp.pinterestclonekotlinapp.activitys

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.magicapp.pinterestclonekotlinapp.R
import com.magicapp.pinterestclonekotlinapp.adapters.DetailsAdapter
import com.magicapp.pinterestclonekotlinapp.adapters.PagerAdapter
import com.magicapp.pinterestclonekotlinapp.fragments.DetailsFragment
import com.magicapp.pinterestclonekotlinapp.models.PhotoElements
import com.magicapp.pinterestclonekotlinapp.models.PhotoList
import java.lang.reflect.Type

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)
        initViews()
    }
    private fun initViews(){
        val vpDetails = findViewById<ViewPager>(R.id.vp_detail)
        refreshAdapter(vpDetails, getList(), getPosition())
    }

    private fun refreshAdapter(viewPager: ViewPager, photoElements: ArrayList<PhotoElements>, position: Int) {
        val adapter = PagerAdapter(supportFragmentManager)
        for (photoItem in photoElements) {
            adapter.addFragment(DetailsFragment(photoItem))
        }
        viewPager.adapter = adapter
        viewPager.currentItem = position
    }
    private fun getList():ArrayList<PhotoElements> {
        val json = intent.getStringExtra("photoList")
        val type: Type = object : TypeToken<ArrayList<PhotoElements>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun getPosition(): Int {
        return intent.getIntExtra("position", 0)
    }

    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = this.window.decorView
            decor.systemUiVisibility = 0
        }
        window.statusBarColor = Color.BLACK
    }

}