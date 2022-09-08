package com.example.everyone_recipe.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.everyone_recipe.Adapter.ViewPager2Adapter;
import com.example.everyone_recipe.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;


public class FragMyinfo extends Fragment {
    ViewPager2Adapter viewPager2Adapter;
    private ViewGroup viewGroup;// 뷰그룹 객체 선언
    ViewPager2 viewpager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_frag_myinfo, container, false);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager2Adapter = new ViewPager2Adapter(this);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        viewpager = view.findViewById(R.id.viewPager);
        viewpager.setAdapter(viewPager2Adapter);

        final List<String> tabElement = Arrays.asList("내 레시피","좋아요 한 레시피");

        new TabLayoutMediator(tabLayout, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                TextView textView = new TextView(getActivity());
                textView.setText(tabElement.get(position));
                tab.setCustomView(textView);
            }
        }).attach();



    }


}