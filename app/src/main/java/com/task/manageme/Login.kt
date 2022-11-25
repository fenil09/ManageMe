package com.task.manageme

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.room.Room
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ncorti.slidetoact.SlideToActView
import java.util.Calendar
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

class Login : AppCompatActivity(),SlideToActView.OnSlideCompleteListener{
    lateinit var usermail:EditText
    lateinit var userpassword:EditText
    lateinit var dialog:AlertDialog.Builder
    lateinit var gsc:GoogleSignInClient
    lateinit var mauth:FirebaseAuth
    val reqcode:Int=153
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usermail=findViewById(R.id.edit1)
        userpassword=findViewById(R.id.edit2)
        val txtview:TextView=findViewById(R.id.textView1)
        val slide:SlideToActView=findViewById(R.id.slide1)
        val login: Button =findViewById(R.id.button1)

        mauth= FirebaseAuth.getInstance()
        var email:String=""
        var password:String=""
        login.setOnClickListener {
            val emaildata=usermail.text.toString()
            val passdata=userpassword.text.toString()

            if(emaildata.isEmpty() || passdata.isEmpty()){
                Toast.makeText(this,"sorry please enter all of your details",Toast.LENGTH_LONG).show()
            }
            else{
                Thread(Runnable {
                    val room=Room.databaseBuilder(this,userauthdatabase::class.java,"userdatabase").build()
                    email=room.getauthuser().getuseremail()
                    password= room.getauthuser().getuserpassword()
                    if(email.isNullOrEmpty() || password.isNullOrEmpty()){
                        loginwithfirebase()
                    }
                    else{
                        Thread(Runnable {
                            loginwithRoom()
                        }).start()
                    }
                }).start()

            }


        }

        slide.onSlideCompleteListener=this
    }

    fun loginwithfirebase(){
        val mail=usermail.text.toString()
        val pass=userpassword.text.toString()
        mauth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(this, OnCompleteListener {

            if(it.isSuccessful){
                val intent:Intent= Intent(this,Home::class.java)
                startActivity(intent)
                Toast.makeText(this,"Login Successfull",Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this,"Sorry some server side error occured plase try again",Toast.LENGTH_LONG).show()
            }
        })
    }


    fun loginwithRoom(){
        val room=Room.databaseBuilder(this,userauthdatabase::class.java,"userdatabase").build()
       val email=room.getauthuser().getuseremail()
        val password=room.getauthuser().getuserpassword()
        val mail=usermail.text.toString()
        val pass=userpassword.text.toString()

        if(email.equals(mail) && password.equals(pass)){
            val intent:Intent=Intent(this,Home::class.java)
            startActivity(intent)
            this.runOnUiThread(){
                Toast.makeText(this,"Login Successfull via room",Toast.LENGTH_LONG).show()
            }
            finish()

        }
        else{
          this.runOnUiThread(){
              Toast.makeText(this,"sorry some server side error occured please try again",Toast.LENGTH_LONG).show()
          }
        }
    }

    override fun onSlideComplete(view: SlideToActView) {
        val gso: GoogleSignInOptions =GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        gsc= GoogleSignIn.getClient(this,gso)
        GoogleSigIn()
    }

    fun GoogleSigIn(){
        val intent:Intent=gsc.signInIntent
        startActivityForResult(intent,reqcode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==reqcode){
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account:GoogleSignInAccount=task.result
            authwithfirebase(account.idToken)
        }
    }


    fun authwithfirebase(idtoken:String?){
        val credentials:AuthCredential=GoogleAuthProvider.getCredential(idtoken,null)
        FirebaseAuth.getInstance().signInWithCredential(credentials).addOnCompleteListener(this, OnCompleteListener {

            if(it.isSuccessful){
                Toast.makeText(this,"Google SignIn Successfull",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"sorry some server side error occured",Toast.LENGTH_LONG).show()
            }
        })
    }


}