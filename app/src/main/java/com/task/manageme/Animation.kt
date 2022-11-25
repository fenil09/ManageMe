package com.task.manageme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class Animation : AppCompatActivity() {
    var checkcounter=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
      val timer: Timer= Timer()
        timer.schedule(2500){
            gotodestination()
            finish()
        }


    }

    fun gotodestination(){

        val data1:String=intent.getStringExtra("activity1").toString()
        val data2:String=intent.getStringExtra("activity2").toString()

        if(checkcounter==0){
            if(data1=="loginactivity"){
                val intent:Intent=Intent(this,Login::class.java)
                startActivity(intent)
            }
            else{
                if(data2=="registeractivity"){
                    val intent:Intent=Intent(this,Registration::class.java)
                    startActivity(intent)
                }
            }
        }
        else{
            finish()
        }

        }

    override fun onBackPressed() {
        checkcounter++
        super.onBackPressed()

    }



    }
