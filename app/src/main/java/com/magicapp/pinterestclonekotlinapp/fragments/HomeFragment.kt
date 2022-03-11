package com.magicapp.pinterestclonekotlinapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.magicapp.pinterestclonekotlinapp.R
import com.magicapp.pinterestclonekotlinapp.helper.SpaceItemDecoration
import com.magicapp.pinterestclonekotlinapp.adapters.HomeAdapter
import com.magicapp.pinterestclonekotlinapp.models.Home
import com.magicapp.pinterestclonekotlinapp.models.PhotoList
import com.magicapp.pinterestclonekotlinapp.networks.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var adapter : HomeAdapter
    private var currentPage = 1
    private var perPage = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiPhotoList()
    }
    private fun initViews(view: View){
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val decoration = SpaceItemDecoration(10)
        recyclerView.addItemDecoration(decoration)
        refreshAdapter(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    apiPhotoList()
                }
            }
        })

    }

    private fun refreshAdapter(recyclerView: RecyclerView) {
        adapter = HomeAdapter(requireContext())
        recyclerView!!.adapter = adapter
    }

    private fun apiPhotoList() {
        RetrofitHttp.photoService.getPhotos(currentPage++, perPage)
            .enqueue(object : Callback<PhotoList> {
                override fun onResponse(call: Call<PhotoList>, response: Response<PhotoList>) {
                    adapter.addPhotos(response.body()!!)
                }

                override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                    Log.e("@@@", t.toString())
                }
            })
    }

}