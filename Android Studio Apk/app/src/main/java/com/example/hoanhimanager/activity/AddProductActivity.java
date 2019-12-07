package com.example.hoanhimanager.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hoanhimanager.R;
import com.example.hoanhimanager.database.Database;


import java.io.InputStream;


public class AddProductActivity extends AppCompatActivity {
    EditText eName, ePrice, eMota;
    ImageView imgPicture;
    Button btnThem, btnHuy;
    int REQUEST_CODE_FOLDER = 9;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setTitle("Add product");
        database = new Database(this,"hoanhimanager.sql",null,1);
        GanID();
        setOnClick();

    }

    private void setOnClick(){

        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eName.getText().toString().trim();
                double price = Double.parseDouble(ePrice.getText().toString().trim());
                String mota = eMota.getText().toString().trim();
                byte[] img = UpdateInformationActivity.ImageToArray((BitmapDrawable) imgPicture.getDrawable());
                if(name.equals("") || mota.equals("")){
                    Toast.makeText(AddProductActivity.this, "Bạn chưa nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }else{
//                    String sql = "INSERT INTO Product VALUES('" + name + "','" + price + "','" + mota + "','" + img + "')";
//                    database.QueryData(sql);

                    try{
                        database.InsertProduct(name, price, mota, img);
                        Toast.makeText(AddProductActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddProductActivity.this, HomeActivity.class));
                        finish();
                    }catch (Exception e){e.printStackTrace();}


                }

            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProductActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            try {
                Uri uri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgPicture.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void GanID(){
        eName = findViewById(R.id.edit_name_product);
        ePrice = findViewById(R.id.edit_price_product);
        eMota = findViewById(R.id.edit_mo_ta_product);
        imgPicture = findViewById(R.id.img_view_product);
        btnThem = findViewById(R.id.btn_add_product);
        btnHuy = findViewById(R.id.btn_cancel_add_product);
    }
}
