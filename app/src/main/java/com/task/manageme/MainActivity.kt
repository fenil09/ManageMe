package com.task.manageme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper

class MainActivity : AppCompatActivity() {

    var data1:String=""
    var data2:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        supportActionBar?.hide()
        val gotologin: Button =findViewById(R.id.button1)
        val textview:TextView=findViewById(R.id.textview2)
        gotologin.setOnClickListener {
            gotoanimationlogin()
        }

        textview.setOnClickListener{
            gotoanimationregister()
        }



    }

    fun gotoanimationlogin(){
        var  intent=Intent(this,Animation::class.java)
        data1="loginactivity"
        intent.putExtra("activity1",data1)
        startActivity(intent)
    }

    fun gotoanimationregister(){
        var intent=Intent(this,Animation::class.java)
        data2="registeractivity"
        intent.putExtra("activity2",data2)
        startActivity(intent)
    }



}