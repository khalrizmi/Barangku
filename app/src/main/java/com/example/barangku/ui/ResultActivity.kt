package com.example.barangku.ui

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.util.Log
import android.view.View
import com.google.zxing.integration.android.IntentIntegrator
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.barangku.R
import com.example.barangku.data.pref.PrefManager
import com.example.barangku.data.response.UserResponse
import com.example.barangku.network.ApiClient
import com.example.barangku.network.ApiInterface
import com.example.barangku.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_result.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResultActivity : AppCompatActivity() {

    private val TAG = ResultActivity::class.java!!.getSimpleName()

    private var mApiInterface: ApiInterface? = null
    private var mProgressDialog: ProgressDialog? = null
    private var mQrScan: IntentIntegrator? = null
    private var mPrefManager: PrefManager? = null

    private var nis: String? = null
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mApiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)
        mPrefManager = PrefManager(this)

        mQrScan = IntentIntegrator(this)
        mQrScan!!.initiateScan()

        btnReport.setOnClickListener { view ->
            val intent: Intent = Intent(applicationContext, ReportItActivity::class.java)
            intent.putExtra("nis", nis)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                finish()
            } else {
                loadData(result.contents.toString())
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun loadData(nis: String) {
        mProgressDialog = CommonUtils.showLoadingDialog(this)

        mApiInterface!!.scan(nis).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val statusCode = response.code().toString()
                if (response.isSuccessful()) {
                    mProgressDialog!!.cancel()

                    setUpdateLabel(response.body())
                } else {
                    mProgressDialog!!.cancel()

                    Toast.makeText(applicationContext, "Tidak terdapat di sistem", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                mProgressDialog!!.cancel()
                Log.e(TAG, "onFailure : " + t.toString())
            }
        })
    }

    private fun setUpdateLabel(body: UserResponse?) {
        nis = body?.nis
        name = body?.name

        tvNis.setText(body?.nis)
        tvName.setText(body?.name)
        tvRayon.setText(body?.rayon)
        tvRombel.setText(body?.rombel)

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide
                .with(applicationContext)
                .load(body?.image)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(imageView)

        if (mPrefManager?.getNis().equals(body?.nis)) {
            btnReport.visibility = View.GONE
        }

    }


}
