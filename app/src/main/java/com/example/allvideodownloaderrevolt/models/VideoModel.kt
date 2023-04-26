package com.example.allvideodownloaderrevolt.models

import java.io.Serializable
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class VideoModel : Serializable, Comparable<VideoModel?> {
    var data: String? = null
    var date: String? = null
    var displayName: String? = null
    var duartion: String? = null
    var id: String? = null
    var isSelected = false
    var playlist_name: String? = null
    var resolution: String? = null
    var size: String? = null
    var title: String? = null
    override fun compareTo(videoModel: VideoModel?): Int {
        return id!!.compareTo(id!!)
    }

    companion object {
        var sort_date: Comparator<VideoModel> = object : Comparator<VideoModel> {
            var format = SimpleDateFormat("dd/MM/yyyy")
            override fun compare(videoModel: VideoModel, videoModel2: VideoModel): Int {
                return try {
                    format.parse(folderDate(videoModel.date))
                        .compareTo(format.parse(folderDate(videoModel2.date)))
                } catch (e: ParseException) {
                    throw IllegalArgumentException(e)
                }
            }
        }
        var sort_length: Comparator<VideoModel> = object : Comparator<VideoModel> {
            var format1 = SimpleDateFormat("hh:mm:ss")
            override fun compare(videoModel: VideoModel, videoModel2: VideoModel): Int {
                return try {
                    format1.parse(duration(videoModel.duartion))
                        .compareTo(format1.parse(duration(videoModel2.duartion)))
                } catch (e: ParseException) {
                    throw IllegalArgumentException(e)
                }
            }
        }
        var sort_location = Comparator<VideoModel> { videoModel, videoModel2 ->
            videoModel.data!!.compareTo(
                videoModel2.data!!)
        }
        var sort_name = Comparator<VideoModel> { videoModel, videoModel2 ->
            videoModel.title!!.compareTo(
                videoModel2.title!!)
        }
        var sort_resolution = Comparator<VideoModel> { videoModel, videoModel2 ->
            videoModel.resolution!!.compareTo(
                videoModel2.resolution!!)
        }
        var sort_size = Comparator<VideoModel> { videoModel, videoModel2 ->
            videoModel.size!!.compareTo(
                videoModel2.size!!)
        }

        fun folderDate(str: String?): String {
            return SimpleDateFormat("dd/MM/yyyy").format(Date(str!!.toLong() * 1000))
        }

        fun duration(str: String?): String {
            return try {
                val parseInt = str!!.toInt().toLong()
                String.format("%02d:%02d:%02d",
                    *arrayOf<Any>(java.lang.Long.valueOf(TimeUnit.MILLISECONDS.toHours(parseInt)),
                        java.lang.Long.valueOf(
                            TimeUnit.MILLISECONDS.toMinutes(parseInt) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(parseInt))),
                        java.lang.Long.valueOf(
                            TimeUnit.MILLISECONDS.toSeconds(parseInt) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(parseInt)))))
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    }
}