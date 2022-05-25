package com.example.practice_fitbit

import android.app.Dialog
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.practice_fitbit.databinding.ActivityUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog
    private lateinit var user: User
    private lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("NewUsers")  //checking in database if user available or not
        if (uid.isNotEmpty()){

            getuserData()

        }


    }

    private fun getuserData() {   //getting user information from database
        showProgressBar()
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{  //listener for changes in the data at this location. Each time the data changes, your listener will be called with an immutable snapshot of the data.
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.FullName.text = user.firstName + " " + user.lastName
                binding.heightinfo.text = "height is " + user.heightet + "ft"
                getUserProfile()
            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
                Toast.makeText(this@UserProfileActivity,"Failed to get user profile data ",Toast.LENGTH_SHORT).show()
            }


        })
    }

    //get user image from database
    private fun getUserProfile() {
        storageReference =FirebaseStorage.getInstance().getReference("Profile/" + auth.currentUser?.uid + "jpg")
        val localFile = File.createTempFile( "tempImg", "jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profilelogo.setImageBitmap(bitmap)
            hideProgressBar()

        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(this@UserProfileActivity,"Failed to retrive image",Toast.LENGTH_SHORT).show()

        }
    }

    private fun showProgressBar(){
        dialog = Dialog(this@UserProfileActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

}