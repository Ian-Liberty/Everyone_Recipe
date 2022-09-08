package com.example.everyone_recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.everyone_recipe.Fragment.FragHome;
import com.example.everyone_recipe.Fragment.FragMyinfo;
import com.example.everyone_recipe.Fragment.FragRecipe_registration;
import com.example.everyone_recipe.Fragment.FragSearch;
import com.example.everyone_recipe.Fragment.FragShopping_basket;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONObject;

public class Home extends AppCompatActivity {

    private TextView textView4;
    private Intent userintent;
    private String userinfo;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userintent=getIntent();
        userinfo = userintent.getStringExtra("data");

        bottomNavigationView = findViewById(R.id.bottomNavi);

        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new FragHome()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragHome()).commit();
                        break;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragSearch()).commit();
                        break;
                    case R.id.recipe:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragRecipe_registration()).commit();
                        break;
                    case R.id.shopping_basket:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragShopping_basket()).commit();
                        break;
                    case R.id.myinfo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragMyinfo()).commit();
                        break;
                }

                return true;
            }
        });

    }
}