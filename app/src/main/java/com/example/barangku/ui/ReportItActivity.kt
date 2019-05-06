package com.example.barangku.ui

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.widget.CircularProgressDrawable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.barangku.R
import com.example.barangku.data.pref.PrefManager
import com.example.barangku.data.response.ReportResponse
import com.example.barangku.network.ApiClient
import com.example.barangku.network.ApiInterface
import com.example.barangku.utils.CommonUtils
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_report.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ReportItActivity : AppCompatActivity() {

    private val REQUEST_TAKE_PHOTO = 1

    private var mApiInterface: ApiInterface? = null
    private var mPrefManager: PrefManager? = null
    private var mProgressDialog: ProgressDialog? = null

    private var mCurrentPhotoPath = ""
    private var photoFile: File? = null
    private var compressedImage: File? = null
    private var currentPhotoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mApiInterface = ApiClient().getClient()!!.create(ApiInterface::class.java)
        mPrefManager = PrefManager(this)

        etNis!!.setText(intent.getStringExtra("nis"))
        etNama!!.setText(intent.getStringExtra("name"))

        btnOpenCamera.setOnClickListener { view ->
            openCamera()
        }

        btnReport.setOnClickListener { view ->
            sendReport()
        }
    }

    private fun sendReport() {
        mProgressDialog = CommonUtils.showLoadingDialog(this)


        val propertyImageFile = File(mCurrentPhotoPath)
        val propertyImage = RequestBody.create(MediaType.parse("image/*"), propertyImageFile)
        val propertyImagePart = MultipartBody.Part.createFormData("image", propertyImageFile.name, propertyImage)


        val nis = RequestBody.create(MediaType.parse("text/plain"), mPrefManager!!.getNis())
        val nisTo = RequestBody.create(MediaType.parse("text/plain"), etNis.text.toString())
        val note = RequestBody.create(MediaType.parse("text/plain"), etNote.text.toString())

        val call = mApiInterface?.report(nis, nisTo, note, propertyImagePart)
        call?.enqueue(object : Callback<ReportResponse> {
            override fun onResponse(call: Call<ReportResponse>, response: Response<ReportResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Laporan berhasil dikirim", Toast.LENGTH_SHORT).show()
                    mProgressDialog?.cancel()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Terjadi kesalahan. Coba beberapa saat lagi", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                mProgressDialog?.cancel()
            }

        })
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 99)
        } else if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 98)
        } else if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 97)
        } else {
            dispatchTakePictureIntent()
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File

                    Toast.makeText(applicationContext, "Gagal mengambil gambar", Toast.LENGTH_SHORT).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.example.barangku.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

            val file: File = File(currentPhotoPath)
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName: String = "JPEG_" + timeStamp + ".jpg"

            compressedImage = Compressor(this)
                    .setDestinationDirectoryPath(file!!.getParent() + "/")
                    .compressToFile(file, imageFileName)

            file!!.delete()

            tvTake.visibility = View.GONE
            btnOpenCamera.visibility = View.GONE
            imageObject.visibility = View.VISIBLE

//
            val circularProgressDrawable = CircularProgressDrawable(this)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide
                    .with(applicationContext)
                    .load(compressedImage!!.absolutePath)
                    .placeholder(circularProgressDrawable)
                    .into(imageObject)

            mCurrentPhotoPath = compressedImage!!.absolutePath
        }
    }
}
