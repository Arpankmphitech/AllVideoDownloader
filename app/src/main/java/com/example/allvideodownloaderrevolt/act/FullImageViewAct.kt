package com.example.allvideodownloaderrevolt.act

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActFullImageViewBinding
import com.example.allvideodownloaderrevolt.modelsClass.GalleyPhotosListModel

class FullImageViewAct : BaseAct() {

    companion object {
        lateinit var actFullImgBinding: ActFullImageViewBinding
        var galleryAlbumsList: List<GalleyPhotosListModel>? = null
        lateinit var fullImageAct: Activity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actFullImgBinding = ActFullImageViewBinding.inflate(layoutInflater)
        setContentView(actFullImgBinding.root)
        fullImageAct = this@FullImageViewAct
        Utils.setStatusBarSkyBlueGradientActivity(fullImageAct as FullImageViewAct)
        getIntentData()
        initToolBar()
        initView()
    }

    private fun getIntentData() {
        galleryAlbumsList = intent.getSerializableExtra("list") as ArrayList<GalleyPhotosListModel>?
    }

    private fun initToolBar() {
        actFullImgBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        actFullImgBinding.toolBar.txtTitleName.text =
            fullImageAct.resources.getString(R.string.roundImage)
    }

    private fun initView() {
        actFullImgBinding.listViewPager.adapter = ImageAdapter(galleryAlbumsList)
        actFullImgBinding.listViewPager.currentItem = intent.getIntExtra("pos", 0)
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
            val inflate: View = LayoutInflater.from(fullImageAct)
                .inflate(R.layout.image_view_item, viewGroup, false)
            Glide.with(fullImageAct).load(photos!![i].photoUri)
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