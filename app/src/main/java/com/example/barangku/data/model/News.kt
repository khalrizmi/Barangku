package com.example.barangku.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Hamz on 06/05/2019.
 * ilham011001@gmail.com
 */

class News {

    @SerializedName("id")
    var id: Int = 0
    @SerializedName("name")
    var name: String = ""
    @SerializedName("imageProfile")
    var imageProfile: String = ""
    @SerializedName("image")
    var image: String = ""
    @SerializedName("to")
    var to: String = ""
    @SerializedName("note")
    var note: String = ""

    constructor()
    constructor(id: Int, name: String, imageProfile: String, image: String, to: String, note: String) {
        this.id = id
        this.name = name
        this.imageProfile = imageProfile
        this.image = image
        this.to = to
        this.note = note
    }


}