package com.baldede.postman.domain;


import com.baldede.postman.solver.DomicileDistanceStandstillStrengthWeightFactory;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@PlanningEntity()
@XmlSeeAlso(Tour.class)
@XmlRootElement
@Entity
@Table(name = "stop")
public class Stop implements Standstill {

    @Id
    private long id;
    private boolean minOccurs;
    private boolean maxOccurs;
    private String name;
    private String street;
    private String houseNo;
    private String postalCode;
    private String city;
    private double longitude;
    private double latitude;
    private double radius;
    private String customerNo;
    private String orderNo;
    private String description;
    private boolean isStartAddress;
    private boolean isEndAddress;
    private int sortNo;
    private double distanceFromPrevious;
    private int minutesFromPrevious;
    private int groupId;

    @Transient
    private Standstill previousStandstill;
    @Transient
    private Location location;

    public Stop() {
    }

    @XmlElement(name = "location")
    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @XmlElement(name = "previousStandstill")
    @PlanningVariable(valueRangeProviderRefs = {"domicileRange", "stopRange"},
            graphType = PlanningVariableGraphType.CHAINED,
            strengthWeightFactoryClass = DomicileDistanceStandstillStrengthWeightFactory.class)
    public Standstill getPreviousStandstill() {
        return previousStandstill;
    }

    public void setPreviousStandstill(Standstill previousStandstill) {
        this.previousStandstill = previousStandstill;
    }


    /**
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public long getDistanceFromPreviousStandstill() {
        if (previousStandstill == null) {
            return 0L;
        }

        return getDistanceFrom(previousStandstill);
    }



    /**
     * @param standstill never null
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public long getDistanceFrom(Standstill standstill) {
        return standstill.getLocation().getDistanceTo(location);
    }

    /**
     * @param standstill never null
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    @Override
    public long getDistanceTo(Standstill standstill) {

        return location.getDistanceTo(standstill.getLocation());
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name = "isMinOccurs")
    public boolean isMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(boolean minOccurs) {
        this.minOccurs = minOccurs;
    }

    @XmlElement(name = "isMaxOccurs")
    public boolean isMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(boolean maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @XmlElement(name = "houseNo")
    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    @XmlElement(name = "postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @XmlElement(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @XmlElement(name = "longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @XmlElement(name = "latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @XmlElement(name = "radius")
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @XmlElement(name = "customerNo")
    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    @XmlElement(name = "orderNo")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartAddress(boolean startAddress) {
        isStartAddress = startAddress;
    }

    public void setEndAddress(boolean endAddress) {
        isEndAddress = endAddress;
    }

    @XmlElement(name = "sortNo")
    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }



    @Override
    public String toString() {
        return "Stop{" +
                 name + '\'' +
               // ", location='" + location +
                '}';
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }


    @XmlElement(name = "isStartAddress")
    public boolean isStartAddress() {
        return isStartAddress;
    }

    @XmlElement(name = "isEndAddress")
    public boolean isEndAddress() {
        return isEndAddress;
    }


    @XmlElement(name = "distanceFromPrevious")
    public double getDistanceFromPrevious() {
        return distanceFromPrevious;
    }

    public void setDistanceFromPrevious(double distanceFromPrevious) {
        this.distanceFromPrevious = distanceFromPrevious;
    }

    @XmlElement(name = "minutesFromPrevious")
    public Integer getMinutesFromPrevious() {
        return minutesFromPrevious;
    }

    public void setMinutesFromPrevious(int minutesFromPrevious) {
        this.minutesFromPrevious = minutesFromPrevious;
    }

    @XmlElement(name = "groupId")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Stop(String name, String street, String houseNo,
                String postalCode, String city, double longitude,
                double latitude, double radius, String customerNo,
                String orderNo, String description, boolean isStartAddress,
                boolean isEndAddress, int sortNo, double distanceFromPrevious,
                int minutesFromPrevious, int groupId) {

        this.name = name;
        this.street = street;
        this.houseNo = houseNo;
        this.postalCode = postalCode;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.customerNo = customerNo;
        this.orderNo = orderNo;
        this.description = description;
        this.isStartAddress = isStartAddress;
        this.isEndAddress = isEndAddress;
        this.sortNo = sortNo;
        this.distanceFromPrevious = distanceFromPrevious;
        this.minutesFromPrevious = minutesFromPrevious;
        this.groupId = groupId;
    }

    public Stop(String name, String street, String houseNo, String postalCode) {
        this.name = name;
        this.street = street;
        this.houseNo = houseNo;
        this.postalCode = postalCode;
    }

    public Stop(String name, double latitude, double longitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public Stop(String name) {
        this.name = name;
    }

    public Stop(Location location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }
}
