package com.jss.sdmp.simulation;

import java.util.Random;

public class RandomLocationGenerator {

    public static double[] getLocation(double x0, double y0, int radius) {
        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double newX = x / Math.cos(Math.toRadians(y0));

        double lat = newX + x0;
        double lng = y + y0;

        lat = (double)Math.round(lat * 1000000d) / 1000000d;
        lng = (double)Math.round(lng * 1000000d) / 1000000d;

        return new double[] {lat, lng};
    }

}
