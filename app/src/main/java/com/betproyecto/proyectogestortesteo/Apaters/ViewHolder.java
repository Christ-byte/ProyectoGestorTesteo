package com.betproyecto.proyectogestortesteo.Apaters;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.betproyecto.proyectogestortesteo.Items.MyObject;
import com.betproyecto.proyectogestortesteo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView nameTextView;
    private TextView categoryTextView;
    private TextView dateTextView;
    public TextView priceTextView;
    private ImageView iconImageView;
    public CardView cv;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageviewitem);
        nameTextView = itemView.findViewById(R.id.nameitem);
        categoryTextView = itemView.findViewById(R.id.cateritem);
        dateTextView = itemView.findViewById(R.id.fechaitem);
        priceTextView = itemView.findViewById(R.id.precio);
        iconImageView = itemView.findViewById(R.id.imagIcon);
        cv = itemView.findViewById(R.id.cv);
    }

    public void bindData(MyObject myObject) {
        //imageView.setImageResource(myObject.getImagen());
        nameTextView.setText(myObject.getTipo());
        categoryTextView.setText(myObject.getCategoría());
        dateTextView.setText(formatFecha(myObject.getFecha()));
        priceTextView.setText(String.valueOf(myObject.getMonto()));

        double monto = myObject.getMonto();
        if (monto >= 0) {
            priceTextView.setTextColor(Color.GREEN);
            iconImageView.setImageResource(R.drawable.arrow_upward);
        } else {
            priceTextView.setTextColor(Color.RED);
           iconImageView.setImageResource(R.drawable.arrow_downward);
        }
        String imageName = myObject.getImagen();
        int imageResource = itemView.getResources().getIdentifier(imageName, "drawable", itemView.getContext().getPackageName());

        // Establecer la imagen en el ImageView
        imageView.setImageResource(imageResource);
    }

    private String formatFecha(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.getDefault());
        return dateFormat.format(date);
    }
}
