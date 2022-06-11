package com.dotware.tuko

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class ProfileEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
//        val profilePic=findViewById<ImageView>(R.id.profile_img_img)
//        if(!(SessionManager.getUser().profilePhoto==null)||!(SessionManager.getUser().profilePhoto.isEmpty())){
//            profilePic.setImageURI(SessionManager.getUser().profilePhoto?.toUri())
//        }
        startData()
        val backBtn=findViewById<Button>(R.id.back_btn2)
        backBtn.setOnClickListener {
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val editBtn=findViewById<Button>(R.id.button2)
        editBtn.setOnClickListener {
            saveData()
            updateUserProfile()
        }
        val logoutBtn=findViewById<Button>(R.id.button3)
        logoutBtn.setOnClickListener {
            SessionManager.logout()
            var intent= Intent(this,SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
//        changeProfile()

    }

    private fun saveData() {
        val name=findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
        val email=findViewById<EditText>(R.id.editTextTextEmailAddress5).text.toString()
        val description=findViewById<EditText>(R.id.editTextTextPersonDescription).text.toString()
        SessionManager.getUser().name=name
        SessionManager.getUser().email=email
        SessionManager.getUser().description=description
    }

    private fun startData() {
        val name=findViewById<EditText>(R.id.editTextTextPersonName3)
        val email=findViewById<EditText>(R.id.editTextTextEmailAddress5)
        val description=findViewById<EditText>(R.id.editTextTextPersonDescription)
        name.setText(SessionManager.getUser().name)
        email.setText(SessionManager.getUser().email)
        description.setText(SessionManager.getUser().description)
    }
//    private fun changeProfile(){
//        val changeProfileBtn=findViewById<Button>(R.id.chng_profile_pic)
//        changeProfileBtn.setOnClickListener {
//            requestPermission()
//        }
//    }
//
//    private fun requestPermission(){
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            when{
//                ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE
//                )==PackageManager.PERMISSION_GRANTED->{
//                    pickPhotoFromGallery()
//                }
//                else->requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//            }
//        }else{
//            pickPhotoFromGallery()
//        }
//    }
//
//    private val requestPermissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//        isGranted->
//        if(isGranted){
//            pickPhotoFromGallery()
//        }else{
//            Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
//
//        }
//    }
//
//    private val startForActivityGallery=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        result->
//        if(result.resultCode== Activity.RESULT_OK){
//            val data=result.data?.data
//            val profilePic=findViewById<ImageView>(R.id.profile_img_img)
//            profilePic.setImageURI(data)
//            SessionManager.setProfilePhoto(data.toString())
//            updateUserProfile()
//        }
//    }
//
//    private fun pickPhotoFromGallery() {
//        val intent=Intent(Intent.ACTION_GET_CONTENT)
//        intent.type="image/*"
//        startForActivityGallery.launch(intent)
//    }

    private fun updateUserProfile(){
        val user=SessionManager.getUser()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.79/apituko/user/update?name=${user.name}&email=${user.email}&token=${user.token}&description=${user.description}"
        val postRequest= JsonObjectRequest(
            Request.Method.GET,url, null,
            Response.Listener { response ->
                when (response.getString("status")) {
                    "success" -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    else -> {
                        Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()
            })
        queue.add(postRequest)
    }

//    private fun performFileSearch(messageTitle : String) {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.type = "image/*"
//        intent.data=SessionManager.getUser().profilePhoto?.toUri()
//        startForActivityGallery.launch(intent)
//    }
}