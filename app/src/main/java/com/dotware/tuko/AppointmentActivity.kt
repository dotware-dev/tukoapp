package com.dotware.tuko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        getData()
        val closeButton = findViewById< Button>(R.id.back_btn4)
        closeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getData(){
        val code=findViewById<TextView>(R.id.textViewCode)
        val device=findViewById<TextView>(R.id.textViewDevice)
        val issue=findViewById<TextView>(R.id.textViewIssue)
        val date=findViewById<TextView>(R.id.textViewDate)
        val status=findViewById<TextView>(R.id.textViewStatus)
        code.text=intent.getStringExtra("token")
        device.text=intent.getStringExtra("device")
        issue.text=intent.getStringExtra("issue")
        date.text=intent.getStringExtra("date")
        status.text=intent.getStringExtra("status")
    }
}