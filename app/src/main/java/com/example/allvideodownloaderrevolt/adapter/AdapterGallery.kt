package com.example.allvideodownloaderrevolt.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.act.ImageListAct
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.modelsClass.GalleyAlbumModel
import com.makeramen.roundedimageview.RoundedImageView

class AdapterGallery(var context: Activity, var listGalleryAlbums: List<GalleyAlbumModel>) :
    RecyclerView.Adapter<AdapterGallery.MyImage>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyImage {
        return MyImage(
            LayoutInflater.from(
            context).inflate(R.layout.row_gallery_folder_item, viewGroup, false))
    }

    override fun onBindViewHolder(myImage: MyImage, @SuppressLint("RecyclerView") i: Int) {
        val galleyAlbumModel = listGalleryAlbums[i]
        myImage.txtSize.visibility = View.GONE
        Glide.with(context).load(listGalleryAlbums[i].albumPhotos!![0].photoUri)
            .into(myImage.roundImgfolder)
        val textView = myImage.txtCount
        textView.text = galleyAlbumModel.albumPhotos!!.size.toString() + " Media"
        myImage.tvTitle.text = galleyAlbumModel.name
        myImage.itemView.setOnClickListener {
            Utils.displayInter(context, {
                context.startActivity(
                    Intent(context,
                    ImageListAct::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .putExtra("pos", i)
                    .putExtra("name", galleyAlbumModel.name))
            }, true)


        }
    }

    override fun getItemCount(): Int {
        return listGalleryAlbums.size
    }

    inner class MyImage(view: View) : RecyclerView.ViewHolder(view) {
        var roundImgfolder: RoundedImageView
        var txtCount: TextView
        var tvTitle: TextView
        var txtSize: TextView

        init {
            txtSize = view.findViewById(R.id.txtSize)
            txtCount = view.findViewById(R.id.txtCount)
            tvTitle = view.findViewById(R.id.txtName)
            roundImgfolder = view.findViewById(R.id.roundImgfolder)
        }
    }
}