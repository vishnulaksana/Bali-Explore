package com.wisnulaksana.baliexplore;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.contentcapture.DataShareWriteAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaceViewHolder extends RecyclerView.ViewHolder{
    TextView txtNamaTempat;
    TextView txtFasilitas;
    TextView txtTipewisata;
    private CardView listPlace;
    private Context context;
    private List<Place> placeList;
    private int id;
    private String namaTempat;
    private String harga;
    private String tipewisata;
    private String fasilitas;


    public PlaceViewHolder(@NonNull View itemView){
        super(itemView);
        context = itemView.getContext();

        txtNamaTempat = itemView.findViewById(R.id.txtNamaLengkapList);
        txtFasilitas = itemView.findViewById(R.id.txtFasilitasList);
        txtTipewisata = itemView.findViewById(R.id.txtTipewisataList);
        listPlace = itemView.findViewById(R.id.row_item);

        listPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {alertAction(context, getAdapterPosition());}
        });
    }

    private void alertAction(Context context,int position) {
        String[] option ={"Detail", "Edit", "Delete"};
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        DataHelper db =new DataHelper(context);
        placeList = db.selectPlaceData();

        id = placeList.get(position).getId();
        namaTempat = placeList.get(position).getNamaTempat();
        harga = placeList.get(position).getHarga();
        tipewisata = placeList.get(position).getTipewisata();
        fasilitas = placeList.get(position).getFasilitas();


        builder.setTitle("Pilih Opsi");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i){
                    case 0:
                        Intent viewActivity = new Intent(context, ViewActivity.class);
                        viewActivity.putExtra("id",id);
                        viewActivity.putExtra("namaTempat",namaTempat);
                        viewActivity.putExtra("harga",harga);
                        viewActivity.putExtra("tipewisata",tipewisata);
                        viewActivity.putExtra("fasilitas",fasilitas);

                        context.startActivity(viewActivity);
                        break;

                    case 1:
                        Intent  modifyActivity = new Intent(context,  ModifyActivity.class);
                        modifyActivity.putExtra("id",id);
                        modifyActivity.putExtra("namaTempat",namaTempat);
                        modifyActivity.putExtra("harga",harga);
                        modifyActivity.putExtra("tipewisata",tipewisata);
                        modifyActivity.putExtra("fasilitas",fasilitas);

                        context.startActivity( modifyActivity);
                        break;

                    case 2:
                        DataHelper db = new DataHelper(context);
                        db.delete(placeList.get(position).getId());

                        placeList = db.selectPlaceData();
                        MainActivity.setupRecyclerView(context, placeList, MainActivity.recyclerView);

                        Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT);
                        break;

                }

            }
        });
        builder.show();

    }
}
