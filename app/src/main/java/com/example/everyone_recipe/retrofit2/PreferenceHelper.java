package com.example.everyone_recipe.retrofit2;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private SharedPreferences app_prefs;
    private Context context;
    private String Email = "email";

    public PreferenceHelper(Context context)
    {
        app_prefs = context.getSharedPreferences("shared", 0);
        this.context = context;
    }
}