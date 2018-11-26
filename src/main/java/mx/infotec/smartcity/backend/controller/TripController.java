/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import mx.infotec.smartcity.backend.model.trip.Trip;
import mx.infotec.smartcity.backend.persistence.TripRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author yolanda.baca
 */
@RestController
@RequestMapping("/trips")
public class TripController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);
    
    @Autowired
    private TripRepository tripRepository;
    
    /**
     * Save Trip
     * @param trip
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trip> createNotification(@RequestBody Trip trip, @PathVariable("id") String id) throws URISyntaxException {
    	
    	trip.setRefUser(id);
	    tripRepository.save(trip);
	    return ResponseEntity.created(new URI("/back-sdk/trips/" + trip.getId())).body(trip);
    }

    /**
     * Get Trip by Id
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Trip getById(@PathVariable("id") String id) {
        return tripRepository.findOne(id);
    }    
    
    /**
     * Get all shared trips
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllTrips() {

    	List<Trip> trips = null;
        try {
        	trips = tripRepository.findBySharedIsTrue();
            if (trips == null) {
                return ResponseEntity.accepted().body(new ArrayList<>(0));
            } else {
                return ResponseEntity.accepted().body(trips);
            }
            
        } catch (Exception ex) {
            LOGGER.error("Error retrieving all trips", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }   
    
    /**
     * Get trips by user
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    public ResponseEntity<?> getTripsByUser(@PathVariable("id") String id) {

    	List<Trip> trips = null;
        try {
            trips = tripRepository.findAllByRefUser(id);
            if (trips == null) {
                return ResponseEntity.accepted().body(new ArrayList<>(0));
            } else {
                return ResponseEntity.accepted().body(trips);
            }
            
        } catch (Exception ex) {
            LOGGER.error("Error retrieving trips by user", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    /**
     * Update trip
     * @param trip
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> updateById(@RequestBody Trip trip, @PathVariable("id") String id) {
        try {
            if (tripRepository.exists(id)) {
                trip.setId(id);
                tripRepository.save(trip);
                return ResponseEntity.accepted().body("updated");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID don't exists");
            }
            
        } catch (Exception ex) {
            LOGGER.error("Error at update", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    
    /**
     * Delete trip
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        try {
        	if (tripRepository.exists(id)) {
                tripRepository.delete(id);
                return ResponseEntity.accepted().body("deleted");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID don't exists");
            }
        	
        } catch (Exception ex) {
            LOGGER.error("Error at delete", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    
}
