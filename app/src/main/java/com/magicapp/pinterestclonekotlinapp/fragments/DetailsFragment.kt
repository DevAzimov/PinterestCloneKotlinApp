package com.magicapp.pinterestclonekotlinapp.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.imageview.ShapeableImageView
import com.magicapp.pinterestclonekotlinapp.R
import com.magicapp.pinterestclonekotlinapp.activitys.DetailsActivity
import com.magicapp.pinterestclonekotlinapp.activitys.MainActivity.Companion.myProfile
import com.magicapp.pinterestclonekotlinapp.adapters.DetailsAdapter
import com.magicapp.pinterestclonekotlinapp.models.PhotoElements
import com.magicapp.pinterestclonekotlinapp.models.PhotoList
import com.magicapp.pinterestclonekotlinapp.models.RelatedPhotos
import com.magicapp.pinterestclonekotlinapp.networks.RetrofitHttp
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsFragment(var photoElements: PhotoElements) : Fragment() {

    private lateinit var adapter: DetailsAdapter
    private lateinit var tvRelated: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = DetailsAdapter(requireContext() as DetailsActivity)
        apiRelatedPhotos(photoElements.id!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        initViews(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(view: View){
        val ivDetailsImage = view.findViewById<ImageView>(R.id.iv_details_image)
        val btnBack = view.findViewById<ImageView>(R.id.iv_back)
        val btnMore = view.findViewById<ImageView>(R.id.iv_more2)
        val ivProfile = view.findViewById<ShapeableImageView>(R.id.iv_profile)
        val tvFullname = view.findViewById<TextView>(R.id.tv_fullname)
        val tvFollowUser = view.findViewById<TextView>(R.id.tv_follow_users)
        val btnLike = view.findViewById<ImageView>(R.id.iv_like)
        val btnSave = view.findViewById<TextView>(R.id.tv_save)
        val btnFollow = view.findViewById<TextView>(R.id.tv_follow)
        val tvDescription = view.findViewById<TextView>(R.id.tv_description)
        val ivMyProfile = view.findViewById<ShapeableImageView>(R.id.iv_profile2)
        val btnShare = view.findViewById<ImageView>(R.id.iv_share)
        val edtComment = view.findViewById<EditText>(R.id.edt_comment)
        val tvRelated = view.findViewById<TextView>(R.id.tv_related)

        val recyclerViewDetails = view.findViewById<RecyclerView>(R.id.recyclerView_details)
        recyclerViewDetails.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewDetails.adapter = adapter

        val s1 = photoElements.altDescription
        val s2 = photoElements.description
        val s3 = photoElements.user!!.name

        Picasso.get().load(photoElements.urls!!.small)
            .placeholder(ColorDrawable(Color.parseColor(photoElements.color))).into(ivDetailsImage)

        Picasso.get().load(photoElements.user!!.profileImage!!.small)
            .placeholder(ColorDrawable(Color.parseColor(photoElements.color))).into(ivProfile)

        tvFullname.text = photoElements.user!!.name
        tvFollowUser.text = "${photoElements.user!!.totalPhotos} Followers"
        tvDescription.text = getDescription(s1, s2, s3)

        Picasso.get().load(myProfile)
            .placeholder(ColorDrawable(Color.parseColor(photoElements.color))).into(ivMyProfile)

        btnBack.setOnClickListener{
            requireActivity().finish()
        }

    }


    private fun apiRelatedPhotos(id: String) {
        RetrofitHttp.photoService.getRelatedPhotos(id).enqueue(object : Callback<RelatedPhotos> {
            override fun onResponse(call: Call<RelatedPhotos>, response: Response<RelatedPhotos>) {
                val photoList: ArrayList<PhotoElements> = response.body()!!.results!!
                if (photoList.size > 0) {
                    adapter.addPhotos(photoList)
                } else {
                    tvRelated.text = "Related images has not found"
                }
            }

            override fun onFailure(call: Call<RelatedPhotos>, t: Throwable) {
                Log.e("@@@", t.message.toString())
            }
        })
    }

    private fun getDescription(s1: String?, s2: String?, s3: String?) : String{
        return when {
            s1 != null -> s1.toString()
            s2 != null -> s2.toString()
            else -> "Photo was made by $s3"
        }
    }

}