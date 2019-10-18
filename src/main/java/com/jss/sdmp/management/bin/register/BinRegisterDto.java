package com.jss.sdmp.management.bin.register;

public class BinRegisterDto {

    private String bin;

    private double lat;

    private double lng;

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "BinRegisterDto{" +
            "bin='" + bin + '\'' +
            ", lat=" + lat +
            ", lng=" + lng +
            '}';
    }
}
