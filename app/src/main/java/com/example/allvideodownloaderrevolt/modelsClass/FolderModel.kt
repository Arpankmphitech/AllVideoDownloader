package com.example.allvideodownloaderrevolt.modelsClass

import java.util.concurrent.TimeUnit

class FolderModel {
    var bid: String? = null
    var bucket: String? = null
    var data: String? = null
    var date: String? = null
    var size: String? = null
    var videoCount: String? = null

    companion object {
        var sort_name = java.util.Comparator<FolderModel> { folderModel, folderModel2 ->
            folderModel.bucket!!.compareTo(
                folderModel2.bucket!!)
        }
        var sort_size = java.util.Comparator<FolderModel> { folderModel, folderModel2 ->
            folderModel.size!!.compareTo(
                folderModel2.size!!)
        }

        private fun duration(str: String): String {
            return try {
                val parseInt = str.toInt().toLong()
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