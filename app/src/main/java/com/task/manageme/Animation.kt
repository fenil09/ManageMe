package com.task.manageme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class Animation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
      val timer: Timer= Timer()
        timer.schedule(2500){
            gotologin()
            finish()
        }
    }

    fun gotologin(){
        val intent:Intent= Intent(this,Login::class.java)
        startActivity(intent)
    }
}