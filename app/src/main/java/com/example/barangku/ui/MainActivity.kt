package com.example.barangku.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.barangku.R
import com.example.barangku.data.pref.PrefManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mPrefManager: PrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        mPrefManager = PrefManager(this)

        setUpdateLbl()


        btnScan.setOnClickListener { view ->
            val intent = Intent(applicationContext, ResultActivity::class.java)
            startActivity(intent)
        }

        btnProfile.setOnClickListener { view ->
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(intent)
        }

        btnNews.setOnClickListener { view ->
            val intent = Intent(applicationContext, NewsActivity::class.java)
            startActivity(intent)
        }

        btnMyReport.setOnClickListener { view ->
            val intent = Intent(applicationContext, MyReportActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpdateLbl() {
        findViewById<TextView>(R.id.tvName).setText(mPrefManager?.getName())

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide.with(applicationContext)
                .load(mPrefManager?.getImage())
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(userImage)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logout) {
            mPrefManager?.setLogged(false)
            val intent: Intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return true
    }
}
