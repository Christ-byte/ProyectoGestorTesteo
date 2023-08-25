package com.betproyecto.proyectogestortesteo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.betproyecto.proyectogestortesteo.Apaters.ApadterDecimal;
import com.betproyecto.proyectogestortesteo.Items.MyObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.betproyecto.proyectogestortesteo.login.LoginActivity;

import java.util.Date;

public class FragmentGastos extends Fragment {

    private ImageView imgOption1, imgOption2, imgOption3, imgOption4,imgOption5,imgOption6,imgOption7;
    private EditText  edtPrecioGasto;
    private TextView txvDescripcion;
    private Button  btnEnviar;
    private DatabaseReference userMyObjectsRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gastos, container, false);

        // Llamar a la clase para filtrar
        edtPrecioGasto = view.findViewById(R.id.PrecioGasto);
        edtPrecioGasto.setFilters(new InputFilter[]{new ApadterDecimal()});

        imgOption1 = view.findViewById(R.id.image_1);
        imgOption2 = view.findViewById(R.id.image_2);
        imgOption3 = view.findViewById(R.id.image_3);
        imgOption4 = view.findViewById(R.id.image_4);
        imgOption5 = view.findViewById(R.id.image_5);
        imgOption6 = view.findViewById(R.id.image_6);
        imgOption7 = view.findViewById(R.id.image_7);
        txvDescripcion = view.findViewById(R.id.txvDescripcion);
        btnEnviar = view.findViewById(R.id.elevatedButton);

        imgOption1.setSelected(true);
        txvDescripcion.setText("Café");

        edtPrecioGasto.requestFocus();

        imgOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(imgOption1);
            }
        });

        imgOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(imgOption2);
            }
        });

        imgOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(imgOption3);
            }
        });

        imgOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(imgOption4);
            }
        });
        imgOption5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(imgOption5);
            }
        });
        imgOption6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(imgOption6);
            }
        });
        imgOption7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(imgOption7);
            }
        });


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String precioGasto = edtPrecioGasto.getText().toString().trim();
                String descripcion = txvDescripcion.getText().toString();

                if (precioGasto.isEmpty()) {
                    Toast.makeText(getActivity(), "Ingresa un precio ", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date fechaActual = new Date();

                // Obtén el ID del usuario actual
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Obtén la referencia para el nodo "my_objects" del usuario actual
                userMyObjectsRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child(userId).child("my_objects");

                // Genera un ID único para el nuevo objeto en el nodo "my_objects" del usuario
                String objectId = userMyObjectsRef.push().getKey();

                // Crea el objeto MyObject con los datos ingresados por el usuario
                MyObject myObject = new MyObject();
                myObject.setId(1);
                myObject.setTipo("Gastos");
                myObject.setMonto(-1 * Double.parseDouble(precioGasto));
                myObject.setFecha(fechaActual);
                myObject.setDescripción(descripcion);
                myObject.setImagen(obtenerImagenSeleccionada());
                myObject.setCategoría(descripcion);
                myObject.setEstado("estado");

                // Guarda el nuevo objeto en la base de datos del usuario actual
                userMyObjectsRef.child(objectId).setValue(myObject);

                Toast.makeText(getActivity(), "Añadido", Toast.LENGTH_SHORT).show();
                edtPrecioGasto.setText("");
            }
        });

        return view;
    }

    private void selectImage(ImageView imageView) {

        imgOption1.setSelected(imageView == imgOption1);
        imgOption2.setSelected(imageView == imgOption2);
        imgOption3.setSelected(imageView == imgOption3);
        imgOption4.setSelected(imageView == imgOption4);
        imgOption5.setSelected(imageView == imgOption5);
        imgOption6.setSelected(imageView == imgOption6);
        imgOption7.setSelected(imageView == imgOption7);

        if (imageView == imgOption1) {
            txvDescripcion.setText("Cafe");
        } else if (imageView == imgOption2) {
            txvDescripcion.setText("Transporte");
        } else if (imageView == imgOption3) {
            txvDescripcion.setText("Educacion");
        } else if (imageView == imgOption4) {
            txvDescripcion.setText("Familia");
        } else if (imageView == imgOption5) {
            txvDescripcion.setText("Regalo");
        } else if (imageView == imgOption6) {
            txvDescripcion.setText("Rutina");
        } else if (imageView == imgOption7) {
            txvDescripcion.setText("Alimentacion");
        }
    }
    private String obtenerImagenSeleccionada() {
        if (imgOption1.isSelected()) {
            return "coffee_icon";
        } else if (imgOption2.isSelected()) {
            return "bus_icon";
        } else if (imgOption3.isSelected()) {
            return "school_icon";
        } else if (imgOption4.isSelected()) {
            return "family_icon";
        } else if (imgOption5.isSelected()) {
            return "redeem_icon";
        } else if (imgOption6.isSelected()) {
            return "fitness_icon";
        } else if (imgOption7.isSelected()) {
            return "fastfood_icon";
        } else {
            return "";
        }
    }

}




