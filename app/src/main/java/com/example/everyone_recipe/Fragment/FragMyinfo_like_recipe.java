package com.example.everyone_recipe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.everyone_recipe.R;

public class FragMyinfo_like_recipe extends Fragment {

    private View view;

    public static FragMyinfo_like_recipe newinstance(){
        FragMyinfo_like_recipe frag_like_recipe = new FragMyinfo_like_recipe();
        return frag_like_recipe;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_myinfo_likerecipe, container, false);

        return view;
    }
}
