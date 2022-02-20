package com.uz.dictionaryroom_flowable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
                finish()
            },2000)

    }


}