package com.mskn.vaadinspringproject.backend.entities;

import com.mskn.vaadinspringproject.backend.entities.base.BaseEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String email;
    private String phone;
    private String address;

    @Lob
    private String notes;

    private Boolean active = Boolean.TRUE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_users", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<AppUser> owners = new HashSet<>();

    @Override
    public String toString() {
        return name != null ? name : "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<AppUser> getOwners() {
        return owners;
    }

    public void setOwners(Set<AppUser> owners) {
        this.owners = owners;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}