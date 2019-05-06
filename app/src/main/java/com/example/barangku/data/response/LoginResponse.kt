package com.example.barangku.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Hamz on 04/05/2019.
 * ilham011001@gmail.com
 */

class LoginResponse {

    @SerializedName("login")
    var login: Boolean = false
    @SerializedName("nis")
    var nis: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("rayon")
    var rayon: String? = null
    @SerializedName("rombel")
    var rombel: String? = null
    @SerializedName("password")
    var password: String? = null
    @SerializedName("image")
    var image: String? = null
    @SerializedName("qrcode")
    var qrcode: String? = null

    constructor()

}