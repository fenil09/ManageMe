package com.task.manageme

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.ncorti.slidetoact.SlideToActView
import java.util.Calendar
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

class Login : AppCompatActivity() {
    lateinit var dialog:AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val mail:EditText=findViewById(R.id.edit1)
        val pass:EditText=findViewById(R.id.edit2)
        val txtview:TextView=findViewById(R.id.textView1)
        val slide:SlideToActView=findViewById(R.id.slide1)


    }


}