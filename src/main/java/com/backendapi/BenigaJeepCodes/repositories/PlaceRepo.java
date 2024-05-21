package com.backendapi.BenigaJeepCodes.repositories;

import com.backendapi.BenigaJeepCodes.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepo extends JpaRepository<Place, String> { }