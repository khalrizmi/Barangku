package com.example.barangku.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.view.View
import com.bumptech.glide.Glide
import com.example.barangku.R
import com.example.barangku.data.pref.PrefManager
import com.example.barangku.data.response.UserResponse

import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    private var mPrefManager: PrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mPrefManager = PrefManager(this)

        setUpdateLabel()

        btnEdit.setOnClickListener { view ->
            val intent: Intent = Intent(applicationContext, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpdateLabel() {
        tvNis.setText(mPrefManager?.getNis())
        tvName.setText(mPrefManager?.getName())
        tvRayon.setText(mPrefManager?.getRayon())
        tvRombel.setText(mPrefManager?.getRombel())

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide
                .with(applicationContext)
                .load(mPrefManager?.getImage())
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(userImage)


    }
}
