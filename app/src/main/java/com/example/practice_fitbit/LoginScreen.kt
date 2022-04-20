@file:Suppress("DEPRECATION")

package com.example.practice_fitbit


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practice_fitbit.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

@Suppress("DEPRECATION")
class LoginScreen() : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivityLoginScreenBinding
    //progressDialog
    private lateinit var progressDialog: ProgressDialog
    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email=""
    private var password=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        //checkUser()

        //handle click, Open registerScreen
        binding.reg.setOnClickListener{
            startActivity(Intent(this,RegisterScreen::class.java))
        }

        //handle click, begin login
        binding.loginbtn.setOnClickListener{
            //before logging in,validate data
            validateData()
           // startActivity(Intent(this,HomeScreen::class.java))
        }


    }

    private fun validateData() {
        //get data
        email = binding.email1.text.toString().trim()
        password = binding.password1.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.email1.error ="Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //no password entered
            binding.password1.error ="Please enter password"
        }
        else{
            //data is validate , begin login
            firebaseLogin()
        }
    }

    //Accesing firebase here

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //login successed
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Logged in as $email",Toast.LENGTH_SHORT).show()
                //open Dashboard
                startActivity(Intent(this,Dashboard::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }


    companion object {
        // defining our own password pattern
        private val PASSWORD_PATTERN = Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +  // at least 1 special character
                    "(?=\\S+$)" +  // no white spaces
                    ".{6,}" +  // at least 4 characters
                    "$"
        )
    }

}