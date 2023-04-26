package com.example.allvideodownloaderrevolt.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.activitys.VideoListAct
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.models.FolderModel
import com.google.android.exoplayer2.metadata.icy.IcyHeaders

class AdapterVideoFolder(
    var context: Activity, var modelList: List<FolderModel>
) : RecyclerView.Adapter<AdapterVideoFolder.MyFolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyFolder {
        return MyFolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.row_video_folder_item, viewGroup, false)
        )
    }

    override fun onBindViewHolder(myFolder: MyFolder, i: Int) {
        val folderModel = modelList[i]
        myFolder.txtName.text = folderModel.bucket
        if (folderModel.videoCount == IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) {
            val textView = myFolder.txtCount
            textView.text = folderModel.videoCount + " Video"
        } else {
            val textView2 = myFolder.txtCount
            textView2.text = folderModel.videoCount + " Videos"
        }
//        myFolder.txtSize.text = folderModel.date
        myFolder.itemView.setOnClickListener {
            Log.d("10/03", "" + folderModel.bid)
            Utils.displayInter(context, {
                context.startActivity(
                    Intent(context, VideoListAct::class.java).setFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                    ).putExtra("Bucket", folderModel.bid).putExtra("name", folderModel.bucket)
                )
            }, true)

        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class MyFolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtCount: TextView
        var txtName: TextView
        var txtSize: TextView

        init {
            txtName = view.findViewById(R.id.txtName)
            txtCount = view.findViewById(R.id.txtCount)
            txtSize = view.findViewById(R.id.txtSize)
        }
    }
}