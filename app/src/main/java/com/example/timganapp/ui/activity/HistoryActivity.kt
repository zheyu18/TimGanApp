package com.example.timganapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.timganapp.R
import kotlinx.android.synthetic.main.activity_history_detail.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail)
        val date = intent.getStringExtra("date")
        initToolBar(date)
        addContainer(date)
    }

    private fun addContainer(date:String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, RecommdFragment.newInstance(date))
            .commit()
    }

    private fun initToolBar(date: String) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = date
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
