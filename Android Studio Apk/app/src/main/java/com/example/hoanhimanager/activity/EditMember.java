package com.example.hoanhimanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.hoanhimanager.R;
import com.example.hoanhimanager.database.Database;


public class EditMember extends AppCompatActivity {

    Database database;
    TextView tID;
    EditText eName, eEmail, ePhone, eAddress;
    RadioButton rMale, rFemale;
    Button bUpdate, bCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);
        getSupportActionBar().setTitle("Update member");

        GanID();
        GetDataBundle();
        database = new Database(this,"hoanhimanager.sql",null,1);

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateMember();
                Intent intent = new Intent(EditMember.this, MembersActivity.class);
                startActivity(intent);
                finish();

            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMember.this, MembersActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }


    public void UpdateMember(){
        String gender;
        int id = Integer.valueOf(tID.getText().toString().trim());
        String name = eName.getText().toString().trim();
        String email = eEmail.getText().toString().trim();
        String phone = ePhone.getText().toString().trim();
        String address = eAddress.getText().toString().trim();
        if (rMale.isChecked() == true && rFemale.isChecked() == false){
            gender = "Nam";
        }else{
            gender = "Nữ";
        }

        String sql = "UPDATE NhanVien SET name = '" + name + "', gender = '" + gender + "', " +
                "email = '" + email + "', phone = '" + phone + "', address = '" + address + "' WHERE id = '" + id + "'";
        database.QueryData(sql);

    }

    public void GetDataBundle(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("RESULT_UPDATE");
        int id = bundle.getInt("id");
        String name = bundle.getString("name");
        String gender = bundle.getString("gender");
        String email = bundle.getString("email");
        String phone = bundle.getString("phone");
        String pass = bundle.getString("pass");
        String address = bundle.getString("address");

//      Gán giá trị cho các text_view và edit_text
        tID.setText(id + "");
        eName.setText(name);
        eEmail.setText(email);
        ePhone.setText(phone);
        eAddress.setText(address);

        if(gender.equals("Nam")){
            rMale.setChecked(true);
        }else{
            rFemale.setChecked(true);
        }
    }

    public void GanID(){
        tID = findViewById(R.id.text_view_id_edit_member_activity);
        eName = findViewById(R.id.edit_text_name_edit_member_activity);
        rMale = findViewById(R.id.radio_btn_nam_edit_member_activity);
        rFemale = findViewById(R.id.radio_btn_nu_edit_member_activity);
        eEmail = findViewById(R.id.edit_text_email_edit_member_activity);
        ePhone = findViewById(R.id.edit_text_phone_edit_member_activity);
        eAddress = findViewById(R.id.edit_text_address_edit_member_activity);
        bUpdate = findViewById(R.id.btn_update_edit_member_activity);
        bCancel = findViewById(R.id.btn_cancel_edit_member_activity);
    }
}
