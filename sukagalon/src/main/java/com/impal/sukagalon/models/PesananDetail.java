package com.impal.sukagalon.models;

import jakarta.persistence.*;

@Entity
@Table(name = "pesanan_detail")
public class PesananDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "pesanan_id", nullable = false)
    private Pesanan pesanan;
    
    @ManyToOne
    @JoinColumn(name = "galon_id", nullable = false)
    private Produk produk;
    
    @Column(nullable = false)
    private Integer kuantitas;
    
    @Column(nullable = false)
    private Double subTotal;
    
    // Getters and Setters
    public Integer getKuantitas() { return kuantitas; }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Pesanan getPesanan() {
        return pesanan;
    }
    public void setPesanan(Pesanan pesanan) {
        this.pesanan = pesanan;
    }
    public Produk getProduk() {
        return produk;
    }
    public void setProduk(Produk produk) {
        this.produk = produk;
    }
    public void setKuantitas(Integer kuantitas) { 
        this.kuantitas = kuantitas;
        this.calculateSubTotal();
    }
    
    public Double getSubTotal() { return subTotal; }

    public void setSubTotal(Double subTotal) {
        this.subTotal=subTotal;
    };
    
    private void calculateSubTotal() {
        if (this.produk != null && this.kuantitas != null) {
            this.subTotal = this.produk.getHarga() * this.kuantitas;
        }
    }

    public void calculateStok(){
        if (this.produk != null && this.kuantitas != null) {
            this.produk.updateStok(kuantitas);
        }
    }
}