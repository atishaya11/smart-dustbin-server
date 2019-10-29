package com.jss.sdmp.data.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
public class BinData {

    private String bin;

    private Instant instant;

    private double percentage;

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
