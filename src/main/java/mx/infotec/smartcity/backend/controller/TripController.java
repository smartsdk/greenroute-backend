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
import java.util.Map;
import java.util.stream.Collectors;

import mx.infotec.smartcity.backend.model.EmissionFactor;
import mx.infotec.smartcity.backend.model.Pollutant;
import mx.infotec.smartcity.backend.model.trip.Segment;
import mx.infotec.smartcity.backend.model.trip.Trip;
import mx.infotec.smartcity.backend.persistence.EmissionFactorRepository;
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
    
    @Autowired
    private EmissionFactorRepository emissionFactorRepository;
    
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
    	
    	List<Pollutant> pollutants = new ArrayList<Pollutant>(); 
    	Double co2= 0.0;
    	Double ch4= 0.0;
    	Double n2o= 0.0;
    	Double totalDistance= 0.0;
    	
    	for (Segment segment : trip.getSegments()) {
    		
    		String mode = segment.getMode().equals("Public Transportation")? segment.getAgencyId() : segment.getMode();
    		
    		try {
				List<EmissionFactor> list = emissionFactorRepository.findByTransportMode(mode);
				Map<String, EmissionFactor> factors = list.stream().collect(Collectors.toMap(EmissionFactor::getName, item -> item));

				Double distance = segment.getDistance()/1000;
				totalDistance += distance; //km

				co2 += distance*factors.get("co2").getValue();
				ch4 += distance*factors.get("ch4").getValue();
				n2o += distance*factors.get("n2o").getValue();
			} catch (Exception e) {
				LOGGER.error("Emission Factors calculation error", e);
			}
		}
    	
    	pollutants.add(new Pollutant("co2", "Carbon Dioxide", co2, totalDistance, "g/km"));
    	pollutants.add(new Pollutant("ch4", "Methane", ch4, totalDistance, "g/km"));
    	pollutants.add(new Pollutant("n20", "Nitrous Oxide", n2o, totalDistance, "g/km"));
    	trip.setPollutants(pollutants);
    	
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
