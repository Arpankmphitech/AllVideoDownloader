package com.example.allvideodownloaderrevolt.adapter

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.LabeledIntent
import android.os.Build
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.activitys.DownloadVideoPlayerAct
import com.example.allvideodownloaderrevolt.activitys.HomeAct
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.VideoThumbnailItemBinding
import java.io.File

class AdapterDownloadVideoList(var filePath: ArrayList<String?>?, var activity: Activity?) :
    RecyclerView.Adapter<AdapterDownloadVideoList.ViewHolder>() {

    class ViewHolder(val binding: VideoThumbnailItemBinding?) : RecyclerView.ViewHolder(binding?.root!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            VideoThumbnailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.roundImage?.let {
            activity?.let { it1 ->
                Glide.with(it1).load(filePath?.get(position)).into(
                    it
                )
            }
        }

        holder.itemView.setOnClickListener {
            try {
                Utils.displayInter(activity!!, {
                    activity?.startActivity(
                        Intent(
                            activity,
                            DownloadVideoPlayerAct::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(
                            "path",
                            filePath?.get(position)
                        )
                    )
                }, true)


            } catch (e: Exception) {
                Toast.makeText(activity, "Unable to play video", Toast.LENGTH_SHORT).show()
            }
        }
        holder.binding?.imgShare?.setOnClickListener {
            activity?.let {
                shareExcludingApp("You Can Easily Download Videos From Multiple Platform With The Help Of This App " +
                        "\n\n" +
                        "\uD83E\uDD29 \uD83E\uDD29 \uD83E\uDD29 \uD83E\uDD29" +
                        "\n\n" +
                        Constant.TINY_URL,
                    filePath?.get(position)
                )
            }
        }
    }

    private fun shareExcludingApp(text: String?,
                                  filePath: String?,
    ) {
        val targetIntent = Intent(Intent.ACTION_SEND)
        targetIntent.type = "video/*"
        targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        targetIntent.putExtra(Intent.EXTRA_SUBJECT, activity?.getString(R.string.app_name))
        targetIntent.putExtra(Intent.EXTRA_TEXT, text)
        val fileURI = FileProvider.getUriForFile(
            activity!!, activity!!.packageName + ".provider",
            File(filePath!!)
        )
        Log.e("1010", "shareExcludingApp: $filePath")
        targetIntent.putExtra(Intent.EXTRA_STREAM, fileURI)
        val excludedAppsPackageNames =
            hashSetOf(activity?.packageName)
        getIntentChooser(
            activity,
            targetIntent,
            "choose!",
            object : HomeAct.ComponentNameFilter {
                override fun shouldBeFilteredOut(componentName: ComponentName?): Boolean {
                    return excludedAppsPackageNames.contains(componentName!!.packageName)
                }
            })?.let { activity?.startActivity(it) }
    }


    private fun getIntentChooser(
        context: Activity?,
        intent: Intent,
        chooserTitle: CharSequence? = null,
        filter: HomeAct.ComponentNameFilter,
    ): Intent? {
        val resolveInfos = context?.packageManager?.queryIntentActivities(intent, 0)
        val excludedComponentNames = HashSet<ComponentName>()
        resolveInfos?.forEach {
            val activityInfo = it.activityInfo
            val componentName = ComponentName(activityInfo.packageName, activityInfo.name)
            if (filter.shouldBeFilteredOut(componentName))
                excludedComponentNames.add(componentName)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Intent.createChooser(intent, chooserTitle)
                .putExtra(Intent.EXTRA_EXCLUDE_COMPONENTS, excludedComponentNames.toTypedArray())
        }
        if (resolveInfos != null) {
            if (resolveInfos.isNotEmpty()) {
                val targetIntents: MutableList<Intent> = ArrayList()
                for (resolveInfo in resolveInfos) {
                    val activityInfo = resolveInfo.activityInfo
                    if (excludedComponentNames.contains(
                            ComponentName(
                                activityInfo.packageName,
                                activityInfo.name
                            )
                        )
                    )
                        continue
                    val targetIntent = Intent(intent)
                    targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    targetIntent.setPackage(activityInfo.packageName)
                    targetIntent.component =
                        ComponentName(activityInfo.packageName, activityInfo.name)
                    val labeledIntent = LabeledIntent(
                        targetIntent,
                        activityInfo.packageName,
                        resolveInfo.labelRes,
                        resolveInfo.icon
                    )
                    targetIntents.add(labeledIntent)
                }
                val chooserIntent: Intent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent.createChooser(Intent(), chooserTitle)
                } else {
                    Intent.createChooser(targetIntents.removeAt(0), chooserTitle)
                }
                if (chooserIntent == null) {
                    return null
                }
                chooserIntent.putExtra(
                    Intent.EXTRA_INITIAL_INTENTS,
                    targetIntents.toTypedArray<Parcelable>()
                )
                return chooserIntent
            }
        }
        return null
    }

    override fun getItemCount(): Int {
        return filePath?.size!!
    }
}