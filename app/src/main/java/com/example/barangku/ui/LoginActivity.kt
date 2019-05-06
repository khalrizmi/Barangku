package com.example.barangku.ui

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.Toast
import com.example.barangku.R
import com.example.barangku.data.pref.PrefManager
import com.example.barangku.data.response.LoginResponse
import com.example.barangku.network.ApiClient
import com.example.barangku.network.ApiInterface
import com.example.barangku.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java!!.simpleName

    private var mApiInterface: ApiInterface? = null
    private var mProgressDialog: ProgressDialog? = null
    private var mPrefManager: PrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mApiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)
        mPrefManager = PrefManager(this)

        if (mPrefManager!!.getLogged()) {
            launchMain()
        }

        btnLogin.setOnClickListener { view ->
            login(etUsername.text!!.toString(), etPassword.text!!.toString())
        }
    }

    fun login(nis:String, password:String) {
        mProgressDialog = CommonUtils.showLoadingDialog(this)

        val call = mApiInterface?.login(nis, password)
        call?.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: retrofit2.Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()!!.login) {
                    val body = response?.body()
                    mPrefManager!!.setLogged(true)
                    mPrefManager!!.setName(body?.name!!)
                    mPrefManager!!.setNis(body?.nis!!)
                    mPrefManager!!.setRayon(body?.rayon!!)
                    mPrefManager!!.setRombel(body?.rombel!!)
                    mPrefManager!!.setPassword(body?.password!!)
                    mPrefManager!!.setImage(body?.image!!)
                    mPrefManager!!.setQrcode(body?.qrcode!!)

                    launchMain()
                    mProgressDialog!!.cancel()
                } else {
                    Toast.makeText(applicationContext, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                    mProgressDialog!!.cancel()
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Terjadi kesalahan, harap coba beberapa saat lagi", Toast.LENGTH_SHORT).show()
                mProgressDialog!!.cancel()
            }


        })

    }

    private fun launchMain() {
        val intent: Intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
