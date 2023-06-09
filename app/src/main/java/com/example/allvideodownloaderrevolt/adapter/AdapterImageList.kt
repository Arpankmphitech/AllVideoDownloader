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
import java.util.*
import kotlin.Int

class AdapterImageList(
    var activity: Activity,
    var galleryAlbumsList: List<GalleyPhotosListModel>,
) : RecyclerView.Adapter<AdapterImageList.MyImage>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyImage {
        return MyImage(
            LayoutInflater.from(
                activity
            ).inflate(R.layout.row_images_item, viewGroup, false)
        )
    }

    override fun onBindViewHolder(myImage: MyImage, @SuppressLint("RecyclerView") i: Int) {
        val galleyPhotosListModel = galleryAlbumsList[i]
        ((Glide.with(activity).load(galleyPhotosListModel.photoUri)
            .centerCrop() as RequestBuilder<*>).placeholder(
            R.color.colorWhite
        ) as RequestBuilder<*>).into(myImage.imgThum)
        myImage.tvTitle.text = galleyPhotosListModel.imageName
        myImage.itemView.setOnClickListener {
            Utils.displayInter(activity, {
                val intent = Intent(activity, FullImageViewAct::class.java)
                intent.putExtra("list", galleryAlbumsList as Serializable)
                intent.putExtra("pos", i)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                activity.startActivity(intent)
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