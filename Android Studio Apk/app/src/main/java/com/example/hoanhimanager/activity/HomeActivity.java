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
import com.example.hoanhimanager.adapter.ProductAdapter;
import com.example.hoanhimanager.database.Database;
import com.example.hoanhimanager.model.Product;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    String name;
    Bundle bundle;
    ListView listView;
    ArrayList<Product> arrProduct;
    ProductAdapter productAdapter;
    Database database;
    public static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("List product");

//        database.QueryData("drop table Product");
        database = new Database(this,"hoanhimanager.sql",null,1);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("RESULT");
        id = Integer.valueOf(bundle.getString("id"));
        name = bundle.getString("name");
        Toast.makeText(this, name + " login success!", Toast.LENGTH_SHORT).show();

        listView = findViewById(R.id.list_view_product);

        arrProduct = new ArrayList<>();
        arrProduct.clear();
        productAdapter = new ProductAdapter(this, R.layout.custom_item_list_product, arrProduct);
        ViewProducts();

    }

    public void DeleteProduct(int id){
        String sql = "DELETE FROM SanPham WHERE id = '" + id + "'";
        try{
            database.QueryData(sql);
        }catch (Exception e){e.printStackTrace();}
        arrProduct.clear();
        ViewProducts();
        Toast.makeText(this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();

    }

    public void ChangeIntent(Intent intent){
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_add_product_menu: {
                startActivity(new Intent(HomeActivity.this, AddProductActivity.class));
                break;}
            case R.id.item_members_menu: {
                Intent intent = new Intent(getApplicationContext(), MembersActivity.class);
                intent.putExtra("RESULT_MEMBER", bundle);
                startActivity(intent);
                break;
            }
            case R.id.item_information_menu: {
                Intent intent = new Intent(HomeActivity.this, InformationActivity.class);
                intent.putExtra("RESULT_INFORMATION", bundle);
                startActivity(intent);
                break;
            }
            case R.id.item_logout_menu: {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void ViewProducts(){
        try{
            database.onOpen(database.getReadableDatabase());
            Cursor cursor = database.GetData("SELECT * FROM SanPham");

            while(cursor.moveToNext()){
                int id =  cursor.getInt(0);
                String name =  cursor.getString(1).trim();
                double price = cursor.getDouble(2);
                String mota =  cursor.getString(3).trim();
                byte[] hinhanh =  cursor.getBlob(4);
                arrProduct.add(new Product(id, name, mota, price, hinhanh));
            }
            productAdapter.notifyDataSetChanged();


            if(arrProduct != null){
                listView.setAdapter(productAdapter);

            }else{
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
