//package com.baldede.postman;
//
//import com.baldede.postman.domain.*;
//import org.junit.Test;
//import org.optaplanner.core.api.solver.SolverFactory;
//import org.optaplanner.test.impl.score.buildin.simplelong.SimpleLongScoreVerifier;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class StaticScoreTest {
//
//    private SimpleLongScoreVerifier<TourSolution> scoreVerifier  =
//            new SimpleLongScoreVerifier(
//                    SolverFactory.createFromXmlResource("optimizedTourSolverConfig.xml"));
//
//    @Test
//    public void testDistanceFromPreviousRule() {
//        TourSolution tourSolution = new TourSolution();
//
//        Domicile domicile = new Domicile();
//        domicile.setId(1L);
//        domicile.setLocation(new Location(11L, 0.0, 0.0)); // TODO delete distanceFromPrevious
//        tourSolution.setDomicile(domicile);
//
//        // Stops
//        Stop stop1 = new Stop(new Location(12L, 100.0, 0.0));
//        //Stop stop2 = new Stop(new Location(13L, 1.0, 1.0, 0.0));
//        List<Stop> stops = new ArrayList<>();
//        stops.add(stop1);
//        //stops.add(stop2);
//
//
//        //stop2.setPreviousStandstill(stop1);
//        stop1.setPreviousStandstill(domicile);
//
//        // tour
//        Tour tour = new Tour();
//        tour.setStops(stops);
//
//        ArrayList<Tour> tourList = new ArrayList<Tour>();
//        tourList.add(tour);
//        tourSolution.setTourList(tourList);
//        tourSolution.setStopList(stops);
//
//        scoreVerifier.assertWeight( null, "distanceToPreviousStandstill", -100000, tourSolution);
//        scoreVerifier.assertWeight( null, "distanceFromLastVisitToDomicile", -100000, tourSolution);
//    }
//
//    @Test
//    public void testWithTwoStops() {
//        TourSolution tourSolution = new TourSolution();
//
//        Domicile domicile = new Domicile();
//        domicile.setId(1L);
//        domicile.setLocation(new Location(11L, 0.0, 0.0)); // TODO delete distanceFromPrevious
//        tourSolution.setDomicile(domicile);
//
//        // Stops
//        Stop stop1 = new Stop(new Location(12L, 100.0, 0.0, 0.0));
//        Stop stop2 = new Stop(new Location(13L, 200.0, 0.0, 0.0));
//        List<Stop> stops = new ArrayList<>();
//        stop2.setPreviousStandstill(stop1);
//        stop1.setPreviousStandstill(domicile);
//
//
//        stops.add(stop1);
//        stops.add(stop2);
//
//
//
//
//        // tour
//        Tour tour = new Tour("tour 1");
//        tour.setStops(stops);
//
//        ArrayList<Tour> tourList = new ArrayList<Tour>();
//        tourList.add(tour);
//        tourSolution.setTourList(tourList);
//        tourSolution.setStopList(stops);
//
//        scoreVerifier.assertWeight( null, "distanceToPreviousStandstill", -200000, tourSolution);
//        scoreVerifier.assertWeight( null, "distanceFromLastVisitToDomicile", -200000, tourSolution);
//    }
//}
