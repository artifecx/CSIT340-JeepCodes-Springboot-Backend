package com.backendapi.BenigaJeepCodes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendapi.BenigaJeepCodes.models.JeepCode;

public interface JeepCodeRepo extends JpaRepository<JeepCode, String>{ }
