package com.example.allvideodownloaderrevolt.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.allvideodownloaderrevolt.frag.DownloadListFrag
import com.example.allvideodownloaderrevolt.frag.WhatsAppFrag
import com.example.allvideodownloaderrevolt.modelsClass.StatusModel

class ViewPagerWhatsApp(
    fm: FragmentManager,
    var wADataList: ArrayList<String?>,
    var wAVideoList: ArrayList<String?>,
    var wAImageListR: ArrayList<StatusModel>,
    var wAVideoListR: ArrayList<StatusModel>,
    var intExtra: Int
) :
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        Log.d("07/03", "position----$position")
        return when (position) {
            0 -> WhatsAppFrag(
                position,
                wADataList,
                wAVideoList,
                intExtra,
                wAImageListR,
                wAVideoListR
            )
            1 -> WhatsAppFrag(
                position,
                wADataList,
                wAVideoList,
                intExtra,
                wAImageListR,
                wAVideoListR
            )
            2 -> DownloadListFrag.newInstance(1)
            else -> {
                WhatsAppFrag(
                    position,
                    wADataList,
                    wAVideoList,
                    intExtra,
                    wAImageListR,
                    wAVideoListR
                )
            }
        }
    }
}