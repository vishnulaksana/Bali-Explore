package com.wisnulaksana.baliexplore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;

import java.util.Calendar;

public class ModifyActivity extends AppCompatActivity {
    private EditText edtNamaTempat;
    private String namaTempat;
    private Slider slider;
    private SeekBar seekBar;
    private TextView lbl_harga;
    private String nilaiHarga;
    private String harga;
    private Button btnUbah;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String tipewisata;
    private RadioButton rbdomestik;
    private RadioButton rbinternasional;
    private CheckBox mHotel, mRestaurant, mToilet;
    private String mResult = "";
    private String fasilitas;
    private int id;

    private DataHelper db;
    private Place place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        edtNamaTempat = findViewById(R.id.input_namaTempat);
        seekBar = findViewById(R.id.seekbar);
        lbl_harga = findViewById(R.id.lbl_harga);
        btnUbah = findViewById(R.id.ubah);
        radioGroup = findViewById(R.id.radioGroupTipewisata);
        rbdomestik = findViewById(R.id.domestik);
        rbinternasional = findViewById(R.id.internasional);
        mHotel = findViewById(R.id.hotel);
        mRestaurant = findViewById(R.id.restaurant);
        mToilet = findViewById(R.id.toilet);

        db = new DataHelper(this);

        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");
        namaTempat = intent.getExtras().getString("namaTempat");
        harga = intent.getExtras().getString("harga");
        tipewisata = intent.getExtras().getString("tipewisata");
        fasilitas = intent.getExtras().getString("fasilitas");

        edtNamaTempat.setText(namaTempat);
        lbl_harga.setText("Harga : Rp. " + harga);
        setTipewisataSelected();
        setFasilitasSelected();
        seekBar.setProgress(Integer.parseInt(harga));

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

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaTempat = edtNamaTempat.getText().toString().trim();
                tipewisata = getTipewisataSelected();
                fasilitas = getFasilitasSelected();

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ModifyActivity.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("Daftarkan");
                builder.setMessage(
                        "Apakah anda sudah yakin dengan data anda ?\n\n" +
                                "Nama Tempat : \n" + namaTempat + "\n\n" +
                                "Harga : \n" + harga + "\n\n" +
                                "Tipewisata :\n" + tipewisata + "\n\n" +
                                "Fasilitas yang tersedia :\n" + fasilitas + ""
                );

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Data anda berhasil terdaftarkan !", Toast.LENGTH_SHORT).show();

                        Intent layout2 = new Intent(ModifyActivity.this, MainActivity.class);

                        place = new Place();
                        place.setId(id);
                        place.setNamaTempat(namaTempat);
                        place.setHarga(harga);
                        place.setTipewisata(tipewisata);
                        place.setFasilitas(fasilitas);

                        db.update(place);

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

    private void setFasilitasSelected() {
        if (fasilitas.contains("Hotel")){
            mHotel.setChecked(true);
        }
        if (fasilitas.contains("Fasilitas")){
            mRestaurant.setChecked(true);
        }
        if (fasilitas.contains("Toilet")){
            mToilet.setChecked(true);
        }
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

    private void setTipewisataSelected(){
        if (tipewisata.equals("Domestik")) {
            rbdomestik.setChecked(true);
        } else if (tipewisata.equals("Internasional")){
            rbinternasional.setChecked(true);
        }
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
