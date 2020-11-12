package com.example.resident_alert;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    //variables
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    //Session names
    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";




    //Login session variables
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_CITY = "city";
    public static final String KEY_STREET = "street";
    public static final String KEY_BLOCK = "block";
    public static final String KEY_FLATLETTER = "flatLetter";
    public static final String KEY_FLAT = "flat";

    // RememberMe session variables
    private static final String IS_REMEMBERME = "IsLoggedIn";
    public static final String KEY_PHONE_rememberme = "phone";
    public static final String KEY_PASSWORD = "password";

    // Constructor
    public SessionManager(Context _context,String sessionName){
        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }


    // Login Session
    public void createLoginSession(String name,String surname,String email,String phone,String city,
                             String street,String block,String flatLetter,String flat){

        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_NAME,name);
        editor.putString(KEY_SURNAME,surname);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PHONE,phone);
        editor.putString(KEY_CITY,city);
        editor.putString(KEY_STREET,street);
        editor.putString(KEY_BLOCK,block);
        editor.putString(KEY_FLATLETTER,flatLetter);
        editor.putString(KEY_FLAT,flat);

        editor.commit();

    }

    public HashMap<String,String> getUserDetailFromSession(){
        HashMap<String,String> userData = new HashMap<String,String>();

        userData.put(KEY_NAME, userSession.getString(KEY_NAME,null));
        userData.put(KEY_SURNAME, userSession.getString(KEY_SURNAME,null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL,null));
        userData.put(KEY_PHONE, userSession.getString(KEY_PHONE,null));
        userData.put(KEY_CITY, userSession.getString(KEY_CITY,null));
        userData.put(KEY_STREET, userSession.getString(KEY_STREET,null));
        userData.put(KEY_BLOCK, userSession.getString(KEY_BLOCK,null));
        userData.put(KEY_FLATLETTER, userSession.getString(KEY_FLATLETTER,null));
        userData.put(KEY_FLAT, userSession.getString(KEY_FLAT,null));
        return userData;
    }

    public boolean checkLogin(){
        if(userSession.getBoolean(IS_LOGIN,true)){
            return true;
        } else {
            return false;
        }
    }
    public void logoutUserSession(){
        editor.clear();
        editor.commit();

    }


    // RememberMe session
    public void createRememberMeSession(String phone,String password){

        editor.putBoolean(IS_REMEMBERME,true);

        editor.putString(KEY_PHONE_rememberme,phone);
        editor.putString(KEY_PASSWORD,password);

        editor.commit();

    }
    public HashMap<String,String> getRememberMeDetailFromSession(){
        HashMap<String,String> userData = new HashMap<String,String>();

        userData.put(KEY_PHONE_rememberme, userSession.getString(KEY_PHONE_rememberme,null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD,null));
        return userData;
    }
    public boolean checkRememberMe(){
        if(userSession.getBoolean(IS_REMEMBERME,true)){
            return true;
        } else {
            return false;
        }
    }
    public void removeDataRememberMe(){
        editor.remove(KEY_PHONE_rememberme);
        editor.remove(KEY_PASSWORD);
        editor.commit();

    }

}
