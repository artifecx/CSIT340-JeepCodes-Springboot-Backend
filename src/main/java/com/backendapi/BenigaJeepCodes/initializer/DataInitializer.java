package com.backendapi.BenigaJeepCodes.initializer;

import com.backendapi.BenigaJeepCodes.services.JeepCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final JeepCodeService jeepCodeService;

    @Autowired
    public DataInitializer(JeepCodeService jeepCodeService) {
        this.jeepCodeService = jeepCodeService;
    }

    @Override
    public void run(String... args) throws Exception {
        jeepCodeService.initializeData();
    }
}