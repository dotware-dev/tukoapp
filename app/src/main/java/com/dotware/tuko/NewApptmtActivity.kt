package com.dotware.tuko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NewApptmtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_apptmt)
        val backBtn=findViewById<Button>(R.id.back_btn)
        backBtn.setOnClickListener {
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}