package com.example.everyone_recipe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.everyone_recipe.R;

public class FragMyinfo_my_recipe extends Fragment {

    private View view;

    public static FragMyinfo_my_recipe newinstance(){
        FragMyinfo_my_recipe frag_my_recipe = new FragMyinfo_my_recipe();
        return frag_my_recipe;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_myinfo_myrecipe, container, false);

        return view;
    }
}
