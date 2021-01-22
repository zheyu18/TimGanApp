package com.example.timganapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.timganapp.R
import com.example.timganapp.util.toast
import kotlinx.android.synthetic.main.activity_photo.*
import kotlinx.android.synthetic.main.common_toolbar.*

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        initToolBar()
        val url = intent.getStringExtra("url")
        if (TextUtils.isEmpty(url)) {
            this.toast("url is null")
            finish()
            return
        }
        Glide.with(this).load(url).into(photoView)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = ""
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}
