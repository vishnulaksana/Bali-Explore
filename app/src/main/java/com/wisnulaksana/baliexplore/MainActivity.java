package com.wisnulaksana.baliexplore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    static RecyclerView recyclerView;
    private RecyclerviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Place> placeList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("About Bali Explore");
                builder.setMessage(
                        "Bali Explore merupakan aplikasi pencatatan tempat wisata dimana" +
                                " menginputkan tempat wisata itu sendiri ke dalam aplikasi yang" +
                                " disimpan ke dalam database\n\n" +
                                "Nama  : Nyoman Wisnu Laksana\n" +
                                "NIM     : 1905551128"
                );
                builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setupRecyclerView();

        try{
            Intent intent = getIntent();
            String status = intent.getExtras().getString("status");


            if (status.equals("add")) {
                Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            } else if (status.equals("edit")) {
                Toast.makeText(this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            if (placeList.isEmpty()) {
                Toast.makeText(this, "Klik fab untuk menambah Tempat", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Klik Tempat untuk opsi lain", Toast.LENGTH_SHORT).show();
            }
        }


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }


    static void setupRecyclerView(Context context, List<Place> placeList, RecyclerView recyclerView) {
        DataHelper db = new DataHelper(context);
        placeList = db.selectPlaceData();

        // Meng set adapter Recycler View nya
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(placeList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void setupRecyclerView() {
        DataHelper db = new DataHelper(this);
        placeList = db.selectPlaceData();

        adapter = new RecyclerviewAdapter(placeList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Selamat datang", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Halo", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Jangan lupa kembali lagi", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Sampai berjumpa lagi nanti", Toast.LENGTH_LONG).show();
    }
}