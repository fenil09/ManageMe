package com.task.manageme

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowId
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import androidx.room.RoomDatabase
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar
import java.util.Timer
import kotlin.concurrent.schedule

class Registration : AppCompatActivity() {
    lateinit var mail:EditText
    lateinit var name:EditText
    lateinit var pass:EditText
    lateinit var mauth:FirebaseAuth
    var checkcounter=0
    lateinit var dialog:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val register: Button =findViewById(R.id.button1)
        mail=findViewById(R.id.edit1)
        name=findViewById(R.id.edit2)
        pass=findViewById(R.id.edit3)
        mauth=FirebaseAuth.getInstance()
        register.setOnClickListener {
            val accessvariables:accessvariables=accessvariables(mail.text.toString(),name.text.toString(),pass.text.toString())
            if(accessvariables.email.isEmpty() || accessvariables.username.isEmpty() || accessvariables.password.isEmpty()){
                Toast.makeText(this,"please enter all your details",Toast.LENGTH_LONG).show()
            }
            else{
                showanimation()
                addusertoFirebase()
                Thread(Runnable {
                    createuser()
                }).start()
            }
        }




    }



    fun createuser(){
        val accessvariables:accessvariables=accessvariables(mail.text.toString(),name.text.toString(),pass.text.toString())
        val roomdatabase: userauthdatabase =Room.databaseBuilder(this,userauthdatabase::class.java,"userdatabase").build()
        roomdatabase.getauthuser().insert(userinfo(Calendar.getInstance().timeInMillis.toInt(),accessvariables.email,accessvariables.username,accessvariables.password))
    }

    fun addusertoFirebase(){
        val accessvariables:accessvariables=accessvariables(mail.text.toString(),name.text.toString(),pass.text.toString())
      mauth.createUserWithEmailAndPassword(accessvariables.email,accessvariables.password).addOnCompleteListener(
          OnCompleteListener {

              if(it.isSuccessful){
                  gohome()
              }
              else{
                  Toast.makeText(this,"sorry some error occured at server side please try again",Toast.LENGTH_LONG).show()
                  dialog.dismiss()
              }

          })

        }



    fun showanimation(){
       dialog=Dialog(this)
        dialog.setContentView(R.layout.animation)
        dialog.setCancelable(false)
        dialog.show()
    }


    fun gohome(){
        val timer:Timer= Timer()
        timer.schedule(2500){
            val intent: Intent = Intent(applicationContext,Home::class.java)
            startActivity(intent)
            finish()
        }
    }






}