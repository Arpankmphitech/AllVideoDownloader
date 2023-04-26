package com.example.allvideodownloaderrevolt.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.allvideodownloaderrevolt.fragment.DownloadListFrag
import com.example.allvideodownloaderrevolt.fragment.WhatsAppFrag
import com.example.allvideodownloaderrevolt.models.StatusModel

class ViewPagerWhatsApp(
    fm: FragmentManager,
    var whatsAppData: ArrayList<String?>,
    var whatsAppVideo: ArrayList<String?>,
    var whatsappImageR: ArrayList<StatusModel>,
    var whatsappVideoR: ArrayList<StatusModel>,
    var intExtra: Int
) :
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        Log.d("07/03","position----$position")
        return when (position) {
            0 -> WhatsAppFrag(position, whatsAppData, whatsAppVideo, intExtra,whatsappImageR,whatsappVideoR)
            1 -> WhatsAppFrag(position, whatsAppData, whatsAppVideo, intExtra,whatsappImageR,whatsappVideoR)
            2 -> DownloadListFrag.newInstance(1)
            else -> {
                WhatsAppFrag(position, whatsAppData, whatsAppVideo, intExtra,whatsappImageR,whatsappVideoR)
            }
        }
    }
}