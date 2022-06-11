package com.dotware.tuko;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static User user;
    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        SessionManager.user = user;
    }


    public static void Login(String name, String email, String token, String description) {
        user = new User(name, email, token, description);
    }

    public static Boolean isLoggedIn() {
        return user != null;
    }

    public static void logout() {
        user = null;
    }


    public static void setDescription(String description){
        user.setDescription(description);
    }




    public static String getDescription(){
        return user.getDescription();
    }

    public static void updateUser(String name, String email, String token, String description){
        user.setName(name);
        user.setEmail(email);
        user.setDescription(description);
    }

}
