package com.baldede.postman.domain;

import java.util.ArrayList;
import java.util.List;

public class StopRegistration {

    private List<Stop> stopRecords;

    private static StopRegistration stdregd = null;

    private StopRegistration(){
        stopRecords = new ArrayList<Stop>();
    }

    public static StopRegistration getInstance() {
        if(stdregd == null) {
            stdregd = new StopRegistration();
            return stdregd;
        } else {
            return stdregd;
        }
    }

    public void add(Stop stop) {
        stopRecords.add(stop);
    }

    public String upDateStop(Stop stop) {
        for(int i=0; i<stopRecords.size(); i++) {
            Stop stp = stopRecords.get(i);
            if(stp.getId() == (stop.getId())) {
                stopRecords.set(i, stop);//update the new record
                return "Update successful";
            }
        }
        return "Update un-successful";
    }

    public String deleteStop(int id) {
        for(int i=0; i<stopRecords.size(); i++){
            Stop stop = stopRecords.get(i);
            if(stop.getId() == (id)){
                stopRecords.remove(i);//update the new record
                return "Delete successful";
            }
        }
        return "Delete un-successful";
    }

    public List<Stop> getStopRecords() {
        return stopRecords;
    }

}
