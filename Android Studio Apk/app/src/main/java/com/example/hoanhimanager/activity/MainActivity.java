package com.example.hoanhimanager.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hoanhimanager.R;
import com.example.hoanhimanager.database.Database;
import com.example.hoanhimanager.model.NhanVien;


public class MainActivity extends AppCompatActivity {

//    https://sqliteonline.com/
//    Khai báo biến
    Database database;
    EditText eName, eEmail, ePhone, ePassword, eConfirm, eAddress;
    RadioButton rMale, rFemale;
    Button bLogin, bSignin, bCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


//        Khởi tạo class database

        database = new Database(this,"hoanhimanager.sql",null,1);
//        database.QueryData("drop table HinhAnh");

//        Thực hiện câu truy vấn dữ liệu
//        create table(tạo bảng)

//        database.QueryData("drop table SanPham");
//        database.QueryData("drop table NhanVien");
//        database.QueryData("drop table HinhAnh");


        String sql = "CREATE TABLE IF NOT EXISTS NhanVien(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100), " +
                "gender VARCHAR, email VARCHAR(500) UNIQUE, phone VARCHAR(100), password VARCHAR(250), address VARCHAR(500))";
        database.QueryData(sql);
        database.QueryData("CREATE TABLE IF NOT EXISTS HinhAnh(id INTEGER PRIMARY KEY AUTOINCREMENT, idnv INTEGER UNIQUE, image BLOB)");
        database.QueryData("CREATE TABLE IF NOT EXISTS SanPham(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(250), price DECIMAL(18,0), mota VARCHAR(500), image BLOB)");
        database.close();
//        Call function

        GanID();
        setButtonOnClick();



    }


    public void setButtonOnClick(){

//        Click on button sign in
        bSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhanVien nhanVien = GanDataNhanVien();
                if (nhanVien == null) {
                    Toast.makeText(MainActivity.this, "Dữ liệu bạn nhập không đúng", Toast.LENGTH_SHORT).show();

                } else {
                    String name = nhanVien.getName();
                    String gender = nhanVien.getGender();
                    String email = nhanVien.getEmail();
                    String phone = nhanVien.getPhone();
                    String password = nhanVien.getPassword();
                    String address = nhanVien.getAddress();
                    if (name.equals("") || gender.equals("") || email.equals("") || password.equals("") || phone.equals("") || address.equals("")) {
                        Toast.makeText(MainActivity.this, "Dữ liệu bạn nhập chưa đầy đủ", Toast.LENGTH_SHORT).show();
                    } else {
                        database.onOpen(database.getWritableDatabase());
                        database.InsertNhanVien(name, gender, email, phone, password, address);
                        String select = "SELECT * FROM NhanVien WHERE email = '" + email + "' and password = '" + password + "'";
                        Cursor cursor = database.GetData(select);
                        if(cursor.moveToFirst()){
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", String.valueOf(cursor.getInt(0)));
                            bundle.putString("name", cursor.getString(1));
                            bundle.putString("gender", cursor.getString(2));
                            bundle.putString("email", cursor.getString(3));
                            bundle.putString("phone", cursor.getString(4));
                            bundle.putString("pass", cursor.getString(5));
                            bundle.putString("address", cursor.getString(6));
                            intent.putExtra("RESULT", bundle);
                            startActivity(intent);
                            eName.setText("");
                            eEmail.setText("");
                            ePassword.setText("");
                            ePhone.setText("");
                            eAddress.setText("");
                            eConfirm.setText("");
                            rMale.setChecked(false);
                            rFemale.setChecked(false);
                            finish();
                        }
                        database.close();
                    }
                }
            }
        });

//        Click on button login

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        Click on button cancel

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eName.setText("");
                eEmail.setText("");
                ePassword.setText("");
                ePhone.setText("");
                eAddress.setText("");
                eConfirm.setText("");
                rMale.setChecked(false);
                rFemale.setChecked(false);
            }
        });

    }


    public NhanVien GanDataNhanVien(){

        String name, gender, email, phone, password, confirm, address;

        name = eName.getText().toString();
        if (rMale.isChecked() == true && rFemale.isChecked() == false){
            gender = "Nam";
        }else{
            gender = "Nữ";
        }
        email = eEmail.getText().toString();
        phone = ePhone.getText().toString();
        password = ePassword.getText().toString();
        confirm = eConfirm.getText().toString();
        address = eAddress.getText().toString();
        NhanVien nhanVien;
        if(password.equals(confirm)){
            nhanVien = new NhanVien(name, gender, email, phone, password, address);
            return nhanVien;
        }else{
            return null;
        }
    }


    public void GanID(){
        eName = findViewById(R.id.edit_text_name_main_activity);
        rMale = findViewById(R.id.radio_btn_nam_main_activity);
        rFemale = findViewById(R.id.radio_btn_nu_main_activity);
        eEmail = findViewById(R.id.edit_text_email_main_activity);
        ePhone = findViewById(R.id.edit_text_phone_main_activity);
        ePassword = findViewById(R.id.edit_text_password_main_activity);
        eConfirm = findViewById(R.id.edit_text_confirm_password_main_activity);
        eAddress = findViewById(R.id.edit_text_address_main_activity);
        bLogin = findViewById(R.id.btn_dang_nhap_main_activity);
        bSignin = findViewById(R.id.btn_dang_ki_main_activity);
        bCancel = findViewById(R.id.btn_nhap_lai_main_activity);
    }
}
