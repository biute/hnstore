package com.example.hoanhimanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hoanhimanager.R;
import com.example.hoanhimanager.activity.EditMember;
import com.example.hoanhimanager.activity.MembersActivity;
import com.example.hoanhimanager.model.NhanVien;

import java.util.List;

public class MembersAdapter extends BaseAdapter {

    private MembersActivity context;
    private int layout;
    private List<NhanVien> nhanVienList;

    public MembersAdapter() {
    }

    public MembersAdapter(MembersActivity context, int layout, List<NhanVien> nhanVienList) {
        this.context = context;
        this.layout = layout;
        this.nhanVienList = nhanVienList;
    }

    @Override
    public int getCount() {
        return nhanVienList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {

        TextView txtID, txtName, txtEmail,  txtPhone, txtAddress;
        ImageView imgEdit, imgDelete;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtID = convertView.findViewById(R.id.text_view_id_member_activity);
            holder.txtName = convertView.findViewById(R.id.text_view_name_member_activity);
            holder.txtEmail = convertView.findViewById(R.id.text_view_email_member_activity);
            holder.txtPhone = convertView.findViewById(R.id.text_view_phone_member_activity);
            holder.txtAddress = convertView.findViewById(R.id.text_view_address_member_activity);
            holder.imgEdit = convertView.findViewById(R.id.image_view_edit_member_activity);
            holder.imgDelete = convertView.findViewById(R.id.image_view_delete_member_activity);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final NhanVien nhanVien = nhanVienList.get(position);
        final int id = nhanVien.getId();
        final String name = nhanVien.getName().trim();
        final String email = nhanVien.getEmail().trim();
        final String phone = nhanVien.getPhone().trim();
        final String address = nhanVien.getAddress().trim();
        final String gender = nhanVien.getGender().trim();
        final String pass = nhanVien.getPassword().trim();
        holder.txtID.setText(id + "");
        holder.txtName.setText(name);
        holder.txtEmail.setText(email);
        holder.txtPhone.setText(phone);
        holder.txtAddress.setText(address);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Edit " + nhanVien.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, EditMember.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("name", name);
                bundle.putString("gender", gender);
                bundle.putString("email", email);
                bundle.putString("phone", phone);
                bundle.putString("pass", pass);
                bundle.putString("address", address);
                intent.putExtra("RESULT_UPDATE", bundle);
                context.ChangeIntent(intent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DeleteMember(id);
            }
        });
        return convertView;
    }

}
