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

public class EditProductActivity extends AppCompatActivity {
    Bundle bundle;
    Database database;
    EditText eName, ePrice, eMota;
    ImageView imgProcduct;
    Button btnUpdate, btnCancel;
    int id, REQUEST_CODE_FOLDER = 1;
    String name, mota;
    byte[] anh;
    double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        getSupportActionBar().setTitle("Update product");
        GanID();
        database = new Database(this,"hoanhimanager.sql",null,1);

        bundle = getIntent().getBundleExtra("RESULT_UPDATE_PRODUCT");
        id = bundle.getInt("id");
        name = bundle.getString("name").trim();
        price = bundle.getDouble("price");
        mota = bundle.getString("mota").trim();
        anh = bundle.getByteArray("image");
        ViewItemPrduct(name.trim(), price, mota.trim(), anh);

        setOnClickButton();

    }

    private void setOnClickButton(){
        imgProcduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eName.getText().toString().trim();
                double price = Double.parseDouble(ePrice.getText().toString().trim());
                String mota = eMota.getText().toString().trim();
                byte[] img = UpdateInformationActivity.ImageToArray((BitmapDrawable) imgProcduct.getDrawable());
                if(name.equals("") || mota.equals("")){
                    Toast.makeText(EditProductActivity.this, "Bạn chưa nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }else{
//                    String sql = "UPDATE SanPham SET name = '" + name + "', price = '" + price + "', mota = '" + mota + "', image = '" + img + "' WHERE id = '" + id + "'";
//                    database.QueryData(sql);
                    try{
                        database.UpdateProduct(id, name, price, mota, img);
                    }catch (Exception e){e.printStackTrace();}
                    Toast.makeText(EditProductActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditProductActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProductActivity.this, HomeActivity.class));
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
                imgProcduct.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ViewItemPrduct(String name, Double price, String mota, byte[] anh){

        eName.setText(name);
        ePrice.setText(price.toString());
        eMota.setText(mota);
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
        imgProcduct.setImageBitmap(bitmap);

    }

    private void GanID(){
        btnCancel = findViewById(R.id.btn_cancel_update_product);
        btnUpdate = findViewById(R.id.btn_update_product);
        eName = findViewById(R.id.edit_name_update_product);
        ePrice = findViewById(R.id.edit_price_update_product);
        eMota = findViewById(R.id.edit_mo_ta_update_product);
        imgProcduct = findViewById(R.id.img_view_update_product);
    }
}
