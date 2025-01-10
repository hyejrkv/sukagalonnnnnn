package com.impal.sukagalon.models;

import java.util.ArrayList;
import java.util.List;

public class PesananFormDTO {
    private int id;
    private User user;
    private String status;
    private List<PesananDetailDTO> pesananDetails = new ArrayList<>();
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
    public List<PesananDetailDTO> getPesananDetails() {
        return pesananDetails;
    }
    public void setPesananDetails(List<PesananDetailDTO> pesananDetails) {
        this.pesananDetails = pesananDetails;
    }

    
}
