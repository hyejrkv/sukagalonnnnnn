package com.impal.sukagalon.models;

import jakarta.persistence.*;

@Entity
@Table(name="produk")
public class Produk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProduk;
    
    @Column(nullable = false)
    private String merk;

    @Column(nullable = false)
    private Double harga;

    @Column(nullable = false)
    private int stok;
    
    public Produk() {
    }
    public Produk(int idProduk, String merk, Double harga, int stok) {
        this.idProduk = idProduk;
        this.merk = merk;
        this.harga = harga;
        this.stok = stok;
    }
    public int getIdProduk() {
        return idProduk;
    }
    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }
    public String getMerk() {
        return merk;
    }
    public void setMerk(String merk) {
        this.merk = merk;
    }
    public Double getHarga() {
        return harga;
    }
    public void setHarga(Double harga) {
        this.harga = harga;
    }
    public int getStok() {
        return stok;
    }
    public void setStok(int stok) {
        this.stok = stok;
    }
    public void updateStok(int kuantitas){
        this.stok=stok-kuantitas;
    }
}
