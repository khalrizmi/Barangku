package com.example.barangku.ui

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.barangku.R
import com.example.barangku.data.response.DetailNewsResponse
import com.example.barangku.network.ApiClient
import com.example.barangku.network.ApiInterface
import com.example.barangku.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_detail_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailNewsActivity : AppCompatActivity() {

    private var mApiInterface: ApiInterface? = null
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mApiInterface = ApiClient().getClient()?.create(ApiInterface::class.java)

        loadData()
    }

    private fun loadData() {
        mProgressDialog = CommonUtils.showLoadingDialog(this)

        mApiInterface?.detailNews(intent.getIntExtra("id", 0))
                ?.enqueue(object : Callback<DetailNewsResponse> {
                    override fun onResponse(call: Call<DetailNewsResponse>, response: Response<DetailNewsResponse>) {
                        if (response.isSuccessful) {

                            val body = response.body()

                            val circularProgressDrawable = CircularProgressDrawable(applicationContext)
                            circularProgressDrawable.strokeWidth = 5f
                            circularProgressDrawable.centerRadius = 30f
                            circularProgressDrawable.start()
                            Glide
                                    .with(applicationContext)
                                    .load(body?.image)
                                    .centerCrop()
                                    .placeholder(circularProgressDrawable)
                                    .into(image)

                            tvNis.setText(body?.nis)
                            tvName.setText(body?.name)
                            tvRombel.setText(body?.rombel)
                            tvTanggal.setText(body?.date)
                            tvNote.setText(body?.note)

                            mProgressDialog?.cancel()
                        } else {
                            Toast.makeText(applicationContext, "Terjadi kesalahan. Coba beberapa saat lagi", Toast.LENGTH_SHORT).show()
                            mProgressDialog?.cancel()
                        }
                    }

                    override fun onFailure(call: Call<DetailNewsResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                        mProgressDialog?.cancel()
                    }

                })
    }
}
