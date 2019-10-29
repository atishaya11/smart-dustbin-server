package com.jss.sdmp.management.bin.model;

import com.jss.sdmp.management.ward.model.Ward;
import com.jss.sdmp.users.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
public class Bin {

    @Id
    private String id;

    @Indexed(unique = true)
    private String bin;

    private String landmark;

    private Instant registeredAt;

    private Instant activatedAt;

    private GeoJsonPoint location;

    @DBRef
    private Ward ward;

    @DBRef
    private User installedBy;

    private boolean active;

    public Bin() {
    }

    public Bin(String bin, Instant registeredAt, GeoJsonPoint location, User installedBy,
               Ward ward) {
        this.bin = bin;
        this.registeredAt = registeredAt;
        this.location = location;
        this.installedBy = installedBy;
        this.ward = ward;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Instant getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(Instant activatedAt) {
        this.activatedAt = activatedAt;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public User getInstalledBy() {
        return installedBy;
    }

    public void setInstalledBy(User installedBy) {
        this.installedBy = installedBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
