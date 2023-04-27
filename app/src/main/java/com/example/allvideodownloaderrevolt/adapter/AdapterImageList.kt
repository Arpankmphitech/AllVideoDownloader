package com.example.allvideodownloaderrevolt.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.act.FullImageViewAct
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.modelsClass.GalleyPhotosListModel
import java.io.Serializable

class AdapterImageList(
    var activity : Activity,
    var galleryAlbumsList: List<GalleyPhotosListModel>,
) : RecyclerView.Adapter<AdapterImageList.MyImage>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyImage {
        return MyImage(LayoutInflater.from(
            activity).inflate(R.layout.row_images_item, viewGroup, false))
    }

    override fun onBindViewHolder(myImage: MyImage, @SuppressLint("RecyclerView") i: Int) {
        val galleyPhotosListModel = galleryAlbumsList[i]
        ((Glide.with(activity).load(galleyPhotosListModel.photoUri)
            .centerCrop() as RequestBuilder<*>).placeholder(
            R.color.colorWhite) as RequestBuilder<*>).into(myImage.imgThum)
        myImage.tvTitle.text = galleyPhotosListModel.albumName
        myImage.itemView.setOnClickListener {
            Utils.displayInter(activity, {
                activity.startActivity(Intent(activity,
                    FullImageViewAct::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .putExtra("pos", i)
                    .putExtra("list", galleryAlbumsList as Serializable))
            }, true)

        }
    }

    override fun getItemCount(): Int {
        return galleryAlbumsList.size
    }

    inner class MyImage(view: View) : RecyclerView.ViewHolder(view) {
        var imgThum: ImageView
        var tvTitle: TextView

        init {
            imgThum = view.findViewById(R.id.imgThum)
            tvTitle = view.findViewById(R.id.txtTitleName)
        }
    }
}