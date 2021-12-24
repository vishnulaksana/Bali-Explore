package com.wisnulaksana.baliexplore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AddActivity extends AppCompatActivity {

    private EditText txtNamaTempat;
    private String namaTempat;
    private SeekBar seekBar;
    private TextView lbl_harga;
    private String nilaiHarga;
    private String harga;
    private Button btnDaftar;
    private RadioGroup radioGroup;
    private String tipewisata;
    private CheckBox mHotel, mRestaurant, mToilet;
    private RadioButton rbdomestik;
    private RadioButton rbinternasional;
    private String fasilitas;

    private DataHelper db;
    private Place place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        txtNamaTempat = findViewById(R.id.input_namaTempat);
        seekBar = findViewById(R.id.seekbar);
        lbl_harga = findViewById(R.id.lbl_harga);
        btnDaftar = findViewById(R.id.daftar);
        radioGroup = findViewById(R.id.radioGroupTipewisata);
        rbdomestik = findViewById(R.id.domestik);
        rbinternasional = findViewById(R.id.internasional);
        mHotel = findViewById(R.id.hotel);
        mRestaurant = findViewById(R.id.restaurant);
        mToilet = findViewById(R.id.toilet);


        db = new DataHelper(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i = i / 1000;
                i = i * 1000;
                nilaiHarga = String.valueOf(i);
                lbl_harga.setText("Harga : Rp. " + nilaiHarga);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                namaTempat = txtNamaTempat.getText().toString();
                harga = nilaiHarga;
                tipewisata = getTipewisataSelected();
                fasilitas = getFasilitasSelected();


                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(AddActivity.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("Daftarkan");
                builder.setMessage(
                        "Apakah anda sudah yakin dengan data anda ?\n\n" +
                                "Nama Tempat : \n" + namaTempat + "\n\n" +
                                "Harga : Rp. \n" + harga + "\n\n" +
                                "Tipe Wisata :\n" + tipewisata + "\n\n" +
                                "Fasilitas Yang tersedia :\n" +fasilitas + ""
                );

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Data anda berhasil diupload !", Toast.LENGTH_SHORT).show();

                        Intent layout2 = new Intent(AddActivity.this, MainActivity.class);

                        place = new Place();
                        place.setNamaTempat(namaTempat);
                        place.setHarga(harga);
                        place.setTipewisata(tipewisata);
                        place.setFasilitas(fasilitas);

                        db.insert(place);

                        startActivity(layout2);
                        finish();

                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });


    }
    private String getFasilitasSelected(){
        String fasilitas = "";

        if (mHotel.isChecked()) {
            fasilitas += mHotel.getText().toString() + "\n";
        }
        if (mRestaurant.isChecked()) {
            fasilitas += mRestaurant.getText().toString() + "\n";
        }
        if (mToilet.isChecked()) {
            fasilitas += mToilet.getText().toString() + "\n";
        }

        return fasilitas;

    }

    private String getTipewisataSelected(){
        String tipewisata = "";

        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == rbdomestik.getId()){
            tipewisata = "Domestik";
        }
        else if (selectedId == rbinternasional.getId()){
            tipewisata = "Internasional";
        }

        return tipewisata;
    }

}
