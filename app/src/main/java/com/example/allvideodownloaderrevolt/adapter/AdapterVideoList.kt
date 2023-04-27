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
import com.example.allvideodownloaderrevolt.act.AllVideoPlayerAct
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.modelsClass.VideoModel
import java.io.File
import java.io.Serializable

class AdapterVideoList(
    var context: Activity, var modelList: List<VideoModel>
) : RecyclerView.Adapter<AdapterVideoList.MyFolder>() {
    var tvTitle: TextView? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyFolder {
        return MyFolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.row_video_list_item, viewGroup, false)
        )
    }

    override fun onBindViewHolder(myFolder: MyFolder, @SuppressLint("RecyclerView") i: Int) {
        val videoModel = modelList[i]
        ((Glide.with(context).load(File(videoModel.data))
            .centerCrop() as RequestBuilder<*>).placeholder(
            R.color.colorWhite
        ) as RequestBuilder<*>).into(myFolder.imgThumbnail)
        myFolder.txtName.text = videoModel.title
        if (!Utils.isValidatEmpty(videoModel.date!!)) {
            myFolder.txtDate.text = videoModel.date
        } else {
            myFolder.txtDate.visibility = View.GONE
        }
        myFolder.txtDuration.text = videoModel.duartion
        if (!Utils.isValidatEmpty(videoModel.size!!)) {
            myFolder.txtSize.text = videoModel.size
        } else {
            myFolder.txtSize.visibility = View.GONE
        }
        myFolder.itemView.setOnClickListener {
            Utils.displayInter(context, {
                val intent = Intent(context, AllVideoPlayerAct::class.java)
                intent.putExtra("list", modelList as Serializable)
                intent.putExtra("position", i)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                context.startActivity(intent)
            }, true)


        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class MyFolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgMore: ImageView
        var imgThumbnail: ImageView
        var txtDate: TextView
        var txtDuration: TextView
        var txtName: TextView
        var txtSize: TextView

        init {
            imgThumbnail = view.findViewById<View>(R.id.imgThumbnail) as ImageView
            imgMore = view.findViewById<View>(R.id.imgMore) as ImageView
            txtName = view.findViewById<View>(R.id.txtName) as TextView
            txtDuration = view.findViewById<View>(R.id.txtDuration) as TextView
            txtDate = view.findViewById<View>(R.id.txtDate) as TextView
            txtSize = view.findViewById<View>(R.id.txtSize) as TextView
        }
    }
}