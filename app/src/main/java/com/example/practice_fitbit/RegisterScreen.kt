package com.example.practice_fitbit


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practice_fitbit.databinding.ActivityRegisterScreenBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class RegisterScreen() : AppCompatActivity() {
    //viewBinding
    private lateinit var binding: ActivityRegisterScreenBinding
    //actionBar
    //private lateinit var actionBar: ActionBar
    //progressDialog
    private lateinit var progressDialog: ProgressDialog
    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email11=""
    private var password11=""
    private var confpswd11=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, Open loginScreen
        binding.reg.setOnClickListener{
            startActivity(Intent(this,LoginScreen::class.java))
        }

        //handle click, begin register
        binding.regbtn.setOnClickListener{
            //validate data
            validateData()
        }


    }

    private fun validateData() {
        //get data
        email11 = binding.email12.text.toString().trim()
        password11 = binding.password12.text.toString().trim()
        confpswd11 = binding.password12.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email11).matches()){
            //invalid email format
            binding.email12.error ="Invalid email format"
        }
        else if (TextUtils.isEmpty(password11)){
            //no password entered
            binding.password12.error ="Please enter password"
        }
        else if(password11.length <6 ){
            //password lenght is less than 6
            binding.password12.error =" Password must be less than 6 characters"
        }
        else if (TextUtils.isEmpty(confpswd11)){
            //no password entered
            binding.password12.error ="Please enter confirm password"
        }
        else if(password11!=confpswd11 ){
            //password does not match
            binding.password12.error ="Password does'nt matched"
        }
        else{
            //data is valid, register
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {
        //show progress
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email11,password11)
            .addOnSuccessListener {
                //login successed
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with $email",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener{ e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Registration failed dur to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go back to previous activity, when back button of actionbar is clicked
        return super.onSupportNavigateUp()
    }



    companion object {
        // defining our own password pattern
        private val PASSWORD_PATTERN = Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +  // at least 1 special character
                    "(?=\\S+$)" +  // no white spaces
                    ".{4,}" +  // at least 4 characters
                    "$"         //Allowed
        )
    }
}

