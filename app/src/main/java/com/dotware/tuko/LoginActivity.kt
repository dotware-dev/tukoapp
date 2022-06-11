package com.dotware.tuko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val loginSndBtn=findViewById<Button>(R.id.loginSndBtn)
        loginSndBtn.setOnClickListener {
            val loginEmail=findViewById<EditText>(R.id.editTextTextEmailAddress)
            val loginPass=findViewById<EditText>(R.id.editTextTextPassword)
            validate(loginEmail.text.toString(),loginPass.text.toString())
        }
    }
    private fun validate(email: String, password: String){
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.79/apituko/login?email=${email}&password=${password}"
        val postRequest=JsonObjectRequest(Request.Method.GET,url, null,
            Response.Listener { response ->
                when (response.getString("status")) {
                    "success" -> {
                        SessionManager.Login(response.getString("name"),response.getString("email"),response.getString("usertoken"),response.getString("description"))
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()

                    }
                    else -> {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()
            })
        queue.add(postRequest)
    }
}