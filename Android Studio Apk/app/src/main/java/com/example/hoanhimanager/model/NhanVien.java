package com.example.hoanhimanager.model;

import android.widget.Toast;

public class NhanVien {

//    Khai báo biến

    private int id;
    private String name, gender, email, phone, password, address;

//    Khởi tạo contructor mặc định
//    khởi tạo contructor không tham số truyền vào

    public NhanVien(){}

//    Khởi tạo contructor có tham số truyền vào
//    Contructor không có id

    public NhanVien(String name, String gender, String email, String phone, String password, String address) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
    }

//    Contructor 2 đối số truyền vào

    public NhanVien(String email, String password) {
        this.email = email;
        this.password = password;
    }

//    Contructor 5 đối số

    public NhanVien(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }


//    Contructor có id

    public NhanVien(int id, String name, String gender, String email, String phone, String password, String address) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
    }

//    Get & Set data (lấy dữ liệu và nhập dữ liệu)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
