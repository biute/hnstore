package com.example.hoanhimanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hoanhimanager.R;
import com.example.hoanhimanager.database.Database;

public class AddMemberActivity extends AppCompatActivity {

    Database database;
    Button btnAdd, btnCancel;
    EditText eName, eEmail, ePhone, ePassword, eAddress;
    RadioButton rMale, rFemale;
    String vName, vEmail, vPhone, vGender, vPassword, vAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        getSupportActionBar().setTitle("Add member");

        database = new Database(this,"hoanhimanager.sql",null,1);
        GanID();
        SetOnClickButton();


    }

    private void SetOnClickButton() {

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMemberActivity.this, MembersActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            vName = eName.getText().toString().trim();
            vEmail = eEmail.getText().toString().trim();
            vPhone = ePhone.getText().toString().trim();
            vPassword = ePassword.getText().toString().trim();
            vAddress = eAddress.getText().toString().trim();
            if(rMale.isChecked() == true && rFemale.isChecked() == false){
                vGender = "Nam";
            }else{
                vGender = "Nữ";
            }
            boolean bool = CheckValues(vName, vGender, vEmail, vPhone, vPassword, vAddress);
            if(bool == true){
                database.InsertNhanVien(vName, vGender, vEmail, vPhone, vPassword, vAddress);
                startActivity(new Intent(AddMemberActivity.this, MembersActivity.class));
                finish();
            }else{
                Toast.makeText(AddMemberActivity.this, "Bạn chưa nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
            }

            }
        });

    }

    private boolean CheckValues(String name, String gender, String email, String phone, String password, String address){

        if(name.equals("") || gender.equals("") || email.equals("") ||
                phone.equals("") || password.equals("") || address.equals("")){
            return false;
        }else{
            return true;
        }

    }

    private void GanID() {
        btnAdd = findViewById(R.id.btn_add_member_activity);
        btnCancel = findViewById(R.id.btn_cancel_add_member_activity);
        rMale = findViewById(R.id.radio_btn_nam_add_member_activity);
        rFemale = findViewById(R.id.radio_btn_nu_add_member_activity);
        eName = findViewById(R.id.edit_text_name_add_member_activity);
        eEmail = findViewById(R.id.edit_text_email_add_member_activity);
        ePhone = findViewById(R.id.edit_text_phone_add_member_activity);
        ePassword = findViewById(R.id.edit_text_password_add_member_activity);
        eAddress = findViewById(R.id.edit_text_address_add_member_activity);

    }
}
