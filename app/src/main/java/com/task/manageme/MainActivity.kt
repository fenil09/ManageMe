package com.task.manageme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ViewFlipper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        supportActionBar?.hide()
        val gotologin: Button =findViewById(R.id.button1)
        gotologin.setOnClickListener {
            gotologin()
        }
    }

    fun gotologin(){
        val intent= Intent(this,Animation::class.java)
        startActivity(intent)
    }

}