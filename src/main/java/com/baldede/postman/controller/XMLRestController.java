package com.baldede.postman.controller;

import com.baldede.postman.domain.*;
import com.baldede.postman.service.OsrmService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class XMLRestController {
    TourSolution ts;
    List<Location> locationList = new ArrayList<>();
    ArrayList<Object> list = new ArrayList<>();
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    FileHandler fh = null;
    Solver<TourSolution> solver;
    String bestSolution = "";


    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String listUser() {
        logger.info("login started");
        return "Login successfully!";
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/start-optimizer", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String getOptimizer(@RequestBody Tour tour) throws JSONException {
        logger.info("optimizer started");
        //System.out.println("optimizer started");
        List<Tour> tours = Arrays.asList(tour);
        List<Stop> stops = tour.getStops();
        TourSolution solvedTourSolution;

        try {
            createTourSolutionWithData(tours, stops);
            readTspLibSolution();
            connectToOsrm();

            SolverFactory<TourSolution> solverFactory = SolverFactory.createFromXmlResource("solver/optimizedTourSolverConfig.xml");
            solver = solverFactory.buildSolver();
            solver.addEventListener(bestSolutionListener);
            TourSolution unsolvedTourSolution = ts;
            solvedTourSolution = solver.solve(unsolvedTourSolution);

        } catch (IOException e) {
            String errorMessage = "Es wurden zu viele Anfragen an den Server gesendet. Versuchen Sie es später bitte noch mal! (HTTP-Code: 429)";
            System.out.println(errorMessage);
            logger.severe(e.getMessage());
            return errorMessage;
        }

        return bestSolution;
    }

    public SolverEventListener bestSolutionListener = new SolverEventListener() {
        @Override
        public void bestSolutionChanged(BestSolutionChangedEvent bestSolutionChangedEvent) {
            System.out.println("BETTER SOLUTION FOUND!");
            TourSolution newSolution = (TourSolution) bestSolutionChangedEvent.getNewBestSolution();
            System.out.println("BETTERSCORE: " + newSolution.getScore());
            bestSolution = getNewSolution(newSolution);
        }
    };


    public String getNewSolution(TourSolution solution) {
        String newStops = "";

        for (Stop attendant : solution.getStopList()) {
            int appointmentCounter = 0;
            Standstill curAppointment;
            curAppointment = attendant.getPreviousStandstill();
            attendant.setId(appointmentCounter);
            newStops += curAppointment;
            System.out.println(newStops);
            appointmentCounter++;
        }

        return newStops;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/stop-optimizer")
    public String stopOptimizer() {
        logger.info("optimizer stopped");
        solver.terminateEarly();

        return "optimizer terminated early";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("logout started");
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "Logout successfully!";
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/getstops", produces = MediaType.APPLICATION_XML_VALUE)
    public String getCustomerToo() throws JAXBException, IOException {

        logger.info("getting all stops");
        File file = new File("/Users/baldede/Documents/workspace/postman/src/main/resources/stop.xml");
        String content = FileUtils.readFileToString(file);
        System.out.println(content);

        return content;
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }

    /**
     * Methods which have no routings
     *
     * @param tours
     * @param stops
     * @return
     */
    private TourSolution createTourSolutionWithData(List<Tour> tours, List<Stop> stops) {

        logger.info("creating toursolution with data");
        for (Stop s : stops) {
            Location location = new Location();
            location.setLongitude(s.getLongitude());
            location.setLatitude(s.getLatitude());
            //location.setDistanceFromPrevious(s.getDistanceFromPrevious());
            locationList.add(location);
            s.setLocation(location);
        }

        Domicile domicile = new Domicile();
        domicile.setLocation(locationList.get(0));
        domicile.setId(stops.get(0).getId());
        domicile.setName("Domicile" + stops.get(0).getName());

        this.ts = new TourSolution();
        ts.setDomicile(domicile);
        ts.setStopList(stops);
        ts.setTourList(tours);
        ts.setLocationList(locationList);

        return ts;
    }

    private void readTspLibSolution() throws IOException, JSONException {

        logger.info("readTspLibSolution started");
        int visitListSize = ts.getStopList().size();
        Map<Long, Stop> idToStopMap = new HashMap<>(visitListSize);
        for (Stop stop : ts.getStopList()) {
            idToStopMap.put(stop.getId(), stop);
        }
        Standstill previousStandstill = ts.getDomicile();
        Stop nextStop = null;
        for (int i = 0; i < visitListSize; i++) {
            long stopId = i;
            Stop stop = idToStopMap.get(stopId);
            if (stop == null) {
                logger.severe("The visitId (" + stopId
                        + ") is does not exist.");
                throw new IllegalStateException("The visitId (" + stopId
                        + ") is does not exist.");
            }
            stop.setPreviousStandstill(previousStandstill);
            previousStandstill = stop;
            for (int j = 1; j < visitListSize; j++) {
                Stop stop1 = idToStopMap.get(j);
            }
        }
    }


    private void connectToOsrm() throws IOException, JSONException {

        //Connecting to OSRM API for a duration matrix. This API computes the duration of the fastest route between all pairs of supplied coordinates.
        //Make a list from the JSONarray
        OsrmService osrmService = new OsrmService();
        list = (ArrayList<Object>) osrmService.getDistanceMatrix(locationList);

        //Creates a list of the locations the distances of the matrix
        double travelDistance;
        int locationListSize = locationList.size();
        for (int i = 0; i < locationListSize; i++) {
            Location location = (Location) locationList.get(i);
            Map<Location, Double> travelDistanceMap = new LinkedHashMap<>(locationListSize);
            JSONArray array = (JSONArray) list.get(i);
            for (int j = 0; j < locationListSize; j++) {
                if (array.get(j).getClass() == Integer.class) {
                    travelDistance = (double) ((Integer) array.get(j));
                } else {
                    travelDistance = (double) array.get(j);
                }
                if (i == j) {
                    if (travelDistance != 0.0) {
                        logger.severe("The travelDistance (" + travelDistance
                                + ") should be zero.");
                        throw new IllegalStateException("The travelDistance (" + travelDistance
                                + ") should be zero.");
                    }
                } else {

                    Location otherLocation = (Location) locationList.get(j);
                    travelDistanceMap.put(otherLocation, travelDistance);
                }
            }
            location.setTravelDistanceMap(travelDistanceMap);
        }
        locationList.clear();
    }
}



