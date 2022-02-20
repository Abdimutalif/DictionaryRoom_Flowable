package com.uz.dictionaryroom_flowable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
//    override fun onSupportNavigateUp(): Boolean {
//        return Navigation.findNavController(this, R.id.my_nav_host_fragment).navigateUp()
//    }
}