package com.magicapp.pinterestclonekotlinapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import com.magicapp.pinterestclonekotlinapp.R
import com.magicapp.pinterestclonekotlinapp.activitys.DetailsActivity
import com.magicapp.pinterestclonekotlinapp.models.PhotoElements
import com.magicapp.pinterestclonekotlinapp.models.PhotoList
import com.squareup.picasso.Picasso

class DetailsAdapter(var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photoList = PhotoList()

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(photoList: ArrayList<PhotoElements>) {
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_fragment, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photoItem = photoList[position]
        val photoColor = photoItem.color
        val photoUrls = photoItem.urls!!.thumb

        if (holder is HomeAdapter.HomeViewHolder){
            holder.tvDescription.text = photoItem.user!!.bio
//            Glide.with(holder.itemView).load(photoItem.urls.thumb).into(holder.iv_image)
            Picasso.get().load(photoUrls).placeholder(ColorDrawable(Color.parseColor(photoColor)))
                .into(holder.iv_image)

            holder.itemView.setOnClickListener {
                callDetails(position)
            }
        }
    }

    class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_image : ShapeableImageView = view.findViewById(R.id.iv_details_image)
        val tvDescription : TextView = view.findViewById(R.id.tv_description)
        val iv_more : ImageView = view.findViewById(R.id.iv_more2)
    }

    private fun callDetails(position: Int) {
        var intent = Intent(context,DetailsActivity::class.java)
        val json = Gson().toJson(photoList)
        intent.putExtra("photoList", json)
        intent.putExtra("position", position)
        context.startActivity(intent)
    }

}