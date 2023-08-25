package com.betproyecto.proyectogestortesteo.Apaters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.betproyecto.proyectogestortesteo.Items.MyObject;
import com.betproyecto.proyectogestortesteo.R;

import java.util.List;

public class MyAdapterMin extends RecyclerView.Adapter<ViewHolder> {
    private List<MyObject> myObjects;
    private Context context;

    public MyAdapterMin(List<MyObject> myObjects, Context context) {
        this.myObjects = myObjects;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyObject myObject = myObjects.get(position);
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(myObject);

    }

    @Override
    public int getItemCount() {
        return myObjects.size();
    }
}
