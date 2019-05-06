package com.example.barangku.data.pref

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Hamz on 05/05/2019.
 * ilham011001@gmail.com
 */

class PrefManager(context: Context) {

    val PREFERENCE_NAME = "SharedPreferenceExample"
    val PREFERENCE_LOGGED = "isLogged"
    val PREFERENCE_NIS = "nis"
    val PREFERENCE_USER_NAME = "name"
    val PREFERENCE_RAYON = "rayon"
    val PREFERENCE_ROMBEL = "rombel"
    val PREFERENCE_PASSWORD = "password"
    val PREFERENCE_IMAGE = "image"
    val PREFERENCE_QRCODE = "qrcode"

    val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = preferences.edit()

    fun getLogged(): Boolean {
        return preferences.getBoolean(PREFERENCE_LOGGED, false)
    }

    fun setLogged(logged: Boolean) {
        editor.putBoolean(PREFERENCE_LOGGED, logged)
        editor.apply()
    }

    fun getNis(): String {
        return preferences.getString(PREFERENCE_NIS, "")
    }

    fun setNis(nis: String) {
        editor.putString(PREFERENCE_NIS, nis)
        editor.apply()
    }

    fun getName(): String {
        return preferences.getString(PREFERENCE_USER_NAME, "")
    }

    fun setName(name: String) {
        editor.putString(PREFERENCE_USER_NAME, name)
        editor.apply()
    }

    fun getRayon(): String {
        return preferences.getString(PREFERENCE_RAYON, "")
    }

    fun setRayon(rayon: String) {
        editor.putString(PREFERENCE_RAYON, rayon)
        editor.apply()
    }

    fun getRombel(): String {
        return preferences.getString(PREFERENCE_ROMBEL, "")
    }

    fun setRombel(rombel: String) {
        editor.putString(PREFERENCE_ROMBEL, rombel)
        editor.apply()
    }

    fun getPassword(): String {
        return preferences.getString(PREFERENCE_PASSWORD, "")
    }

    fun setPassword(password: String) {
        editor.putString(PREFERENCE_PASSWORD, password)
        editor.apply()
    }

    fun getImage(): String {
        return preferences.getString(PREFERENCE_IMAGE, "")
    }

    fun setImage(image: String) {
        editor.putString(PREFERENCE_IMAGE, image)
        editor.apply()
    }

    fun getQrcode(): String {
        return preferences.getString(PREFERENCE_QRCODE, "")
    }

    fun setQrcode(qrcode: String) {
        editor.putString(PREFERENCE_QRCODE, qrcode)
        editor.apply()
    }


}