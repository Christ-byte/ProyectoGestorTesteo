package com.betproyecto.proyectogestortesteo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betproyecto.proyectogestortesteo.Apaters.MyAdapterMin;
import com.betproyecto.proyectogestortesteo.Data.FirebaseDatabaseHelper;
import com.betproyecto.proyectogestortesteo.Items.MyObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class FragmentPocket extends Fragment {

    private RecyclerView recyclerView;
    private List<MyObject> myList;
    private MyAdapterMin adapter;
    private FirebaseDatabaseHelper firebaseDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pocket, container, false);

        recyclerView = view.findViewById(R.id.SecundaryRecyclerView);
        myList = new ArrayList<>();

        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.leerMyObjects(new FirebaseDatabaseHelper.OnMyObjectsFetchedListener() {
            @Override
            public void onMyObjectsFetched(List<MyObject> myObjects) {
                if (myObjects != null) {
                    myList.clear();
                    Collections.reverse(myObjects);
                    myList.addAll(myObjects);

                    // Inicializar el adaptador
                    adapter = new MyAdapterMin(myList,getContext());

                    // Asignar el adaptador al RecyclerView
                    recyclerView.setAdapter(adapter);

                    // Establecer el LayoutManager
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    adapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

}