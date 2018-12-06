package com.example.reddittestclient.feature.imageviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.reddittestclient.R
import com.stfalcon.frescoimageviewer.ImageViewer

class ImageViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        val imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
        ImageViewer.Builder(this@ImageViewerActivity, arrayOf(imageUrl))
            .setOnDismissListener { finish() }
            .show()
    }

    companion object {
        val EXTRA_IMAGE_URL = "image_url"
    }
}
