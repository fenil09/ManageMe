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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ncorti.slidetoact.SlideToActView
import java.util.Calendar
import java.util.Timer
import kotlin.concurrent.schedule

class Registration : AppCompatActivity(),SlideToActView.OnSlideCompleteListener{
    lateinit var mail:EditText
    lateinit var pass:EditText
    lateinit var mauth:FirebaseAuth
    var checkcounter=0
    lateinit var dialog:Dialog
  lateinit var swipe:SlideToActView
  lateinit var gsc:GoogleSignInClient
  val reqcode=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val register: Button =findViewById(R.id.button1)
        mail=findViewById(R.id.edit1)
        pass=findViewById(R.id.edit3)
        swipe=findViewById(R.id.slide1)
        mauth=FirebaseAuth.getInstance()
        register.setOnClickListener {
            val accessvariables:accessvariables=accessvariables(mail.text.toString(),pass.text.toString())
            if(accessvariables.email.isEmpty() ||  accessvariables.password.isEmpty()){
                Toast.makeText(this,"please enter all your details",Toast.LENGTH_LONG).show()
            }
            else{
                showanimation()
                addusertoFirebase()
            }
        }

        swipe.onSlideCompleteListener=this


    }



    fun createuser(){
        val accessvariables:accessvariables=accessvariables(mail.text.toString(),pass.text.toString())
        val roomdatabase: userauthdatabase =Room.databaseBuilder(this,userauthdatabase::class.java,"userdatabase").build()
        roomdatabase.getauthuser().insert(userinfo(Calendar.getInstance().timeInMillis.toInt(),accessvariables.email,accessvariables.password))
    }

    fun addusertoFirebase(){
       val email=mail.text.toString()
        val password=pass.text.toString()
      mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
          OnCompleteListener {

              if(it.isSuccessful){
                  gohome()
                 Thread(Runnable {
                     createuser()
                 }).start()
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

    override fun onSlideComplete(view: SlideToActView) {
        val gso: GoogleSignInOptions =GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken((getString(R.string.default_web_client_id)))
            .requestEmail()
            .build()

       gsc=GoogleSignIn.getClient(this,gso)
        GooglesignIn()
    }


    fun GooglesignIn(){
        val intent:Intent=gsc.signInIntent
        startActivityForResult(intent,reqcode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==reqcode){
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account:GoogleSignInAccount = task.result
            AuthWithGoogle(account.idToken)
        }
    }


    fun AuthWithGoogle(idtoken:String?){
        val auth:AuthCredential=GoogleAuthProvider.getCredential(idtoken,null)
        FirebaseAuth.getInstance().signInWithCredential(auth).addOnCompleteListener(this, OnCompleteListener {

            if(it.isSuccessful){
                val intent:Intent=Intent(this,Home::class.java)
                startActivity(intent)
                Toast.makeText(this,"SiginIn Successfull",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"sorry the task failed due to some Server Error",Toast.LENGTH_LONG).show()
            }
        })
    }




}