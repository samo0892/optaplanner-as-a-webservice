//package com.baldede.postman;
//
//import com.baldede.postman.domain.*;
//import com.baldede.postman.service.OsrmService;
//import org.apache.commons.io.IOUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Test;
//import org.optaplanner.core.api.solver.Solver;
//import org.optaplanner.core.api.solver.SolverFactory;
//import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
//import org.optaplanner.core.api.solver.event.SolverEventListener;
//
//import javax.swing.*;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.util.*;
//
//
//public class OptimizerTest {
//
//    int locationListSize = 0;
//    TourSolution ts;
//    List<Location> locationList = new ArrayList<>();
//    Map<Location, Double> travelDistanceMap = new LinkedHashMap<>(locationListSize);
//    ArrayList<Object> list = new ArrayList<Object>();
//
//    private TourSolution loadTestData() throws IOException, JSONException {
//        File file = new File("/Users/baldede/Documents/workspace/postman/src/test/resources/stop.xml");
//        Tour tour = null;
//        JAXBContext jaxbContext;
//
//        try {
//            jaxbContext = JAXBContext.newInstance(Tour.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            tour = (Tour) jaxbUnmarshaller.unmarshal(file);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//        List<Tour> tours = Arrays.asList(tour);
//
//        List<Stop> stops = tour.getStops();
//        for (Stop s : stops) {
//            Location location = new Location();
//            location.setLongitude(s.getLongitude());
//            location.setLatitude(s.getLatitude());
//            location.setId(s.getId());
//            location.setName(s.getName());
//            locationList.add(location);
//            s.setLocation(location);
//        }
//
//
//        Domicile domicile = new Domicile();
//        domicile.setLocation(locationList.get(0));
//        domicile.setId(stops.get(0).getId());
//        domicile.setName(stops.get(0).getName());
//
//        this.ts = new TourSolution();
//        ts.setDomicile(domicile);
//        ts.setStopList(stops);
//        ts.setTourList(tours);
//        ts.setLocationList(locationList);
//        readTspLibSolution();
//        return ts;
//    }
//
//
//    private void readTspLibSolution() throws IOException, JSONException {
//
//        long domicileId = ts.getDomicile().getId();
//        Domicile domicile = ts.getDomicile();
//        if (!domicile.getId().equals(domicileId)) {
//            throw new IllegalStateException("The domicileId (" + domicileId
//                    + ") is not the domicile's id (" + domicile.getId() + ").");
//        }
//        int visitListSize = ts.getStopList().size();
//        Map<Long, Stop> idToStopMap = new HashMap<>(visitListSize);
//        for (Stop stop : ts.getStopList()) {
//            idToStopMap.put(stop.getId(), stop);
//        }
//        Standstill previousStandstill = domicile;
//        for (int i = 0; i < visitListSize; i++) {
//            long stopId = i;
//            Stop stop = idToStopMap.get(stopId);
//            if (stop == null) {
//                throw new IllegalStateException("The visitId (" + stopId
//                        + ") is does not exist.");
//            }
//            stop.setPreviousStandstill(previousStandstill);
//            previousStandstill = stop;
//        }
//
//
//        //Connecting to OSRM API for a duration matrix. This API computes the duration of the fastest route between all pairs of supplied coordinates.
//        //Make a list from the JSONarray
//        OsrmService osrmService = new OsrmService();
//        list = osrmService.getDistanceMatrix(locationList);
//
//
//
//        //Creates a list of the locations the distances of the matrix
//        double travelDistance;
//        int locationListSize = locationList.size();
//        for (int i = 0; i < locationListSize; i++) {
//            Location location = (Location) locationList.get(i);
//            Map<Location, Double> travelDistanceMap = new LinkedHashMap<>(locationListSize);
//            JSONArray array = (JSONArray) list.get(i);
//            for (int j = 0; j < locationListSize; j++) {
//                if(array.get(j).getClass() == Integer.class) {
//                    travelDistance = (double) ((Integer) array.get(j));
//                } else {
//                    travelDistance = (double) array.get(j);
//                }
//                if (i == j) {
//                    if (travelDistance != 0.0) {
//                        throw new IllegalStateException("The travelDistance (" + travelDistance
//                                + ") should be zero.");
//                    }
//                } else {
//
//                    Location otherLocation = (Location) locationList.get(j);
//                    travelDistanceMap.put(otherLocation, travelDistance);
//                }
//            }
//            location.setTravelDistanceMap(travelDistanceMap);
//            System.out.println("TD;: " + location.getTravelDistanceMap());
//        }
//
//    }
//
//
//    @Test
//    public void test() throws IOException, JSONException {
//        loadTestData();
//        readTspLibSolution();
//    }
//
//    @Test
//    public void testLoadTestData() throws IOException, JSONException {
//        TourSolution testSolution = this.loadTestData();
//        assert(testSolution.getStopList().size() == 4);
//        assert(testSolution.getTourList().size() == 1);
//
//        readTspLibSolution();
//    }
//
//    @Test
//    public void solvedSolutionIsNotNull() throws IOException, JSONException {
//
//        SolverFactory<TourSolution> solverFactory = SolverFactory.createFromXmlResource("solver/optimizedTourSolverConfig.xml");
//        Solver<TourSolution> solver = solverFactory.buildSolver();
//
//        SolverEventListener bestSolutionListener = new SolverEventListener() {
//            @Override
//            public void bestSolutionChanged(BestSolutionChangedEvent bestSolutionChangedEvent) {
//                System.out.println("BETTER SOLUTION FOUND!");
//                TourSolution newSolution = (TourSolution) bestSolutionChangedEvent.getNewBestSolution();
//                System.out.println("BETTERSCORE: " + newSolution.getScore());
//                System.out.println("BETTERSOLUTION: " + newSolution);
//            }
//        };
//
//        solver.addEventListener(bestSolutionListener);
//
//        TourSolution unsolvedTourSolution = this.loadTestData();
//        TourSolution solvedTourSolution = solver.solve(unsolvedTourSolution);
//        //System.out.println(solver.explainBestScore());
//        System.out.println("SCORE1: " + unsolvedTourSolution.getScore());
//        System.out.println("SCORE2: " + solvedTourSolution.getScore());
//        System.out.println("UNSOLVED: " + unsolvedTourSolution.getStopList());
//        System.out.println("BEST SOLUTION: " + solvedTourSolution.getStopList());
//
//        System.out.println("\nSolved :\n"
//                + toDisplayString(solvedTourSolution));
//
//
//        Stop st0 = solvedTourSolution.getStopList().get(0);
//        System.out.println("PS0: " + st0.getPreviousStandstill());
//
//        Stop st1 = solvedTourSolution.getStopList().get(1);
//        System.out.println("PS1: " + st1.getPreviousStandstill());
//
//        Stop st2 = solvedTourSolution.getStopList().get(2);
//        System.out.println("PS2: " + st2.getPreviousStandstill());
//
//        Stop st3 = solvedTourSolution.getStopList().get(3);
//        System.out.println("PS3: " + st3.getPreviousStandstill()
//        );
////
////        System.out.println("SOLVER: " + solver.explainBestScore());
//       // System.out.println("SOLVED: " + solvedTourSolution);
//        assert (!solvedTourSolution.getStopList().equals(unsolvedTourSolution.getStopList()));
//    }
//
//
//    public static String toDisplayString(TourSolution tourSolution) {
//        StringBuilder displayString = new StringBuilder();
//        for (Stop stop : tourSolution.getStopList()) {
//            Standstill standstill = stop.getPreviousStandstill();
//            displayString.append("  ").append(stop.getName()).append(" -> ")
//                    .append(standstill == null ? null : standstill.getLocation().getName()).append("\n");
//        }
//        return displayString.toString();
//    }
//
//
//    @Test
//    public void newTest() {
//
//        TourSolution unsolvedTourSolution = new TourSolution();
//
//        Domicile domicile = new Domicile();
//        domicile.setId(1L);
//        domicile.setLocation(new Location(11L, "location-1",52.496474, 13.368373)); // TODO delete distanceFromPrevious
//        unsolvedTourSolution.setDomicile(domicile);
//
//        Stop stop1 = new Stop("12L", 52.496474, 13.368373); //Akquinet
//        Stop stop2 = new Stop("13L", 52.465423, 13.344653); //Zu Hause
//        Stop stop3 = new Stop("13L", 52.454377, 13.305447); //Botanischer Garten
//        Stop stop4 = new Stop("13L", 52.458147, 13.320773); //Das Schloß
//        Stop stop5 = new Stop("13L", 52.491797, 13.395709); //Gneisenauerstr
//        List<Stop> stops = new ArrayList<>();
//
//
//
//        Location location1 = (new Location(11L, "location-1",52.496474, 13.368373));
//        Location location2 = (new Location(12L, "location-1",52.496474, 13.368373));
//        Location location3 = (new Location(13L, "location-1",52.496474, 13.368373));
//        Location location4 = (new Location(14L, "location-1",52.496474, 13.368373));
//        Location location5 = (new Location(15L, "location-1",52.496474, 13.368373));
//        List<Location> locations = new ArrayList<>();
//
//
//        stop1.setLocation(location1);
//        stop2.setLocation(location2);
//        stop3.setLocation(location3);
//        stop4.setLocation(location4);
//        stop5.setLocation(location5);
//
//        stops.add(stop1);
//        stops.add(stop2);
//        stops.add(stop3);
//        stops.add(stop4);
//        stops.add(stop5);
//        locations.add(location1);
//        locations.add(location2);
//        locations.add(location3);
//        locations.add(location4);
//        locations.add(location5);
//
//        // tour
//        Tour tour = new Tour("tour 1");
//        tour.setStops(stops);
//
//        ArrayList<Tour> tourList = new ArrayList<Tour>();
//        tourList.add(tour);
//        unsolvedTourSolution.setTourList(tourList);
//        unsolvedTourSolution.setStopList(stops);
//        unsolvedTourSolution.setLocationList(locations);
//        unsolvedTourSolution.setDomicile(domicile);
//
//        SolverFactory<TourSolution> solverFactory = SolverFactory.createFromXmlResource("solver/optimizedTourSolverConfig.xml");
//        Solver<TourSolution> solver = solverFactory.buildSolver();
//        TourSolution solvedTourSolution = solver.solve(unsolvedTourSolution);
//        System.out.println("SOLVED: " + solvedTourSolution);
//
//    }
//}
