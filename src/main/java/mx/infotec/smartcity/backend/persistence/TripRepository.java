/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.trip.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author yolanda.baca
 */
public interface TripRepository extends MongoRepository<Trip, String>{
    
    public List<Trip> findAllById(List<String> listId, Pageable pageable);

    public List<Trip> findAllByRefUser(String refUser);
    
    public Page<Trip> findAllByOrderByTimeDesc(Pageable pageable);
    
    //public List<Trip> findAllByOrderByDateDesc();
    
    public List<Trip> findById(String id);
}