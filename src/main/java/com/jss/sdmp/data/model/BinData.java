package com.jss.sdmp.data.model;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.Instant;

public class BinData {

    private String binId;
    private Instant instant;
    private GeoJsonPoint location;

}
