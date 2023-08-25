package com.betproyecto.proyectogestortesteo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.betproyecto.proyectogestortesteo.Apaters.MyAdapterMin;
import com.betproyecto.proyectogestortesteo.Data.FirebaseDatabaseHelper;
import com.betproyecto.proyectogestortesteo.Items.MyObject;
import com.betproyecto.proyectogestortesteo.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentPrincipal extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapterMin adapter;
    private List<MyObject> myList;
    private TextView txvTotal, txvRegalo,txvSalario,txvOtros,txvInteres;
    private Button txvVer;
    private FirebaseDatabaseHelper firebaseDatabaseHelper;
    private String currentUserId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);


        recyclerView = view.findViewById(R.id.PrincipalRecyclerView);



        txvTotal = view.findViewById(R.id.txvTotal);
        txvRegalo = view.findViewById(R.id.RegaloTotal);
        txvInteres = view.findViewById(R.id.InteresTotal);
        txvOtros = view.findViewById(R.id.OtrosTotal);
        txvSalario = view.findViewById(R.id.SalarioTotal);
        txvVer = view.findViewById(R.id.txvVerMas);

        // Configurar el LinearLayoutManager para el RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        myList = new ArrayList<>();
        adapter = new MyAdapterMin(myList,getContext());
        recyclerView.setAdapter(adapter);
        firebaseDatabaseHelper = new FirebaseDatabaseHelper();

        txvVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir el FragmentPocket
                FragmentPocket fragmentPocket = new FragmentPocket();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.MainFrameLayout, fragmentPocket);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        // Leer objetos de la base de datos
        firebaseDatabaseHelper.leerMyObjects(new FirebaseDatabaseHelper.OnMyObjectsFetchedListener() {
            @Override
            public void onMyObjectsFetched(List<MyObject> myObjects) {
                if (myObjects != null) {
                    myList.clear();
                    int startIndex = Math.max(0, myObjects.size() - 2); // Índice de inicio para obtener los últimos dos elementos
                    List<MyObject> reversedList = new ArrayList<>(myObjects.subList(startIndex, myObjects.size()));
                    Collections.reverse(reversedList);
                    myList.addAll(reversedList);
                    adapter.notifyDataSetChanged();

                }
            }
        });

        firebaseDatabaseHelper.sumarPrecios(new FirebaseDatabaseHelper.OnSumaPreciosListener() {
            @Override
            public void onSumaPrecios(double sumaPrecios) {
                // Aquí puedes utilizar el resultado de la suma de precios como desees
                String sumaFormateada = String.format(Locale.getDefault(), "$%.2f", sumaPrecios);
                txvTotal.setText(sumaFormateada);
            }
        });

        obtenerSumaCategorias();

        return view;
    }

    private void obtenerSumaCategorias() {
        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();

        firebaseDatabaseHelper.leerMyObjects(new FirebaseDatabaseHelper.OnMyObjectsFetchedListener() {
            @Override
            public void onMyObjectsFetched(List<MyObject> myObjects) {
                if (myObjects != null) {
                    double regaloTotal = 0.0;
                    double interesTotal = 0.0;
                    double otrosTotal = 0.0;
                    double salarioTotal = 0.0;

                    for (MyObject myObject : myObjects) {
                        if (myObject.getCategoría().equals("Regalos")) {
                            regaloTotal += myObject.getMonto();
                        } else if (myObject.getCategoría().equals("Intereses")) {
                            interesTotal += myObject.getMonto();
                        } else if (myObject.getCategoría().equals("Otros")) {
                            otrosTotal += myObject.getMonto();
                        } else if (myObject.getCategoría().equals("Salario")) {
                            salarioTotal += myObject.getMonto();
                        }
                    }

                    txvRegalo.setText(String.format(Locale.getDefault(), "$%.2f", regaloTotal));
                    txvInteres.setText(String.format(Locale.getDefault(), "$%.2f", interesTotal));
                    txvOtros.setText(String.format(Locale.getDefault(), "$%.2f", otrosTotal));
                    txvSalario.setText(String.format(Locale.getDefault(), "$%.2f", salarioTotal));
                }
            }
        });
    }

}