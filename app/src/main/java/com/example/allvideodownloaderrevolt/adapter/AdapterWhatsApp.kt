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
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.SharedPreferences
import com.example.allvideodownloaderrevolt.databinding.ImageThumbanialItemBinding
import com.example.allvideodownloaderrevolt.databinding.VideoThumbnailItemBinding
import com.example.allvideodownloaderrevolt.models.StatusModel
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel

class AdapterWhatsApp(
    var activity: FragmentActivity?,
    var position: Int,
    var whatsAppData: ArrayList<String?>?,
    var whatsAppVideo: ArrayList<String?>?,
    var intExtra: Int,
    var imgEmpty: ImageView?,
    var whatsappImageR: ArrayList<StatusModel>,
    var whatsappVideoR: ArrayList<StatusModel>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ImageHolder(var binding: ImageThumbanialItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class VideoHolder(var binding: VideoThumbnailItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return ImageHolder(
                ImageThumbanialItemBinding.inflate(
                    LayoutInflater.from(activity),
                    parent,
                    false
                )
            )
        }
        return VideoHolder(
            VideoThumbnailItemBinding.inflate(
                LayoutInflater.from(activity),
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
            if (whatsappImageR.isEmpty()) {
                var imageHolder: ImageHolder = mainHolder as ImageHolder
                activity?.let {
                    Glide.with(it).load(whatsAppData?.get(position)).into(
                        imageHolder.binding.roundImage
                    )
                }
                var file = File(whatsAppData?.get(position))
                if (SharedPreferences.getStringName(file.name).equals("1")) {
                    imageHolder.binding.imgPlay.visibility = View.GONE
                }
                Log.d("13/10", "if-----" + file.name)
                imageHolder.binding.roundImage.setOnClickListener {
                    if (SharedPreferences.getStringName(file.name).equals("1")) {
                        Toast.makeText(activity, "Image already downloaded", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                }
                imageHolder.binding.imgPlay.setOnClickListener {
                    SharedPreferences.setStringName(file.name, "1")
                    copyFileOrDirectory(
                        whatsAppData?.get(position),
                        Constant.WHATSAPP_IMAGES_PATH.path
                    )

                    imageHolder.binding.imgPlay.visibility = View.GONE
                }
            } else {
                var status = whatsappImageR[position]
                var imageHolder: ImageHolder = mainHolder as ImageHolder
                activity?.let {
                    Glide.with(it).load(status.documentFile?.uri).into(
                        imageHolder.binding.roundImage
                    )
                }
                var file = File(Constant.WHATSAPP_STATUS_FOLDER_PATH2 + status.documentFile?.name)
                if (SharedPreferences.getStringName(file.name).equals("1")) {
                    imageHolder.binding.imgPlay.visibility = View.GONE
                }
                Log.d("13/10", "if-----" + file.name)
                imageHolder.binding.roundImage.setOnClickListener {
                    if (SharedPreferences.getStringName(file.name).equals("1")) {
                        Toast.makeText(activity, "Image already downloaded", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                }
                imageHolder.binding.imgPlay.setOnClickListener {
                    SharedPreferences.setStringName(file.name, "1")
                    saveFile(
                        status.documentFile?.uri,
                        status.documentFile?.name.toString(),
                        "roundImage/jpg"
                    )

                    imageHolder.binding.imgPlay.visibility = View.GONE
                }
            }

        } else {
            if (whatsappVideoR.isEmpty()) {
                val thumb = whatsAppVideo?.get(position)?.let {
                    ThumbnailUtils.createVideoThumbnail(
                        it,
                        MediaStore.Images.Thumbnails.MINI_KIND
                    )
                }
                var videoHolder = mainHolder as VideoHolder
                videoHolder.binding.roundImage.setImageBitmap(thumb)
                videoHolder.binding.imgShare.visibility = View.GONE
                videoHolder.binding.imgPlay.setImageResource(R.drawable.ic_wa_download)
                var file = File(whatsAppVideo?.get(position))
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
                        Toast.makeText(activity, "Video already downloaded", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                }
                videoHolder.binding.imgPlay.setOnClickListener {
                    SharedPreferences.setStringName(file.name, "1")
                    copyFileOrDirectory(
                        whatsAppVideo?.get(position),
                        Constant.WHATSAPP_VIDEO_PATH.path
                    )
                    videoHolder.binding.imgPlay.visibility = View.GONE
                }
            } else {
                var videoHolder = mainHolder as VideoHolder
                var status = whatsappVideoR[position]
                Log.d("24/12", "" + status.documentFile?.name)

                activity?.let {
                    Glide.with(it).load(status.documentFile?.uri)
                        .into(videoHolder.binding.roundImage)
                }

//                videoHolder.binding.roundImage.setImageBitmap(thumb)
                videoHolder.binding.imgShare.visibility = View.GONE
                videoHolder.binding.imgPlay.setImageResource(R.drawable.ic_wa_download)
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
                        Toast.makeText(activity, "Video already downloaded", Toast.LENGTH_SHORT)
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
            if (whatsAppData?.size == 0 && whatsappImageR.size == 0)
                imgEmpty?.visibility = View.VISIBLE
            else
                imgEmpty?.visibility = View.GONE
            return if (whatsappImageR.size == 0)
                whatsAppData?.size!!
            else
                whatsappImageR.size
        } else {
            if (whatsAppVideo?.size == 0 && whatsappVideoR.size == 0)
                imgEmpty?.visibility = View.VISIBLE
            else
                imgEmpty?.visibility = View.GONE
            return if (whatsappVideoR.size == 0)
                whatsAppVideo?.size!!
            else
                whatsappVideoR.size
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
            activity?.contentResolver
                ?.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)!!
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                contentValues.put(
                    MediaStore.DownloadColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DCIM + "/All Videos Downloader/Whatsapp/Images"
                )
            }
            activity?.contentResolver
                ?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
        }
        Toast.makeText(activity, "Downloaded Successfully", Toast.LENGTH_SHORT).show()
        IOUtils.copy(
            uri?.let { activity?.contentResolver?.openInputStream(it) },
            activity?.contentResolver?.openOutputStream(uri2)
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
            Toast.makeText(activity, "Downloaded Successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
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