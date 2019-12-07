package com.example.hoanhimanager.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.hoanhimanager.R;
import com.example.hoanhimanager.database.Database;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class UpdateInformationActivity extends AppCompatActivity {

    Button btnUpdate, btnCancel;
    EditText eName, eEmail, ePhone, eAddress;
    ImageView imgAvatar;
    ImageButton imgCamera, imgFolder;
    RadioButton rMale, rFemale;
    Bundle bundle;
    int REQUEST_CODE_CAMERA = 1;
    int REQUEST_CODE_FOLDER = 9;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);
        getSupportActionBar().setTitle("Update information");
        database = new Database(this,"hoanhimanager.sql",null,1);
        GetID();

        bundle = getIntent().getBundleExtra("result_value");
        GanValues(bundle);
        setOnClickButton();
    }

    public static byte[] ImageToArray(BitmapDrawable bitmapDrawable){

        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
        return byteArray.toByteArray();

    }


    private void setOnClickButton(){
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        imgFolder.setOnClickListener(new View.OnClickListener() {
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
                int id = bundle.getInt("id");
                String name = eName.getText().toString().trim();
                String email = eEmail.getText().toString().trim();
                String phone = ePhone.getText().toString().trim();
                String address = eAddress.getText().toString().trim();
                String gender;
                if (rMale.isChecked() == true && rFemale.isChecked() == false) {
                    gender = "Nam";
                } else {
                    gender = "Nữ";
                }
                String sql = "UPDATE NhanVien SET name = '" + name + "', gender = '" + gender + "', " +
                        "email = '" + email + "', phone = '" + phone + "', address = '" + address + "' WHERE id = '" + id + "'";

                database.QueryData(sql);



                try{
                    byte[] img = ImageToArray((BitmapDrawable) imgAvatar.getDrawable());
                    Cursor cursor = database.GetData("SELECT * FROM HinhAnh WHERE idnv = '" + id + "'");
                    if (cursor.moveToFirst()) {
                        database.UpdateImage(id, img);
                        startActivity(new Intent(UpdateInformationActivity.this, InformationActivity.class));
                        finish();


                    } else {
                        database.InsertImage(id, img);
                        startActivity(new Intent(UpdateInformationActivity.this, InformationActivity.class));
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }});
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateInformationActivity.this, InformationActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);
        }

        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            try {
                Uri uri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void GanValues(Bundle bundle){
        try{
            if(bundle.getByteArray("image") != null){
                byte[] img = bundle.getByteArray("image");
                Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                imgAvatar.setImageBitmap(bitmap);
            }
            if(bundle.getString("gender").equals("Nam")){
                rMale.setChecked(true);
            }else{
                rFemale.setChecked(true);
            }
            eName.setText(bundle.getString("name"));
            eEmail.setText(bundle.getString("email"));
            ePhone.setText(bundle.getString("phone"));
            eAddress.setText(bundle.getString("address"));
        }catch (Exception e){e.printStackTrace();}
    }

    private void GetID(){
        imgAvatar= findViewById(R.id.img_view_picture_update_information_activity);
        eName = findViewById(R.id.edit_text_name_update_information_activity);
        eEmail = findViewById(R.id.edit_text_email_update_information_activity);
        ePhone = findViewById(R.id.edit_text_phone_update_information_activity);
        eAddress = findViewById(R.id.edit_text_address_update_information_activity);
        btnUpdate = findViewById(R.id.btn_update_information_activity_update_information);
        btnCancel = findViewById(R.id.btn_cancel_update_activity_update_information);
        rMale = findViewById(R.id.radio_btn_nam_update_information_activity);
        rFemale = findViewById(R.id.radio_btn_nu_update_information_activity);
        imgCamera = findViewById(R.id.img_btn_camera_update_information_activity);
        imgFolder = findViewById(R.id.img_btn_folder_update_information_activity);
    }
}
