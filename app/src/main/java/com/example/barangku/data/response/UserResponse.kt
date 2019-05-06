package com.example.barangku.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Hamz on 04/05/2019.
 * ilham011001@gmail.com
 */

class UserResponse {


    @SerializedName("nis")
    var nis: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("rayon")
    var rayon: String = ""
    @SerializedName("rombel")
    var rombel: String = ""
    @SerializedName("image")
    var image: String = ""


    constructor()


}