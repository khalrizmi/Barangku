package com.example.barangku.ui

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.barangku.R
import com.example.barangku.data.adapter.MyReportAdapter
import com.example.barangku.data.adapter.NewsAdapter
import com.example.barangku.data.model.News
import com.example.barangku.data.pref.PrefManager
import com.example.barangku.data.response.NewsResponse
import com.example.barangku.network.ApiClient
import com.example.barangku.network.ApiInterface
import com.example.barangku.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_my_report.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReportActivity : AppCompatActivity() {

    private var newses: ArrayList<News>? = null
    private var mApiInterface: ApiInterface? = null
    private var mProgressDialog: ProgressDialog? = null
    private var mPrefManager: PrefManager? = null

    private var listNews: List<News> ? = null
    private var newsAdapter: MyReportAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_report)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mApiInterface = ApiClient().getClient()?.create(ApiInterface::class.java)
        mPrefManager = PrefManager(this)

        loadData()
    }

    private fun loadData() {
        mProgressDialog = CommonUtils.showLoadingDialog(this)

        val call = mApiInterface?.news(mPrefManager?.getNis()!!)
        call?.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    listNews = response.body()?.listNews
                    newsAdapter = MyReportAdapter(listNews, applicationContext)
                    recyclerview.apply {
                        layoutManager = LinearLayoutManager(applicationContext)
                        adapter = newsAdapter
                    }
                    mProgressDialog?.cancel()
                }else {
                    Toast.makeText(applicationContext, "Terjadi kesalahan. Coba beberapa saat lagi", Toast.LENGTH_SHORT).show()
                    mProgressDialog?.cancel()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                mProgressDialog?.cancel()
            }
        })
    }
}
