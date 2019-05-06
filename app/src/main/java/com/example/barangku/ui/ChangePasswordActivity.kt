package com.example.barangku.ui

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.barangku.R
import com.example.barangku.data.pref.PrefManager
import com.example.barangku.data.response.ChangePasswordResponse
import com.example.barangku.network.ApiClient
import com.example.barangku.network.ApiInterface
import com.example.barangku.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_change_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    private var mPrefManager: PrefManager? = null
    private var mProgressDialog: ProgressDialog? = null
    private var mApiInterface: ApiInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mPrefManager = PrefManager(this)
        mApiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)

        btnSave.setOnClickListener { view ->
            val last = etPassLast.text.toString()
            val new = etPassNew.text.toString()
            val confirm = etPassConfirm.text.toString()

            if (!new.equals(confirm)) {
                Toast.makeText(applicationContext, "Konfirmasi tidak sama", Toast.LENGTH_SHORT).show()
            } else {
                if (!last.equals(mPrefManager?.getPassword())) {
                    Toast.makeText(applicationContext, "Password lama salah", Toast.LENGTH_SHORT).show()
                } else {
                    updatePassword(new)
                }
            }
        }
    }

    private fun updatePassword(password: String) {
        mProgressDialog = CommonUtils.showLoadingDialog(this)

        val call = mApiInterface?.changePassword(mPrefManager!!.getNis(), password)
        call?.enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                if (response.isSuccessful) {
                    mPrefManager?.setPassword(password)
                    Toast.makeText(applicationContext, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                    mProgressDialog?.cancel()
                    finish()
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                mProgressDialog?.cancel()
            }

        })

    }
}
