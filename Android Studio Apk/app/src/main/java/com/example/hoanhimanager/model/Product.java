package com.example.hoanhimanager.model;

public class Product {
    private int idP;
    private String nameP, mota;
    private double price;
    private byte[] hinhAnh;

    public Product() {
    }

    public Product(int id, String name, String mota, double price) {
        this.idP = id;
        this.nameP = name;
        this.mota = mota;
        this.price = price;
    }

    public Product(String name, String mota, double price, byte[] hinhAnh) {
        this.nameP = name;
        this.mota = mota;
        this.price = price;
        this.hinhAnh = hinhAnh;
    }

    public Product(int id, String name, String mota, double price, byte[] hinhAnh) {
        this.idP = id;
        this.nameP = name;
        this.mota = mota;
        this.price = price;
        this.hinhAnh = hinhAnh;
    }

    public Product(String name, String mota, double price) {
        this.nameP = name;
        this.mota = mota;
        this.price = price;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int id) {
        this.idP = id;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String name) {
        this.nameP = name;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
