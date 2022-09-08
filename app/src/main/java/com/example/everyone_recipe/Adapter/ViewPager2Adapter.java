package com.example.everyone_recipe.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.everyone_recipe.Fragment.FragMyinfo;
import com.example.everyone_recipe.Fragment.FragMyinfo_like_recipe;
import com.example.everyone_recipe.Fragment.FragMyinfo_my_recipe;

public class ViewPager2Adapter extends FragmentStateAdapter {

    private final int mSetItemCount = 2;// 프래그먼트 갯수 지정

    public ViewPager2Adapter(Fragment fragment){
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        int iViewIdx = getRealPosition(position);
        //새로운 프레그먼트 인스턴스

        switch(iViewIdx){
            case 0: {return new FragMyinfo_my_recipe(); }
            case 1: {return new FragMyinfo_like_recipe(); }
            default :{return new FragMyinfo_my_recipe(); }

        }
    }

    public int getRealPosition(int _iPosition){
        return  _iPosition % mSetItemCount;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
