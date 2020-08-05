package com.air_tickets.repositories;

import com.air_tickets.entities.Aircrafts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface AircraftRepository extends JpaRepository<Aircrafts,Long> {
    ArrayList<Aircrafts> findAll();

}
