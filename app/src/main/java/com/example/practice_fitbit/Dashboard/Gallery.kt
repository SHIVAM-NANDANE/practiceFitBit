package com.example.practice_fitbit.Dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.practice_fitbit.User
import com.example.practice_fitbit.databinding.GalleryPickerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Gallery :AppCompatActivity() {
    private lateinit var binding: GalleryPickerBinding
    private val GALLERY_REQUEST_CODE = 1
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseDatabase
    private lateinit var imageUri: Uri
    private lateinit var height: EditText
    private lateinit var dialog: AlertDialog.Builder
    private lateinit var databaseReference: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GalleryPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        val uid =  auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Profile/")


        //when you click on the Profileimage
        binding.profilelogo.setOnClickListener {

            dialog = AlertDialog.Builder(this)
            dialog.setTitle("Select Action")
            val pictureDialogItem = arrayOf(
                "Select photo from Gallery"
            )
            dialog.setItems(pictureDialogItem) { dialog, which ->

                when (which) {
                    0 -> gallery()
                }
            }

            dialog.show()
        }
        binding.savebtn.setOnClickListener {
             if(uid != null) {
                 uploadFile()
             }
        }


    }

    //onCreate Ends here..

    //Opens gallery
    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }


    //Uploading profile pic
    private fun uploadFile() {

            val reference = storage.reference.child("Profile/")
                .child(auth.currentUser?.uid + "jpg") //here we define where to store image in database

             reference.putFile(imageUri).addOnCompleteListener {
                if (it.isSuccessful) {
                    reference.downloadUrl.addOnSuccessListener { task ->       //here we fetch the file to display later.
                        uploadInfo(task.toString())
                    }
                }
             }
    }

    //getting user info and save to database
    private fun uploadInfo(imgUrl: String) {
        val user =
            User(auth.uid.toString(), binding.firstname.text.toString(), binding.lastname.text.toString(),imgUrl,binding.heightet.text.toString())
        database.reference.child("NewUsers")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Data inserted ", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, Dashboard::class.java))
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null ) {    //check if file path is empty


            imageUri = data.data!!  //got image from gallery\


        }


        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                GALLERY_REQUEST_CODE -> {

                    binding.profilelogo.load(data?.data) {

                    }

                }
            }

        }
    }
}


