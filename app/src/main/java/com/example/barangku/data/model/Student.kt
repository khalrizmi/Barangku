package com.example.barangku.data.model

/**
 * Created by Hamz on 04/05/2019.
 * ilham011001@gmail.com
 */


class Student {

    var id: Int? = null
    var name: String = ""
    var nis: String = ""

    constructor() {}

    constructor(id: Int?, name: String, nis: String) {
        this.id = id
        this.name = name
        this.nis = nis
    }

}