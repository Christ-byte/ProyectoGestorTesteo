package com.betproyecto.proyectogestortesteo.Data;

import com.betproyecto.proyectogestortesteo.Items.MyObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirebaseDatabaseHelper {
    private DatabaseReference myObjectsRef;

    public FirebaseDatabaseHelper() {
        // Obtén una instancia de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Cambia la referencia de la base de datos para el usuario actual
        myObjectsRef = database.getReference("usuarios").child(userId).child("my_objects");

    }

    public void leerMyObjects(final OnMyObjectsFetchedListener listener) {
        myObjectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<MyObject> myObjects = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    MyObject myObject = childSnapshot.getValue(MyObject.class);
                    myObjects.add(myObject);
                }
                listener.onMyObjectsFetched(myObjects);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onMyObjectsFetched(null);
            }
        });

    }

    public interface OnMyObjectsFetchedListener {
        void onMyObjectsFetched(List<MyObject> myObjects);
    }

    public void sumarPrecios(final OnSumaPreciosListener listener) {
        myObjectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double sumaPrecios = 0;

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    MyObject myObject = childSnapshot.getValue(MyObject.class);
                    sumaPrecios += myObject.getMonto();
                }

                listener.onSumaPrecios(sumaPrecios);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onSumaPrecios(0);
            }
        });
    }

    public interface OnSumaPreciosListener {
        void onSumaPrecios(double sumaPrecios);
    }

    public void obtenerIngresosPorDescripcion(final OnIngresosPorDescripcionListener listener) {
        myObjectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Double> ingresosPorDescripcion = new HashMap<>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    MyObject myObject = childSnapshot.getValue(MyObject.class);
                    if (myObject.getMonto() > 0) {
                        String descripcion = myObject.getDescripción();
                        double monto = myObject.getMonto();

                        // Si ya existe una entrada para la descripción, sumar el monto
                        if (ingresosPorDescripcion.containsKey(descripcion)) {
                            double montoActual = ingresosPorDescripcion.get(descripcion);
                            ingresosPorDescripcion.put(descripcion, montoActual + monto);
                        } else {
                            ingresosPorDescripcion.put(descripcion, monto);
                        }
                    }
                }

                listener.onIngresosPorDescripcionObtenidos(ingresosPorDescripcion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onIngresosPorDescripcionObtenidos(null);
            }
        });
    }

    public interface OnIngresosPorDescripcionListener {
        void onIngresosPorDescripcionObtenidos(HashMap<String, Double> ingresosPorDescripcion2);
    }

    public void obtenerIngresosNegativosPorDescripcion(final OnIngresosPorDescripcionListener listener) {
        myObjectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Double> ingresosPorDescripcionNegativos = new HashMap<>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    MyObject myObject = childSnapshot.getValue(MyObject.class);
                    if (myObject.getMonto() < 0) { // Solo agregar montos negativos
                        String descripcion = myObject.getDescripción();
                        double monto = myObject.getMonto();


                        if (ingresosPorDescripcionNegativos.containsKey(descripcion)) {
                            double montoActual = ingresosPorDescripcionNegativos.get(descripcion);
                            ingresosPorDescripcionNegativos.put(descripcion, montoActual + monto);
                        } else {
                            ingresosPorDescripcionNegativos.put(descripcion, monto);
                        }
                    }
                }

                listener.onIngresosPorDescripcionObtenidos(ingresosPorDescripcionNegativos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onIngresosPorDescripcionObtenidos(null);
            }
        });
    }







}