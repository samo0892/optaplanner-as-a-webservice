package com.baldede.postman.domain;

import com.baldede.postman.persistence.AbstractPersistable;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.simplelong.SimpleLongScore;
import org.optaplanner.persistence.xstream.api.score.buildin.simplelong.SimpleLongScoreXStreamConverter;

import java.util.Collections;
import java.util.List;

@PlanningSolution
@XStreamAlias("TourSolution")
public class TourSolution extends AbstractPersistable {

    private List<Tour> tourList;
    private List<Stop> stopList;
    @XStreamConverter(SimpleLongScoreXStreamConverter.class)
    private SimpleLongScore score;
    private List<Location> locationList;
    private Domicile domicile;


    public List<Tour> getTourList() {
        return tourList;
    }

    public void setTourList(List<Tour> tourList) {
        this.tourList = tourList;
    }

    //@ProblemFactCollectionProperty
    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    @ProblemFactProperty
    public Domicile getDomicile() {
        return domicile;
    }

    public void setDomicile(Domicile domicile) {
        this.domicile = domicile;
    }

    @PlanningEntityCollectionProperty
    @ValueRangeProvider(id = "stopRange")
    public List<Stop> getStopList() {
        return stopList;
    }

    public void setStopList(List<Stop> stopList) {
        this.stopList = stopList;
    }

    @PlanningScore
    public SimpleLongScore getScore() {
        return score;
    }

    public void setScore(SimpleLongScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    @ValueRangeProvider(id = "domicileRange")
    public List<Domicile> getDomicileRange() {
        return Collections.singletonList(domicile);
    }

    public TourSolution() {}

    @Override
    public String toString() {
        return
                "Stops = '" + getStopList() + '\n' +
                '}';
    }
}
