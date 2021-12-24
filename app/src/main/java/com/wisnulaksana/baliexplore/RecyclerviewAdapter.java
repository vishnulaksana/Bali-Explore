package com.wisnulaksana.baliexplore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
    List<Place> placeList;

    public RecyclerviewAdapter(List<Place> placeList) {
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        PlaceViewHolder placeViewHolder = new PlaceViewHolder(view);

        return placeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.txtNamaTempat.setText(placeList.get(position).getNamaTempat());
        holder.txtTipewisata.setText(placeList.get(position).getTipewisata());
        holder.txtFasilitas.setText(placeList.get(position).getFasilitas());
    }

    // Ini untuk menghitung jumlah datanya
    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
