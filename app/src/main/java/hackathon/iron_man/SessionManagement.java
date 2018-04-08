package hackathon.iron_man;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hackathon.iron_man.Activities.LoginActivity;
import hackathon.iron_man.Model.Item_fields;


public class SessionManagement {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASS = "password";
    public static final String KEY_OBJECT = "object";
    public static final String KEY_ALLITEMS_LIST = "allitems";
    public static final String KEY_INTERESTS_LIST = "interestslist";
    public static final String CART_SUM = "cartsum";





    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setAllItemsList(List<Item_fields> list) {
        String key = KEY_ALLITEMS_LIST;
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public ArrayList<Item_fields> getAllItemsList(){
        String s = pref.getString(KEY_ALLITEMS_LIST,null);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Item_fields>>() {
        }.getType();
        ArrayList<Item_fields> all_item_list = gson.fromJson(s, collectionType);
        return all_item_list;
    }

    public void setList(List<Item_fields> list) {
        String key = KEY_OBJECT;
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public List<Item_fields> getList(){
        String s = pref.getString(KEY_OBJECT,null);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Item_fields>>() {
        }.getType();
        List<Item_fields> subscribed_list = gson.fromJson(s, collectionType);
        return subscribed_list;
    }

    public void setInterestsList(ArrayList<String> list){
        String key = KEY_INTERESTS_LIST;
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public ArrayList<String> getInterestsList(){
        String s = pref.getString(KEY_INTERESTS_LIST,null);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Item_fields>>() {
        }.getType();
        ArrayList<String> all_item_list = gson.fromJson(s, collectionType);
        return all_item_list;
    }

    public void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setCartSum(long value) {
        editor.putLong(CART_SUM, value);
        editor.commit();
    }
    public long getCartSum(){
      return   pref.getLong(CART_SUM,0);
    }

    /**
     * Create login session
     * */

    public void createLoginSession(String email, String pass){

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASS, pass);

        editor.commit();
    }

    public void checkLogin(){

        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PASS, pref.getString(KEY_PASS, null));
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
         i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}