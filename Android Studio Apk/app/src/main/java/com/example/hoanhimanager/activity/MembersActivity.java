package com.example.hoanhimanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hoanhimanager.R;
import com.example.hoanhimanager.adapter.MembersAdapter;
import com.example.hoanhimanager.database.Database;
import com.example.hoanhimanager.model.NhanVien;

import java.util.ArrayList;

public class MembersActivity extends AppCompatActivity {

    ListView listViewNhanVien;
    ArrayList<NhanVien> arrayNhanVien;
    MembersAdapter membersAdapter;
    static Database database;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        getSupportActionBar().setTitle("Members");

        listViewNhanVien = findViewById(R.id.list_item_members_activity);
        arrayNhanVien = new ArrayList<>();

        database = new Database(this,"hoanhimanager.sql",null,1);
        bundle = getIntent().getBundleExtra("RESULT_MEMBER");
        ViewMembers();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub_member, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        try{
            switch (item.getItemId()){
                case R.id.information_menu_item: {
                    Intent intent = new Intent(MembersActivity.this, InformationActivity.class);
                    intent.putExtra("RESULT_INFORMATION", bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.home_menu_item: {
                    try{
                        if(getApplicationContext() == MembersActivity.this){
                            Intent intent = new Intent(MembersActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        }else{
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            break;
                        }
                    }catch (Exception ex){ex.printStackTrace();}
                }
                case R.id.logout_menu_item: {
                    Intent intent = new Intent(MembersActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                case R.id.add_member_item: {
                    startActivity(new Intent(MembersActivity.this, AddMemberActivity.class));
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return super.onOptionsItemSelected(item);
    }
    public void ChangeIntent(Intent intent){
        startActivity(intent);
    }

    public void DeleteMember(int id){
        Bundle bundle = getIntent().getBundleExtra("RESULT_MEMBER");
        int idM = Integer.valueOf(bundle.getString("id"));
        String sql = "DELETE FROM NhanVien WHERE id = '" + id + "'";
        if(id != idM){
            database.QueryData(sql);
            arrayNhanVien.clear();
            ViewMembers();
        }else{
            Toast.makeText(this, "Không thể xóa " + bundle.getString("name"), Toast.LENGTH_SHORT).show();
        }


    }

    private void ViewMembers(){
        String sql = "SELECT * FROM NhanVien";
        Cursor cursor = database.GetData(sql);
        while(cursor.moveToNext()){
            int id =  cursor.getInt(0);
            String name =  cursor.getString(1);
            String gender = cursor.getString(2);
            String email =  cursor.getString(3);
            String phone =  cursor.getString(4);
            String pass = cursor.getString(5);
            String address =  cursor.getString(6);
            arrayNhanVien.add(new NhanVien(id, name, gender, email, phone, pass, address));
        }

        if(arrayNhanVien != null){
            membersAdapter = new MembersAdapter(MembersActivity.this, R.layout.custom_item_member, arrayNhanVien);
            listViewNhanVien.setAdapter(membersAdapter);
            membersAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
        }
    }

}
