package com.example.myhotels.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
/* CLASS TO WRITE NEW DATA TO SHARED PREFERENCES.*/
public class Writer {
    public static void writeBookings(Context context , ArrayList<Bookings> bookings) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String bookingjson = new Gson().toJson(bookings);
        System.out.println(bookingjson);

        editor.putString("bookings",bookingjson);
        editor.apply();
    }
    public static void writeDrafts(Context context , ArrayList<Drafts> drafts) {

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String draftjson = new Gson().toJson(drafts);
        System.out.println(draftjson);

        editor.putString("drafts",draftjson);
        editor.apply();
    }
}
