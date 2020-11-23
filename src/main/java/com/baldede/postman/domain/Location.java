package com.baldede.postman.domain;

import com.baldede.postman.persistence.AbstractPersistable;

import java.util.Map;

public class Location  extends AbstractPersistable {

    protected double latitude;
    protected double longitude;
    protected Map<Location, Double> travelDistanceMap;
    private String name;


    public Location() {
    }

    public Location(long id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * If a matrix for the RoadLocation exist, you can use this function!
     * This is a matirx of the durations of the locations
     * @param location
     * @return
     */
    public long getDistanceTo(Location location) {
        if (this == location) {

            return 0L;
        }
        double distance = travelDistanceMap.get((Location) location);
        // Multiplied by 1000 to avoid floating point arithmetic rounding errors
        return (long) (distance * 1000.0 + 0.5);
    }


    @Override
    public String toString(){
        return ("Name: " + this.name + " / Latitude: " + this.latitude + " / Longitude:" + this.longitude);
    }



    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Map<Location, Double> getTravelDistanceMap() {
        return travelDistanceMap;
    }

    public void setTravelDistanceMap(Map<Location, Double> travelDistanceMap) {
        this.travelDistanceMap = travelDistanceMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




//
//    public long getDistanceTo(Location location) {
//            double distance = getAirDistanceDoubleTo(location);
//            // Multiplied by 1000 to avoid floating point arithmetic rounding errors
//            return (long) (distance * 1000.0 + 0.5);
//    }

    public double getAirDistanceDoubleTo(Location location) {
        // Implementation specified by TSPLIB http://www2.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/
        // Euclidean distance (Pythagorean theorem) - not correct when the surface is a sphere
        double latitudeDifference = location.latitude - latitude;
        double longitudeDifference = location.longitude - longitude;
        return Math.sqrt(
                (latitudeDifference * latitudeDifference) + (longitudeDifference * longitudeDifference));
    }

    public double getAngle(Location location) {
        // Euclidean distance (Pythagorean theorem) - not correct when the surface is a sphere
        double latitudeDifference = location.latitude - latitude;
        double longitudeDifference = location.longitude - longitude;
        return Math.atan2(latitudeDifference, longitudeDifference);
    }
}
