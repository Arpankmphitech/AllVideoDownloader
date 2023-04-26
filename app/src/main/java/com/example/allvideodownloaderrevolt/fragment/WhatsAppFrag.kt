package com.example.allvideodownloaderrevolt.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allvideodownloaderrevolt.adapter.AdapterWhatsApp
import com.example.allvideodownloaderrevolt.databinding.FragWhatsAppBinding
import com.example.allvideodownloaderrevolt.models.StatusModel

class WhatsAppFrag(
    var position: Int,
    var whatsAppData: ArrayList<String?>,
    var whatsAppVideo: ArrayList<String?>,
    var intExtra: Int,
    private var whatsappImageR: ArrayList<StatusModel>,
    var whatsappVideoR: ArrayList<StatusModel>
) : Fragment() {

    lateinit var binding: FragWhatsAppBinding
    var adapter: AdapterWhatsApp? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragWhatsAppBinding.inflate(layoutInflater)
        Log.d("07/03",""+whatsappImageR.size)
        Log.d("07/03",""+whatsappVideoR.size)
        adapter = AdapterWhatsApp(
            activity,
            position,
            whatsAppData,
            whatsAppVideo,
            intExtra,
            binding.imgEmpty,
            whatsappImageR,
            whatsappVideoR
        )
        binding.rcvVideoList.layoutManager = GridLayoutManager(activity, 3)
        binding.rcvVideoList.adapter = adapter
        return binding.root
    }

}