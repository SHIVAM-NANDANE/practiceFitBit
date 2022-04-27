package com.example.practice_fitbit.Dashboard

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import coil.load
import com.example.practice_fitbit.MainScreens.LoginScreen
import com.example.practice_fitbit.R
import com.example.practice_fitbit.User
import com.example.practice_fitbit.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener


class Profile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    private lateinit var savebutton : Button
    private lateinit var backbtn: ImageButton
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri : Uri
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        savebutton = findViewById(R.id.savebtn)
        backbtn = findViewById(R.id.backbtn)
        auth = FirebaseAuth.getInstance()
        val uid =  auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        backbtn.setOnClickListener{
            Toast.makeText(this@Profile,"Back to Dashboard",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }

        binding.savebtn.setOnClickListener {
            showProgressBar()
            val firstName = binding.firstname.text.toString()
            val lastName =  binding.lastname.text.toString()

//            if (firstName.isEmpty() || lastName.isEmpty()){
//                Toast.makeText(this, "Please enter text" ,Toast.LENGTH_SHORT).show()
//            }

            val user = User(firstName,lastName)
            if(uid != null ){

                databaseReference.child(uid).setValue(user).addOnCompleteListener {

                    if (it.isSuccessful){

                        uploadProfilePic()

                    }else{
                        hideProgressBar()
                        Toast.makeText(this@Profile,"Failed to update profile",Toast.LENGTH_SHORT).show()
                    }


                }

            }

//            Toast.makeText(this@Profile,"Profile Updated",Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, Dashboard::class.java))
//            finish()

        }


        // get reference to the string array that we just created
        val ages = resources.getStringArray(R.array.Age)
        val height = resources.getStringArray(R.array.Height)
        val weight = resources.getStringArray(R.array.Weight)

        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapterage = ArrayAdapter(this, R.layout.dropdown_item, ages)
        val arrayAdapterheight = ArrayAdapter(this, R.layout.dropdown_item, height)
        val arrayAdapterweight = ArrayAdapter(this, R.layout.dropdown_item, weight)

        // get reference to the autocomplete text view
        val autocompleteTV1 = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView1)
        val autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        val autocompleteTV3 = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView3)

        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV1.setAdapter(arrayAdapterage)
        autocompleteTV2.setAdapter(arrayAdapterheight)
        autocompleteTV3.setAdapter(arrayAdapterweight)


        //when you click on the image
        binding.profilelogo.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this)
            pictureDialog.setTitle("Select Action")
            val pictureDialogItem = arrayOf("Select photo from Gallery",
                "Capture photo from Camera")
            pictureDialog.setItems(pictureDialogItem) { dialog, which ->

                when (which) {
                    0 -> gallery()
                    1 -> camera()
                }
            }

            pictureDialog.show()
        }

    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.defaultprofile}")
        storageReference = FirebaseStorage.getInstance().getReference("Users"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            hideProgressBar()
            Toast.makeText(this@Profile,"Profile successfuly uploaded",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Dashboard::class.java))

        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(this@Profile,"Failed to update image",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar(){
        dialog = Dialog(this@Profile)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

    private fun galleryCheckPermission() {

        Dexter.withContext(this).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                gallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    this@Profile,
                    "You have denied the storage permission to select image",
                    Toast.LENGTH_SHORT
                ).show()
                showRotationalDialogForPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?, p1: PermissionToken?) {
                showRotationalDialogForPermission()
            }
        }).onSameThread().check()
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }


    private fun cameraCheckPermission() {

        Dexter.withContext(this)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {

                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?) {
                        showRotationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
    }


    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    val bitmap = data?.extras?.get("data") as Bitmap

                    //we are using coroutine image loader (coil)
                    binding.profilelogo.load(bitmap)
                }

                GALLERY_REQUEST_CODE -> {

                    binding.profilelogo.load(data?.data) {
                    }

                }
            }

        }

    }


    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage("It looks like you have turned off permissions"
                    + "required for this feature. It can be enable under App settings!!!")

            .setPositiveButton("Go TO SETTINGS") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}