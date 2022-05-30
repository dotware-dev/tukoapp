package com.dotware.tuko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()/*,ClientAppointments_Fragment.OnFragmentInteractionListener */{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val clientHome_Fragment=ClientHome_Fragment()
        val clientAppointments_Fragment=ClientAppointments_Fragment()
        val clientContact_Fragment=ClientContact_Fragment()
        onProfilePicClick()
        setCurrentFragment(clientHome_Fragment)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val textView=findViewById<TextView>(R.id.fragment_txt)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeMenuItem->{
                    setCurrentFragment(clientHome_Fragment)
                    textView.text = it.title
                    true
                }
                R.id.apptmtsMenuItem->{
                    setCurrentFragment(clientAppointments_Fragment)
                    textView.text = it.title
                    true
                }
                R.id.contactMenuItem->{
                    setCurrentFragment(clientContact_Fragment)
                    textView.text = it.title
                    true
                }
                else->{
                    setCurrentFragment(clientHome_Fragment)
                    textView.text = "Home"
                    true
                }
            }
        }

    }

//    override fun onFragmentInteraction(string: String) {
//        TODO("Not yet implemented")
//    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragments,fragment)
            commit()
        }

    private fun onProfilePicClick(){
        val profilePic=findViewById<ImageView>(R.id.profile_img)
        profilePic.setOnClickListener {
            var intent = Intent(this, ProfileEditActivity::class.java)
            startActivity(intent)
        }
    }
}