package com.example.hoanhimanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoanhimanager.R;
import com.example.hoanhimanager.database.Database;


public class InformationActivity extends AppCompatActivity {
    Button btnUpdate;
    TextView tvName, tvGender, tvEmail, tvPhone, tvAddress;
    ImageView imgAvatar;
    Bundle bundle;
    Database database;
    Bundle bundle_infor = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getSupportActionBar().setTitle("Information");
        database = new Database(this,"hoanhimanager.sql",null,1);
//        database.QueryData("CREATE TABLE IF NOT EXISTS HinhAnh(id INTEGER PRIMARY KEY, image BLOB)");
        imgAvatar = findViewById(R.id.img_view_picture_information_activity);
        tvName = findViewById(R.id.text_view_name_information_activity);
        tvGender = findViewById(R.id.text_view_gender_information_activity);
        tvEmail = findViewById(R.id.text_view_email_information_activity);
        tvPhone = findViewById(R.id.text_view_phone_information_activity);
        tvAddress = findViewById(R.id.text_view_address_information_activity);
        btnUpdate = findViewById(R.id.btn_update_information_activity_information);

        bundle = getIntent().getBundleExtra("RESULT_INFORMATION");
        final String em = bundle.getString("email");
        Cursor cs = database.GetData("SELECT * FROM NhanVien WHERE email = '"+ em +"'");
        String name, gender, email, phone, address;
        int idnv;

        if(cs.moveToFirst()){
            idnv = cs.getInt(0);
            name = cs.getString(1);
            gender = cs.getString(2);
            email = cs.getString(3);
            phone = cs.getString(4);
            address = cs.getString(6);
            tvName.setText(name);
            tvGender.setText(gender);
            tvEmail.setText(email);
            tvPhone.setText(phone);
            tvAddress.setText(address);
            try{

                Cursor cursor = database.GetData("SELECT * FROM HinhAnh WHERE idnv = '" + idnv + "'");
                Bitmap bitmap;
                if(cursor.moveToFirst()){
                    byte[] hinhanh = cursor.getBlob(2);
                    bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
                    imgAvatar.setImageBitmap(bitmap);


                }else{
                    System.out.println("khong co anh");
                }
            }catch (Exception e){
                System.out.println("Khong co anh");
                e.printStackTrace();

            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(InformationActivity.this, UpdateInformationActivity.class);

                    Cursor cs = database.GetData("SELECT * FROM NhanVien WHERE email = '"+ em +"'");
                    if(cs.moveToFirst()){
                    int idnv = cs.getInt(0);
                    String name = cs.getString(1);
                    String gender = cs.getString(2);
                    String email = cs.getString(3);
                    String phone = cs.getString(4);
                    String address = cs.getString(6);

                    Cursor c = database.GetData("SELECT * FROM HinhAnh WHERE idnv = '"+ idnv +"'");
                    byte[] hinhanh;
                    if(c.moveToFirst()){
                        hinhanh = c.getBlob(2);
                        bundle_infor.putInt("id", idnv);
                        bundle_infor.putString("name", name);
                        bundle_infor.putString("gender", gender);
                        bundle_infor.putString("email", email);
                        bundle_infor.putString("phone", phone);
                        bundle_infor.putString("address", address);
                        bundle_infor.putByteArray("image", hinhanh);
                        intent.putExtra("result_value", bundle_infor);
                        startActivity(intent);
                    }else{
                        bundle_infor.putInt("id", idnv);
                        bundle_infor.putString("name", name);
                        bundle_infor.putString("gender", gender);
                        bundle_infor.putString("email", email);
                        bundle_infor.putString("phone", phone);
                        bundle_infor.putString("address", address);
                        intent.putExtra("result_value", bundle_infor);
                        startActivity(intent);
                    }


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub_information, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        try{
            switch (item.getItemId()){
                case R.id.members_menu_item_i: {
                    startActivity(new Intent(InformationActivity.this, MembersActivity.class));
                    break;}
                case R.id.home_menu_item_i: {
                    try{
                        if(getApplicationContext() == InformationActivity.this){
                            Intent intent = new Intent(InformationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        }else{
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            break;
                        }

                    }catch (Exception e){e.printStackTrace();}
                }
                case R.id.logout_menu_item_i: {
                    Intent intent = new Intent(InformationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }catch (Exception ex){ex.printStackTrace();}

        return super.onOptionsItemSelected(item);
    }



}
