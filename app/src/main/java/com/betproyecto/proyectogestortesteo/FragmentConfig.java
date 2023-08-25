package com.betproyecto.proyectogestortesteo;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import com.betproyecto.proyectogestortesteo.login.LoginActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;


public class FragmentConfig extends Fragment {

    private SwitchMaterial switchTheme;
    private Button buttonLogout;

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);

        switchTheme = view.findViewById(R.id.swichtDark);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switchTheme.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Cambiar al tema oscuro
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    // Cambiar al tema claro
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
            // Inicializa el botón de "Cerrar sesión"
            buttonLogout = view.findViewById(R.id.buttonLogout);
            buttonLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutUser();
                }
            });


        return view;
        }



    private void logoutUser() {
        // Utiliza FirebaseAuth para cerrar la sesión actual del usuario
        FirebaseAuth.getInstance().signOut();

        // Borra la información de inicio de sesión guardada en SharedPreferences
        saveLoginStatus(false);

        // Dirige al usuario de vuelta a LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish(); // Finaliza la actividad que contiene el fragmento
    }

    private void saveLoginStatus(boolean isLoggedIn) {
        // Obtén el contexto de la actividad que contiene el fragmento
        Context context = getActivity();

        // Llama a getSharedPreferences() en el contexto para obtener el objeto SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences("login_status", MODE_PRIVATE);

        // Guarda el estado de inicio de sesión del usuario
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

}