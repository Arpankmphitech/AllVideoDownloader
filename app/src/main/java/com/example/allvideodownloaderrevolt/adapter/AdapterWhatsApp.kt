package com.example.allvideodownloaderrevolt.adapter

import android.content.ContentValues
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.SharedPreferences
import com.example.allvideodownloaderrevolt.databinding.ImageThumbanialItemBinding
import com.example.allvideodownloaderrevolt.databinding.VideoThumbnailItemBinding
import com.example.allvideodownloaderrevolt.modelsClass.StatusModel
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel

class AdapterWhatsApp(
    var fragActivity: FragmentActivity?,
    var position: Int,
    var wAData: ArrayList<String?>?,
    var wAVideo: ArrayList<String?>?,
    var intExtra: Int,
    var emptyImage: ImageView?,
    var wAImageR: ArrayList<StatusModel>,
    var wAVideoR: ArrayList<StatusModel>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ImageHolder(var imageBinding: ImageThumbanialItemBinding) :
        RecyclerView.ViewHolder(imageBinding.root)

    class VideoHolder(var binding: VideoThumbnailItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return ImageHolder(
                ImageThumbanialItemBinding.inflate(
                    LayoutInflater.from(fragActivity),
                    parent,
                    false
                )
            )
        }
        return VideoHolder(
            VideoThumbnailItemBinding.inflate(
                LayoutInflater.from(fragActivity),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(pos: Int): Int {
        return position
    }

    override fun onBindViewHolder(mainHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            if (wAImageR.isEmpty()) {
                var imageHolder: ImageHolder = mainHolder as ImageHolder
                fragActivity?.let {
                    Glide.with(it).load(wAData?.get(position)).into(
                        imageHolder.imageBinding.roundImage
                    )
                }
                var file = File(wAData?.get(position))
                if (SharedPreferences.getStringName(file.name).equals("1")) {
                    imageHolder.imageBinding.imgPlay.visibility = View.GONE
                }
                Log.d("13/10", "if-----" + file.name)
                imageHolder.imageBinding.roundImage.setOnClickListener {
                    if (SharedPreferences.getStringName(file.name).equals("1")) {
                        Toast.makeText(fragActivity, "Image already downloaded", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                }
                imageHolder.imageBinding.imgPlay.setOnClickListener {
                    SharedPreferences.setStringName(file.name, "1")
                    copyFileOrDirectory(
                        wAData?.get(position),
                        Constant.WHATSAPP_IMAGES_PATH.path
                    )

                    imageHolder.imageBinding.imgPlay.visibility = View.GONE
                }
            } else {
                var status = wAImageR[position]
                var imageHolder: ImageHolder = mainHolder as ImageHolder
                fragActivity?.let {
                    Glide.with(it).load(status.documentFile?.uri).into(
                        imageHolder.imageBinding.roundImage
                    )
                }
                var file = File(Constant.WHATSAPP_STATUS_FOLDER_PATH2 + status.documentFile?.name)
                if (SharedPreferences.getStringName(file.name).equals("1")) {
                    imageHolder.imageBinding.imgPlay.visibility = View.GONE
                }
                Log.d("13/10", "if-----" + file.name)
                imageHolder.imageBinding.roundImage.setOnClickListener {
                    if (SharedPreferences.getStringName(file.name).equals("1")) {
                        Toast.makeText(fragActivity, "Image already downloaded", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                }
                imageHolder.imageBinding.imgPlay.setOnClickListener {
                    SharedPreferences.setStringName(file.name, "1")
                    saveFile(
                        status.documentFile?.uri,
                        status.documentFile?.name.toString(),
                        "roundImage/jpg"
                    )

                    imageHolder.imageBinding.imgPlay.visibility = View.GONE
                }
            }

        } else {
            if (wAVideoR.isEmpty()) {
                val thumb = wAVideo?.get(position)?.let {
                    ThumbnailUtils.createVideoThumbnail(
                        it,
                        MediaStore.Images.Thumbnails.MINI_KIND
                    )
                }
                var videoHolder = mainHolder as VideoHolder
                videoHolder.binding.roundImage.setImageBitmap(thumb)
                videoHolder.binding.imgShare.visibility = View.GONE
                videoHolder.binding.imgPlay.setImageResource(R.drawable.icon_wa_download)
                var file = File(wAVideo?.get(position))
                Log.d(
                    "13/10",
                    "" + SharedPreferences.getStringName(file.name)
                        .equals("1") + "-----else-----" + file.name
                )
                if (SharedPreferences.getStringName(file.name).equals("1")) {
                    videoHolder.binding.imgPlay.visibility = View.GONE
                }
                videoHolder.binding.roundImage.setOnClickListener {
                    if (SharedPreferences.getStringName(file.name).equals("1")) {
                        Toast.makeText(fragActivity, "Video already downloaded", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                }
                videoHolder.binding.imgPlay.setOnClickListener {
                    SharedPreferences.setStringName(file.name, "1")
                    copyFileOrDirectory(
                        wAVideo?.get(position),
                        Constant.WHATSAPP_VIDEO_PATH.path
                    )
                    videoHolder.binding.imgPlay.visibility = View.GONE
                }
            } else {
                var videoHolder = mainHolder as VideoHolder
                var status = wAVideoR[position]
                Log.d("24/12", "" + status.documentFile?.name)

                fragActivity?.let {
                    Glide.with(it).load(status.documentFile?.uri)
                        .into(videoHolder.binding.roundImage)
                }

//                videoHolder.binding.roundImage.setImageBitmap(thumb)
                videoHolder.binding.imgShare.visibility = View.GONE
                videoHolder.binding.imgPlay.setImageResource(R.drawable.icon_wa_download)
                var file = File(Constant.WHATSAPP_STATUS_FOLDER_PATH2 + status.documentFile?.name)
                Log.d(
                    "13/10",
                    "" + SharedPreferences.getStringName(file.name)
                        .equals("1") + "-----else-----" + file.name
                )
                if (SharedPreferences.getStringName(file.name).equals("1")) {
                    videoHolder.binding.imgPlay.visibility = View.GONE
                }
                videoHolder.binding.roundImage.setOnClickListener {
                    if (SharedPreferences.getStringName(file.name).equals("1")) {
                        Toast.makeText(fragActivity, "Video already downloaded", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                }
                videoHolder.binding.imgPlay.setOnClickListener {
                    SharedPreferences.setStringName(file.name, "1")
                    saveFile(
                        status.documentFile?.uri,
                        status.documentFile?.name.toString(),
                        "video/mp4"
                    )
                    videoHolder.binding.imgPlay.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (position == 0) {
            if (wAData?.size == 0 && wAImageR.size == 0)
                emptyImage?.visibility = View.VISIBLE
            else
                emptyImage?.visibility = View.GONE
            return if (wAImageR.size == 0)
                wAData?.size!!
            else
                wAImageR.size
        } else {
            if (wAVideo?.size == 0 && wAVideoR.size == 0)
                emptyImage?.visibility = View.VISIBLE
            else
                emptyImage?.visibility = View.GONE
            return if (wAVideoR.size == 0)
                wAVideo?.size!!
            else
                wAVideoR.size
        }
    }

    open fun copyFileOrDirectory(srcDir: String?, dstDir: String?) {
        try {
            val src = File(srcDir)
            val dst = File(dstDir, src.name)
            if (src.isDirectory) {
                val files = src.list()
                val filesLength = files.size
                for (i in 0 until filesLength) {
                    val src1 = File(src, files[i]).path
                    val dst1 = dst.path
                    copyFileOrDirectory(src1, dst1)
                }
            } else {
                copyFile(src, dst)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun saveFile(uri: Uri?, str: String, str2: String?) {
        val uri2: Uri

        val contentValues = ContentValues()
        contentValues.put("_display_name", str)
        contentValues.put("mime_type", str2)
        uri2 = if (str.endsWith(".mp4")) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                contentValues.put(
                    MediaStore.DownloadColumns.RELATIVE_PATH,
//                    Constants.WHATSAPP_VIDEO_PATH.path
                    Environment.DIRECTORY_DCIM + "/All Videos Downloader/Whatsapp/Videos"
                )
            }
            fragActivity?.contentResolver
                ?.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)!!
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                contentValues.put(
                    MediaStore.DownloadColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DCIM + "/All Videos Downloader/Whatsapp/Images"
                )
            }
            fragActivity?.contentResolver
                ?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
        }
        Toast.makeText(fragActivity, "Downloaded Successfully", Toast.LENGTH_SHORT).show()
        IOUtils.copy(
            uri?.let { fragActivity?.contentResolver?.openInputStream(it) },
            fragActivity?.contentResolver?.openOutputStream(uri2)
        )
    }

    @Throws(IOException::class)
    fun copyFile(sourceFile: File?, destFile: File) {
        if (!destFile.parentFile.exists()) destFile.parentFile.mkdirs()
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        var source: FileChannel? = null
        var destination: FileChannel? = null
        try {
            source = FileInputStream(sourceFile).channel
            destination = FileOutputStream(destFile).channel
            destination.transferFrom(source, 0, source.size())
            Toast.makeText(fragActivity, "Downloaded Successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(fragActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        } finally {
            if (source != null) {
                source.close()
            }
            if (destination != null) {
                destination.close()
            }
        }
    }
}