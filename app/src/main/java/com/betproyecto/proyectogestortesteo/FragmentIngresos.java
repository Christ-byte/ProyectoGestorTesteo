package com.betproyecto.proyectogestortesteo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.betproyecto.proyectogestortesteo.Apaters.ApadterDecimal;
import com.betproyecto.proyectogestortesteo.Items.MyObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class FragmentIngresos extends Fragment {
    private ImageView imgOption1, imgOption2, imgOption3, imgOption4;
    private EditText edtPrecioGasto;
    private TextView txvDescripcion;
    private Button btnEnviar;
    private DatabaseReference userMyObjectsRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingresos, container, false);

        // Llamar a la clase para filtrar
        edtPrecioGasto = view.findViewById(R.id.PrecioGasto);
        edtPrecioGasto.setFilters(new InputFilter[] { new ApadterDecimal() });

        imgOption1 = view.findViewById(R.id.image_1);
        imgOption2 = view.findViewById(R.id.image_2);
        imgOption3 = view.findViewById(R.id.image_3);
        imgOption4 = view.findViewById(R.id.image_4);
        txvDescripcion = view.findViewById(R.id.txvDescripcion);
        btnEnviar = view.findViewById(R.id.elevatedButton);

        imgOption1.setSelected(true);
        txvDescripcion.setText("Salario");

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

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String precioGasto = edtPrecioGasto.getText().toString().trim();
                String descripcion = txvDescripcion.getText().toString();

                if (precioGasto.isEmpty()) {
                    Toast.makeText(getActivity(), "Ingresa un precio", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtener la fecha actual
                Date fechaActual = new Date();

                // Obtén el ID del usuario actual
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Obtén la referencia para el nodo "my_objects" del usuario actual
                userMyObjectsRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child(userId).child("my_objects");

                // Generar un ID único para el objeto MyObject en el nodo del usuario
                String objectId = userMyObjectsRef.push().getKey();

                // Crea el objeto MyObject con los datos ingresados
                MyObject myObject = new MyObject();
                myObject.setId(1);
                myObject.setTipo("Ingreso");
                myObject.setMonto(Double.parseDouble(precioGasto));
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

        if (imageView == imgOption1) {
            txvDescripcion.setText("Salario");
        } else if (imageView == imgOption2) {
            txvDescripcion.setText("Intereses");
        } else if (imageView == imgOption3) {
            txvDescripcion.setText("Otros");
        } else if (imageView == imgOption4) {
            txvDescripcion.setText("Regalos");
        }
    }
    private String obtenerImagenSeleccionada() {
        if (imgOption1.isSelected()) {
            return "work_icon";
        } else if (imgOption2.isSelected()) {
            return "account_icon";
        } else if (imgOption3.isSelected()) {
            return "help_icon";
        } else if (imgOption4.isSelected()) {
            return "redeem_icon";
        } else {
            return "";
        }
    }




}