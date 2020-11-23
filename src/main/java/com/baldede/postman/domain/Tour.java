package com.baldede.postman.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "tour")
public class Tour {

    private String tourname;
    private String assetName;
    private double totalDistance;
    private int totalMinutes;
    private boolean truckMode;

    private List<Stop> stops;

    public Tour() {}

    public Tour (String tourname) {
        super();
        this.tourname = tourname;
    }


    @Override
    public String toString() {
        return "Tour{" +
                 ", tourName='" + tourname + '\'' +
                ", assetName='" + assetName + '\'' +
                ", totalDistance='" + totalDistance + '\'' +
                ", totalMinutes='" + totalMinutes + '\'' +
                ", truckMode='" + truckMode + '\'' +
                ", stops ='" + stops +
                '}';
    }

    @XmlElement(name = "name")
    public String getTourname() {
        return tourname;
    }

    public void setTourname(String tourname) {
        this.tourname = tourname;
    }

    @XmlElement(name = "assetName")
    public String getAssetName() {
        return assetName;
    }


    @XmlElement(name = "totalDistance")
    public double getTotalDistance() {
        return totalDistance;
    }

    @XmlElement(name = "totalMinutes")
    public int getTotalMinutes() {
        return totalMinutes;
    }

    @XmlElement(name = "truckMode")
    public boolean isTruckMode() {
        return truckMode;
    }

    @XmlElement(name = "stop")
    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public void setTruckMode(boolean truckMode) {
        this.truckMode = truckMode;
    }
}
