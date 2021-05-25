package com.baldede.postman.service;

import com.baldede.postman.domain.Location;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * This is a service to connect to the OSRM-API and getting the duration between all the locations
 */

@Service
public class OsrmService {

    final static String osrmApi = "http://router.project-osrm.org/table/v1/driving/";
    String coordinates = "";
    String urlToOsrmApi = "";
    JSONObject json = null;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    StringBuilder stringBuilder = new StringBuilder();


    public void getDistanceMatrix(List<Location> locationList) throws JSONException, IOException {

        try {
                logger.info("getDistanceMatrix started");

                //creating a new string with the coordinates of the locations
                for(Location location : locationList) {
                    stringBuilder.append(location.getLatitude() + "," + location.getLongitude() + ";");
                }
                coordinates = coordinates.substring(0, coordinates.length() - 1);

                //creating a new url with the api url of osrm and the coordinates
                urlToOsrmApi = osrmApi + coordinates;

                //connecting to the osrm api and getting the durations from the response
                json = new JSONObject(IOUtils.toString( new URL(urlToOsrmApi), StandardCharsets.UTF_8));
                JSONArray jsonArray =  json.getJSONArray("durations");

                //Make a list from the JSONarray
                ArrayList<Object> list = new ArrayList<>();
                if (jsonArray != null) {
                    int len = jsonArray.length();
                    for (int i=0;i<len;i++){
                        list.add(jsonArray.get(i));
                    }
                }
                coordinates = "";
                urlToOsrmApi = "";
                json = null;
                return list;
        } catch (IOException e) {
            logger.severe(e.getMessage());
        } catch (JSONException e) {
            logger.severe(e.getMessage());
        }
        Collections.emptyList();
    }
}
