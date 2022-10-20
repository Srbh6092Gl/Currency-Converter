package com.srbh.converter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var mDelayHandler: Handler ?= null
    private val delay: Long = 6000
    private val apiKey = ""//your api key here

    private val runnable: Runnable = Runnable {

        var intent: Intent ?= null
        intent = if(auth.currentUser == null)
                    Intent(this, LoginActivity::class.java)
                else
                    Intent(this, HomeActivity::class.java)
        finish()
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        fetchJson()

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(runnable, delay)
    }

    override fun onDestroy() {
        if(mDelayHandler!=null)
            mDelayHandler!!.removeCallbacks(runnable)
        super.onDestroy()
    }
    private fun fetchJson() {

        var okHttpClient = OkHttpClient()

        //"https://api.apilayer.com/currency_data/live?source=&currencies="
        val httpUrl: HttpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("api.apilayer.com")
            .addPathSegment("currency_data")
            .addPathSegment("live")
            .addQueryParameter("source", "")
            .addQueryParameter("currencies","")
            .build()

        var request = Request.Builder()
            .url(httpUrl)
            .header("apikey", apiKey)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Failed request")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response?.body?.string()
                println(body)
                Log.d("onResponse: ", body.toString())
                val gson = GsonBuilder().create()
                currency = gson.fromJson(body,Currency::class.java)
            }

        })
    }
}