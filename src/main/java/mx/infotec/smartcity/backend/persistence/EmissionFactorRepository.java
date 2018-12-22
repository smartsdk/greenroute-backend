package mx.infotec.smartcity.backend.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mx.infotec.smartcity.backend.model.EmissionFactor;

public interface EmissionFactorRepository extends MongoRepository<EmissionFactor, String> {

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<EmissionFactor> findByName(String name);
    
    @Query(value = "{'transportMode': {$regex : ?0, $options: 'i'}}")
    List<EmissionFactor> findByTransportMode(String transportMode);
    
    List<EmissionFactor> findById(String id);
    
    @Query("{ 'name' : ?0, 'transportMode': ?0 }")
    List<EmissionFactor> findByNameTransportMode(String name, String transportMode);
}
