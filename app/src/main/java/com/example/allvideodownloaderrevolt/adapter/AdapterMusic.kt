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
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.act.MusicPlayAct
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.modelsClass.AudioModel
import java.io.Serializable

class AdapterMusic(var context: Activity, var listGalleryAlbums: List<AudioModel>) :
    RecyclerView.Adapter<AdapterMusic.MyImage>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyImage {
        return MyImage(
            LayoutInflater.from(
            context).inflate(R.layout.row_music_folder_item, viewGroup, false))
    }

    override fun onBindViewHolder(myImage: MyImage, @SuppressLint("RecyclerView") i: Int) {
        val audioModel = listGalleryAlbums[i]
        val textView = myImage.txtCount
        textView.text = audioModel.getaArtist()
        myImage.tvTitle.text = audioModel.getaName()
        myImage.txtSize.visibility = View.GONE
        myImage.itemView.setOnClickListener {
            Utils.displayInter(context, {
                context.startActivity(
                    Intent(context,
                    MusicPlayAct::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .putExtra("pos", i)
                    .putExtra("arrayList", listGalleryAlbums as Serializable))
            }, true)
        }
    }

    override fun getItemCount(): Int {
        return listGalleryAlbums.size
    }

    inner class MyImage(view: View) : RecyclerView.ViewHolder(view) {
        var roundImgfolder: ImageView
        var txtCount: TextView
        var tvTitle: TextView
        var txtSize: TextView

        init {
            txtCount = view.findViewById(R.id.txtCount)
            tvTitle = view.findViewById(R.id.txtName)
            roundImgfolder = view.findViewById(R.id.roundImgfolder)
            txtSize = view.findViewById(R.id.txtSize)
        }
    }
}