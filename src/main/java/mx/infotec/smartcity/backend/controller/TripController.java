/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.controller;

import java.net.URI;
import java.net.URISyntaxException;
import mx.infotec.smartcity.backend.model.trip.Trip;
import mx.infotec.smartcity.backend.persistence.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/trip")
public class TripController {
    
    @Autowired
    private TripRepository tripRepository;
    
        @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Trip> createNotification(@RequestBody Trip trip) throws URISyntaxException {
            
        tripRepository.save(trip);
        return ResponseEntity.created(new URI("/back-sdk/trip/" + trip.getId())).body(trip);
    }
}
