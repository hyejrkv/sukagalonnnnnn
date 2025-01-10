package com.impal.sukagalon.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="pesanan")
public class Pesanan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = true)
    private User user;
    
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false)
    private Double totalHarga;
    
    @Column(nullable = false)
    private LocalDateTime tanggalPesanan;
    
    
    @OneToMany(mappedBy = "pesanan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PesananDetail> pesananDetails = new ArrayList<>();
    
    // Methods to manage bidirectional relationship
    public void addPesananDetail(PesananDetail detail) {
        pesananDetails.add(detail);
        detail.setPesanan(this);
        //detail.calculateStok();
        //calculateTotal();
    }
    
    public void removePesananDetail(PesananDetail detail) {
        pesananDetails.remove(detail);
        detail.setPesanan(null);
        calculateTotal();
    }
    
    private void calculateTotal() {
        this.totalHarga = pesananDetails.stream()
            .mapToDouble(PesananDetail::getSubTotal)
            .sum();
    }
    
    public List<PesananDetail> getPesananDetails() { return pesananDetails; }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(Double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public LocalDateTime getTanggalPesanan() {
        return tanggalPesanan;
    }

    public void setTanggalPesanan(LocalDateTime tanggalPesanan) {
        this.tanggalPesanan = tanggalPesanan;
    }

    public void setPesananDetails(List<PesananDetail> pesananDetails) {
        this.pesananDetails.clear();
        if (pesananDetails != null) {
            pesananDetails.forEach(this::addPesananDetail);
        }
    }
}
