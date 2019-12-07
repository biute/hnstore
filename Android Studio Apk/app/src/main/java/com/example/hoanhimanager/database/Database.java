package com.example.hoanhimanager.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;


public class Database extends SQLiteOpenHelper {


    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    Truy vấn không trả về kết quả: create, insert, update, delete,...
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

//    Truy vấn có trả về kết quả: select
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        return cursor;
    }

    public void InsertNhanVien(String name, String gender, String email, String phone, String password, String address){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO NhanVien VALUES(null, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, gender);
        statement.bindString(3, email);
        statement.bindString(4, phone);
        statement.bindString(5, password);
        statement.bindString(6, address);
        statement.executeInsert();

    }

    public void InsertProduct(String name, double price, String mota, byte[] hinhanh){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO SanPham VALUES(null, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindDouble(2, price);
        statement.bindString(3, mota);
        statement.bindBlob(4, hinhanh);
        statement.executeInsert();
//        db.close();

    }
    public void UpdateProduct(int id, String name, double price, String mota, byte[] hinhanh){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE SanPham SET name = ?, price = ?, mota = ?, image = ? WHERE id = '" + id + "'";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindDouble(2, price);
        statement.bindString(3, mota);
        statement.bindBlob(4, hinhanh);
        statement.executeUpdateDelete();


    }

    public void UpdateImage(int idnv, byte[] hinhanh){
        try{
            SQLiteDatabase db = getWritableDatabase();

            String sql = "UPDATE HinhAnh SET image = ? WHERE idnv = '" + idnv + "'";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();

            statement.bindBlob(1, hinhanh);
            statement.executeUpdateDelete();
            System.out.println(idnv);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void InsertImage(int id, byte[] hinhanh){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO HinhAnh VALUES(null, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1, id);
        statement.bindBlob(2, hinhanh);
        statement.executeInsert();
    }

}
