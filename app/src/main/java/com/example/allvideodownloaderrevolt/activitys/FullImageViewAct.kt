package com.example.allvideodownloaderrevolt.activitys

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActFullImageViewBinding
import com.example.allvideodownloaderrevolt.models.GalleyPhotosListModel

class FullImageViewAct : BaseAct() {

    companion object {
        lateinit var binding: ActFullImageViewBinding
        var listGalleryAlbums: List<GalleyPhotosListModel>? = null
        lateinit var fullImageActivity: Activity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActFullImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullImageActivity = this@FullImageViewAct
        Utils.setStatusBarSkyBlueGradientActivity(fullImageActivity as FullImageViewAct)
        getIntentData()
        initToolBar()
        initView()
    }

    private fun getIntentData() {
        listGalleryAlbums = intent.getSerializableExtra("list") as ArrayList<GalleyPhotosListModel>?
    }

    private fun initToolBar() {
        binding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        binding.toolBar.txtTitleName.text = fullImageActivity.resources.getString(R.string.roundImage)
    }

    private fun initView() {
        binding.listViewPager.adapter = ImageAdapter(listGalleryAlbums)
        binding.listViewPager.currentItem = intent.getIntExtra("pos", 0)
    }

    internal class ImageAdapter(list: List<GalleyPhotosListModel>?) :
        PagerAdapter() {
        var photos: List<GalleyPhotosListModel>?
        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun getCount(): Int {
            return photos!!.size
        }

        override fun instantiateItem(viewGroup: ViewGroup, i: Int): Any {
            val inflate: View = LayoutInflater.from(fullImageActivity)
                .inflate(R.layout.image_view_item, viewGroup, false)
            Glide.with(fullImageActivity).load(photos!![i].photoUri)
                .into(inflate.findViewById<View>(R.id.imgThum) as ImageView)
            viewGroup.addView(inflate)
            return inflate
        }

        override fun destroyItem(viewGroup: ViewGroup, i: Int, obj: Any) {
            viewGroup.removeView(obj as View)
        }

        init {
            photos = list
        }
    }

    override fun onBackPressed() {
        finish()
    }
}