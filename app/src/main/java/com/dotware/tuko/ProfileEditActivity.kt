package com.dotware.tuko

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class ProfileEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        val backBtn=findViewById<Button>(R.id.back_btn2)
        backBtn.setOnClickListener {
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        changeProfile()

    }

    private fun changeProfile(){
        val changeProfileBtn=findViewById<Button>(R.id.chng_profile_pic)
        changeProfileBtn.setOnClickListener {
            requestPermission()
        }
    }

    private fun requestPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )==PackageManager.PERMISSION_GRANTED->{
                    pickPhotoFromGallery()
                }
                else->requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            pickPhotoFromGallery()
        }
    }

    private val requestPermissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted->
        if(isGranted){
            pickPhotoFromGallery()
        }else{
            Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()

        }
    }

    private val startForActivityGallery=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result->
        if(result.resultCode== Activity.RESULT_OK){
            val data=result.data?.data
            val profilePic=findViewById<ImageView>(R.id.profile_img_img)
            profilePic.setImageURI(data)
        }
    }

    private fun pickPhotoFromGallery() {
        val intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startForActivityGallery.launch(intent)
    }
}