package com.betproyecto.proyectogestortesteo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.betproyecto.proyectogestortesteo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new FragmentPrincipal());
        binding.MainBottomNaView.setBackground(null);

        binding.MainBottomNaView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new FragmentPrincipal());
                binding.MainBottomNaView.setBackground(null);
            } else if (itemId == R.id.pocket) {
                replaceFragment(new FragmentPocket());
                binding.MainBottomNaView.setBackground(null);
            } else if (itemId == R.id.statis) {
                replaceFragment(new FragmentStatis());
                binding.MainBottomNaView.setBackground(null);
            } else if (itemId == R.id.config) {
                replaceFragment(new FragmentConfig());
                binding.MainBottomNaView.setBackground(null);
            }

            return true;
        });
        binding.MainFloatActButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentFinan());
            }
        });



    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}
