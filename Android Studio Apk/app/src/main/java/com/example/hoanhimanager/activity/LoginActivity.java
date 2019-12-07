package com.example.hoanhimanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoanhimanager.R;
import com.example.hoanhimanager.database.Database;


public class LoginActivity extends AppCompatActivity {
    Database database;
    Button bLogin, bCancel;
    EditText eEmail, ePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        database = new Database(this,"hoanhimanager.sql",null,1);

        GanID();
        setOnClickButton();


    }


    public void setOnClickButton(){
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eEmail.getText().toString();
                String pass = ePassword.getText().toString();
                if(email.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }else{
                    String sql = "SELECT * FROM NhanVien WHERE email = '" + email + "' and password = '" + pass + "'";
                    database.onOpen(database.getWritableDatabase());
                    Cursor cursor = database.GetData(sql);
                    if(cursor.moveToFirst()){
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
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
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                    database.close();

                }
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    public void GanID(){
        eEmail = findViewById(R.id.edit_text_email_dialog_dang_nhap_activity);
        ePassword = findViewById(R.id.edit_text_password_dialog_dang_nhap_activity);
        bLogin = findViewById(R.id.btn_login_dang_nhap_activity);
        bCancel = findViewById(R.id.btn_cancel_dang_nhap_activity);
    }
}
