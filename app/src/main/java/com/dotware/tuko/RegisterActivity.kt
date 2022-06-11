package com.dotware.tuko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val username= findViewById<EditText>(R.id.editTextTextPersonName)
            val password= findViewById<EditText>(R.id.editTextTextPassword2)
            val email= findViewById<EditText>(R.id.editTextTextEmailAddress2)
            register(email.text.toString(),password.text.toString(),username.text.toString())
        }
    }
    private fun register(email: String, password: String, name: String){
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.79/apituko/signup?email=$email&password=$password&name=$name"
        val postRequest= JsonObjectRequest(
            Request.Method.GET,url, null,
            Response.Listener { response ->
                when(response.getString("status")){
                    "success" -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()
            })
        queue.add(postRequest)
    }
}