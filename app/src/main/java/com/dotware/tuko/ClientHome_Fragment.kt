package com.dotware.tuko

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientHome_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientHome_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientHome_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientHome_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFinishedAppntmts()
        getLatestAppntmts()
    }
    private fun getFinishedAppntmts(){
        val user=SessionManager.getUser()
        val queue: RequestQueue = Volley.newRequestQueue(activity)
        val url = "http://192.168.100.79/apituko/citas/getfinishedapptmnts?usertoken=${user.token}"
        val linearLayout = view?.findViewById<LinearLayout>(R.id.finished_linear_layout)
        val postRequest= JsonArrayRequest(
            Request.Method.GET,url, null,
            Response.Listener { response ->
                when (response.getJSONObject(0).getString("status")) {
                    "success" -> {
                        for (i in 1 until response.length()) {
                            val apptmt = response.getJSONObject(i)
                            val button = Button(activity)
                            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                            button.text = apptmt.getString("apptmntToken")
                            button.setOnClickListener{
                                onClickFunction(apptmt.getString("apptmntToken"))
                            }
                            button.background= activity?.getDrawable(R.drawable.btn_drawable_three)
                            button.setTextColor(Color.WHITE)

                            linearLayout?.addView(button);
                        }

                    }
                    else -> {
                        Toast.makeText(activity, "Fetching Appointments Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Response.ErrorListener {
                Toast.makeText(activity, "An error occured", Toast.LENGTH_SHORT).show()
            })
        queue.add(postRequest)
    }

    private fun getLatestAppntmts(){
        val user=SessionManager.getUser()
        val queue: RequestQueue = Volley.newRequestQueue(activity)
        val url = "http://192.168.100.79/apituko/citas/getlatestapptmnts?usertoken=${user.token}"
        val linearLayout = view?.findViewById<LinearLayout>(R.id.latest_linear_layout)
        val postRequest= JsonArrayRequest(
            Request.Method.GET,url, null,
            Response.Listener { response ->
                when (response.getJSONObject(0).getString("status")) {
                    "success" -> {
                        for (i in 1 until response.length()) {
                            val apptmt = response.getJSONObject(i)
                            val button = Button(activity)
                            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                            button.text = apptmt.getString("apptmntToken")
                            button.setOnClickListener{
                                onClickFunction(apptmt.getString("apptmntToken"))
                            }
                            button.background= activity?.getDrawable(R.drawable.btn_drawable_three)
                            button.setTextColor(Color.WHITE)

                            linearLayout?.addView(button);
                        }

                    }
                    else -> {
                        Toast.makeText(activity, "Fetching Appointments Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Response.ErrorListener {
                Toast.makeText(activity, "An error occured", Toast.LENGTH_SHORT).show()
            })
        queue.add(postRequest)
    }

    private fun onClickFunction(token:String){
        val user=SessionManager.getUser()
        val queue: RequestQueue = Volley.newRequestQueue(activity)
        val url = "http://192.168.100.79/apituko/citas/getappointment?token=${token}&usertoken=${user.token}"
        val postRequest= JsonObjectRequest(
            Request.Method.GET,url, null,
            Response.Listener { response ->
                when (response.getString("statusRequest")) {
                    "success" -> {
                        val intent = Intent(activity, AppointmentActivity::class.java)
                        intent.putExtra("token", response.getString("apptmntToken"))
                        intent.putExtra("issue", response.getString("issue"))
                        intent.putExtra("date", response.getString("apptmntDate"))
                        intent.putExtra("device", response.getString("devicename"))
                        intent.putExtra("status", response.getString("status"))
                        startActivity(intent)
                        activity?.finish()

                    }
                    else -> {
                        Toast.makeText(activity, "Fetching Appointment Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Response.ErrorListener {
                Toast.makeText(activity, "An error occured", Toast.LENGTH_SHORT).show()
            })
        queue.add(postRequest)
    }
}