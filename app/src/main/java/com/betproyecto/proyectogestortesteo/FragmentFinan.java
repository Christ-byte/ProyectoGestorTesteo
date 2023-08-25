package com.betproyecto.proyectogestortesteo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.betproyecto.proyectogestortesteo.Apaters.PagerAdapterView;
import com.google.android.material.tabs.TabLayout;

public class FragmentFinan extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finan, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        // Create an adapter for the ViewPager
        PagerAdapterView pagerAdapter = new PagerAdapterView(getChildFragmentManager(), tabLayout.getTabCount());

        // Configure the adapter on the ViewPager
        viewPager.setAdapter(pagerAdapter);

        // Add a page change listener to sync tab selection with ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Add a tab selected listener to update ViewPager when a tab is selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


}
