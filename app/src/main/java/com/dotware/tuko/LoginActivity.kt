package com.dotware.tuko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
            validate("Juan","123")
        }
    }
    private fun validate(email: String, password: String){
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.79/apituko/login"
        var params=HashMap<String,String>()
        params["email"]="juanjoselarios@gmail.com"
        params["password"]="123"
        val postRequest=JsonObjectRequest(Request.Method.GET,url, null,
            Response.Listener { response ->
                Toast.makeText(this,response.getString("success"),Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            })
        queue.add(postRequest)
    }
}