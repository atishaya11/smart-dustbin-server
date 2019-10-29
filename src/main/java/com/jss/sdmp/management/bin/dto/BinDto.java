package com.jss.sdmp.management.bin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jss.sdmp.management.ward.dto.WardDto;
import com.jss.sdmp.users.dto.UserBean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.Instant;

public class BinDto {

    private String id;

    private String bin;

    private String landmark;

    private Instant registeredAt;

    private Instant activatedAt;

    private GeoJsonPoint location;

    @JsonIgnore
    private WardDto ward;

    private UserBean installedBy;

    private boolean active;

    private BinStatus status;

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

    public WardDto getWard() {
        return ward;
    }

    public void setWard(WardDto ward) {
        this.ward = ward;
    }

    public UserBean getInstalledBy() {
        return installedBy;
    }

    public void setInstalledBy(UserBean installedBy) {
        this.installedBy = installedBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BinStatus getStatus() {
        return status;
    }

    public void setStatus(BinStatus status) {
        this.status = status;
    }
}
