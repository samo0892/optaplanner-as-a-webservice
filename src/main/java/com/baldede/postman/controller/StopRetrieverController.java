package com.baldede.postman.controller;

import com.baldede.postman.app.StopRepository;
import com.baldede.postman.domain.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StopRetrieverController {

    @Autowired
    @Qualifier("stopRepository")
    private StopRepository stopRepository;

    @GetMapping(path = "/stops")
    public List<Stop> findAll() {
        return stopRepository.findAll();
    }

    @PostMapping(path = "/setstop",  consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public Stop create(@RequestBody Stop stop) {
        Stop newStop = new Stop();
        newStop = stop;
        return stopRepository.save(newStop);
    }

    @DeleteMapping(path = "/delete/stop/{id}")
    public void deleteStop(@PathVariable("id") int id) {
        stopRepository.deleteById((long) id);
    }

    @GetMapping(path = "/stop/{id}")
    public Optional<Stop> findStop(@PathVariable("id") int id) {
        return stopRepository.findById((long) id);
    }

    @PutMapping(path = "/update/stop", consumes = MediaType.APPLICATION_XML_VALUE)
    public Stop update(@RequestBody Stop stop){
        return stopRepository.save(stop);
    }
}
