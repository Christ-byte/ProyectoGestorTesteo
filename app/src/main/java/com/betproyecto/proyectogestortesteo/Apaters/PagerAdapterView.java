package com.betproyecto.proyectogestortesteo.Apaters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.betproyecto.proyectogestortesteo.FragmentIngresos;
import com.betproyecto.proyectogestortesteo.FragmentGastos;

public class PagerAdapterView extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public PagerAdapterView(FragmentManager fm, int numOfTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentGastos();
            case 1:
                return new FragmentIngresos();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
