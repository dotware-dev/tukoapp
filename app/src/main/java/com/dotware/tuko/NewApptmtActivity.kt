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

class NewApptmtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        setContentView(R.layout.activity_new_apptmt)
        val backBtn=findViewById<Button>(R.id.back_btn)
        backBtn.setOnClickListener {
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val submitBtn=findViewById<Button>(R.id.button4)
        submitBtn.setOnClickListener {
            saveData()
        }
    }

    private fun saveData(){
        val device=findViewById<EditText>(R.id.editTextTextPersonName2)
        val issue=findViewById<EditText>(R.id.editTextTextPersonName4)
        val date=findViewById<EditText>(R.id.editTextDate)
        val time=findViewById<EditText>(R.id.editTextDate2)
        newApptmt(device.text.toString(),issue.text.toString(),date.text.toString(),time.text.toString())
    }

    private fun newApptmt(device:String, issue:String, date: String, time: String){
        val user=SessionManager.getUser()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.79/apituko/citas?devicename=${device}&issue=${issue}&apptmntdate=${date}&apptmnttime=${time}&usertoken=${user.token}"
        val postRequest= JsonObjectRequest(
            Request.Method.GET,url, null,
            Response.Listener { response ->
                when (response.getString("status")) {
                    "success" -> {
                        val intent = Intent(this, AppointmentActivity::class.java)
                        intent.putExtra("token", response.getString("token"))
                        intent.putExtra("issue", response.getString("issue"))
                        intent.putExtra("date", response.getString("apptmntdate"))
                        intent.putExtra("device", response.getString("devicename"))
                        intent.putExtra("status", response.getString("devicestatus"))
                        startActivity(intent)
                        finish()

                    }
                    else -> {
                        Toast.makeText(this, "New Appointment Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()
            })
        queue.add(postRequest)
    }
}