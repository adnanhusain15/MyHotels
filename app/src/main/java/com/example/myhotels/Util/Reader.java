package com.example.myhotels.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/* THIS CLASS PROVIDE METHODS FOR READING JSON FILES FROM ASSETS AS WELL AS FROM SHARED PREFERENCES 
AND RETURNS LIST OF SUITABLE OBJECTS.*/
public class Reader {
    public static String loadJSONFromAsset(Context context,String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    public static List<Hotel> getRestaurantList(Context context){
        ObjectMapper mapper = new ObjectMapper();
        String json = loadJSONFromAsset(context, "hotels.json");
        try{
            return Arrays.asList(mapper.readValue(json, Hotel[].class));


        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
    public static User getUserList(Context context){
        ObjectMapper mapper = new ObjectMapper();
        String json = loadJSONFromAsset(context, "users.json");
        try{
            return (mapper.readValue(json, User.class));


        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
    public static ArrayList<Bookings> getBookingsList(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String json = pref.getString("bookings", "xyz");
        if (json.equals("xyz")) return null;
        else {
            try {
                // ArrayList<Bookings> bookings = new ArrayList<Bookings>();
                List<Bookings> bookinglist = new ArrayList<>();
                bookinglist = Arrays.asList(mapper.readValue(json, Bookings[].class));
                ArrayList<Bookings> bookings = new ArrayList<Bookings>(bookinglist);
                return bookings;

            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }
    public static ArrayList<Drafts> getDraftsList(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String json = pref.getString("drafts", "xyz");
        if (json.equals("xyz")) return null;
        else {
            try {

                List<Drafts> draftlist = new ArrayList<>();
                draftlist = Arrays.asList(mapper.readValue(json, Drafts[].class));
                ArrayList<Drafts> drafts = new ArrayList<Drafts>(draftlist);
                return drafts;

            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }
}
