package com.example.mayoqcar.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.mayoqcar.models.Worker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MySharedPreference {
    private const val PREFERENCES_NAME = "my_preferences"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }


    fun setUser(worker: Worker) {
        val editor = sharedPreferences.edit()
        editor.putString("worker", workerToJson(worker))
        editor.apply()
    }

    fun getWorker(): Worker = jsonToWorker(sharedPreferences.getString("worker", "{}") ?: "")

    fun jsonToWorker(string: String): Worker {
        val gson = Gson()
        val type = object : TypeToken<Worker>() {}.type
        return gson.fromJson(string, type)
    }

    fun workerToJson(worker: Worker): String = Gson().toJson(worker)

}
