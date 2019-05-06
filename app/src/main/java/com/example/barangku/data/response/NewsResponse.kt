package com.example.barangku.data.response

import com.example.barangku.data.model.News
import com.google.gson.annotations.SerializedName

/**
 * Created by Hamz on 06/05/2019.
 * ilham011001@gmail.com
 */

class NewsResponse {

    @SerializedName("data")
    var listNews: List<News>? = null

}