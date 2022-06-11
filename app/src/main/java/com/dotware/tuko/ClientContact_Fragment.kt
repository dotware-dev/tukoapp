package com.dotware.tuko

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientContact_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientContact_Fragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_client_contact, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientContact_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientContact_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startData()
        val button = view.findViewById<Button>(R.id.loginSndBtn2)
        button.setOnClickListener {
            storeData()
        }
    }
    private fun startData() {
        val name=view?.findViewById<EditText>(R.id.editTextTextEmailAddress3)
        val email=view?.findViewById<EditText>(R.id.editTextTextPersonName5)
        name?.setText(SessionManager.getUser().name)
        email?.setText(SessionManager.getUser().email)
    }
    private fun storeData() {
        val name=view?.findViewById<EditText>(R.id.editTextTextEmailAddress3)
        val email=view?.findViewById<EditText>(R.id.editTextTextPersonName5)
        val description=view?.findViewById<EditText>(R.id.editTextTextPersonDescription2)
        sendComment(name?.text.toString(),email?.text.toString(),description?.text.toString())
    }
    private fun sendComment(name:String, email:String, comment:String) {
        val user=SessionManager.getUser()
        val queue: RequestQueue = Volley.newRequestQueue(activity)
        val url = "http://192.168.100.79/apituko/comments?name=${name}&email=${email}&token=${user.token}&comment=${comment}"
        val postRequest= JsonObjectRequest(
            Request.Method.GET,url, null,
            Response.Listener { response ->
                when (response.getString("status")) {
                    "success" -> {
                        resetEditText()
                        Toast.makeText(activity, "Comment sent", Toast.LENGTH_SHORT).show()

                    }
                    else -> {
                        Toast.makeText(activity, "Comment Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Response.ErrorListener {
                Toast.makeText(activity, "An error occured", Toast.LENGTH_SHORT).show()
            })
        queue.add(postRequest)
    }

    private fun resetEditText(){
        val description=view?.findViewById<EditText>(R.id.editTextTextPersonDescription2)
        description?.setText("")
    }
}