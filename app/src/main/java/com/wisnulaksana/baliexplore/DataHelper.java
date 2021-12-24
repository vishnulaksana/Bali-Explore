package com.wisnulaksana.baliexplore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "BaliExplore";
    private static final String TABLE_NAME = "tbl_place";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAMATEMPAT = "nama_tempat";
    private static final String KEY_HARGA = "harga";
    private static final String KEY_TIPEWISATA = "tipewisata";
    private static final String KEY_FASILITAS = "fasilitas";



    public DataHelper(@Nullable Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createPlaceTable = "CREATE TABLE " + TABLE_NAME +
                " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAMATEMPAT + " TEXT, " +
                KEY_HARGA + " TEXT, " +
                KEY_TIPEWISATA + " TEXT, " +
                KEY_FASILITAS + " TEXT " + ")";
        sqLiteDatabase.execSQL(createPlaceTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;

        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert (Place place) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAMATEMPAT, place.getNamaTempat());
        values.put(KEY_HARGA, place.getHarga());
        values.put(KEY_TIPEWISATA, place.getTipewisata());
        values.put(KEY_FASILITAS, place.getFasilitas());

        db.insert(TABLE_NAME, null, values);
    }

    public List<Place> selectPlaceData() {
        ArrayList<Place> places = new ArrayList<Place>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_ID, KEY_NAMATEMPAT, KEY_HARGA, KEY_TIPEWISATA, KEY_FASILITAS};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {

            int id =cursor.getInt(0);
            String namaTempat = cursor.getString(1);
            String harga = cursor.getString(2);
            String tipewisata = cursor.getString(3);
            String fasilitas = cursor.getString(4);

            Place place = new Place();
            place.setId(id);
            place.setNamaTempat(namaTempat);
            place.setHarga(harga);
            place.setTipewisata(tipewisata);
            place.setFasilitas(fasilitas);

            places.add(place);
        }

        return places;
    }

    public void update(Place place){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAMATEMPAT, place.getNamaTempat());
        values.put(KEY_HARGA, place.getHarga());
        values.put(KEY_TIPEWISATA, place.getTipewisata());
        values.put(KEY_FASILITAS, place.getFasilitas());

        String whereClause = KEY_ID + " = '" + place.getId() +"'";

        db.update(TABLE_NAME, values,whereClause,null);
    }


    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = KEY_ID + " = '" + id + "'";

        db.delete(TABLE_NAME, whereClause, null);
    }
}
