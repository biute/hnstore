package com.example.hoanhimanager.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoanhimanager.R;
import com.example.hoanhimanager.activity.EditProductActivity;
import com.example.hoanhimanager.activity.HomeActivity;
import com.example.hoanhimanager.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private HomeActivity context;
    private int layout;
    private List<Product> listProduct;

    public ProductAdapter(HomeActivity context, int layout, List<Product> listProduct) {
        this.context = context;
        this.layout = layout;
        this.listProduct = listProduct;
    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imgProduct = convertView.findViewById(R.id.img_item_product);
            holder.txtName = convertView.findViewById(R.id.text_view_name_product);
            holder.txtPrice = convertView.findViewById(R.id.text_view_price_product);
            holder.txtMota = convertView.findViewById(R.id.text_view_mo_ta_product);
            holder.imgEdit = convertView.findViewById(R.id.img_view_btn_edit_product);
            holder.imgDelete = convertView.findViewById(R.id.img_view_btn_delete_product);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Product product = listProduct.get(position);
        final int id = product.getIdP();
        final String name = product.getNameP().trim();
        final double price = Double.valueOf(product.getPrice());
        final String mota = product.getMota().trim();
        final byte[] img = product.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        holder.imgProduct.setImageBitmap(bitmap);
        holder.txtName.setText(name);
        holder.txtPrice.setText(price + "$");
        holder.txtMota.setText(mota);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Edit " + product.getNameP(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, EditProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("name", name);
                bundle.putDouble("price", price);
                bundle.putString("mota", mota);
                bundle.putByteArray("image", img);
                intent.putExtra("RESULT_UPDATE_PRODUCT", bundle);
                context.ChangeIntent(intent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DeleteProduct(id);
            }
        });

        return convertView;
    }

    private class ViewHolder {

        TextView txtName, txtPrice,  txtMota;
        ImageView imgEdit, imgDelete, imgProduct;

    }
}
