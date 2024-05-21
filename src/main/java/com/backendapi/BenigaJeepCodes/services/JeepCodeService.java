package com.backendapi.BenigaJeepCodes.services;

import com.backendapi.BenigaJeepCodes.models.JeepCode;
import com.backendapi.BenigaJeepCodes.models.Place;
import com.backendapi.BenigaJeepCodes.repositories.JeepCodeRepo;
import com.backendapi.BenigaJeepCodes.repositories.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JeepCodeService {

    private final JeepCodeRepo jeepCodeRepo;
    private final PlaceRepo placeRepo;

    @Autowired
    public JeepCodeService(JeepCodeRepo jeepCodeRepo, PlaceRepo placeRepo) {
        this.jeepCodeRepo = jeepCodeRepo;
        this.placeRepo = placeRepo;
    }

    public List<Place> getPlacesByJeepCode(String code) {
        JeepCode jeepCode = jeepCodeRepo.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid jeep code: " + code));
        return jeepCode.getPlaces();
    }

    public JeepCode addJeepCode(JeepCode jeepCode) {
        return jeepCodeRepo.save(jeepCode);
    }

    public List<JeepCode> getAllJeepCodes() {
        return jeepCodeRepo.findAll();
    }

    public JeepCode addPlaceToJeepCode(String code, Place place) {
        JeepCode jeepCode = jeepCodeRepo.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid jeep code: " + code));
        jeepCode.getPlaces().add(place);
        return jeepCodeRepo.save(jeepCode);
    }

    public JeepCode removePlaceFromJeepCode(String code, Place place) {
        JeepCode jeepCode = jeepCodeRepo.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid jeep code: " + code));
        jeepCode.getPlaces().removeIf(p -> p.getName().equals(place.getName()));
        return jeepCodeRepo.save(jeepCode);
    }

    public void initializeData() {
        Map<String, List<String>> data = new HashMap<>();
        data.put("01A", Arrays.asList("Alpha", "Bravo", "Charlie", "Echo", "Golf"));
        data.put("02B", Arrays.asList("Alpha", "Delta", "Echo", "Foxtrot", "Golf"));
        data.put("03C", Arrays.asList("Charlie", "Delta", "Foxtrot", "Hotel", "India"));
        data.put("04A", Arrays.asList("Charlie", "Delta", "Echo", "Foxtrot", "Golf"));
        data.put("04D", Arrays.asList("Charlie", "Echo", "Foxtrot", "Golf", "India"));
        data.put("06B", Arrays.asList("Delta", "Hotel", "Juliet", "Kilo", "Lima"));
        data.put("06D", Arrays.asList("Delta", "Foxtrot", "Golf", "India", "Kilo"));
        data.put("10C", Arrays.asList("Foxtrot", "Golf", "Hotel", "India", "Juliet"));
        data.put("10H", Arrays.asList("Foxtrot", "Hotel", "Juliet", "Lima", "November"));
        data.put("11A", Arrays.asList("Foxtrot", "Golf", "Kilo", "Mike", "November"));
        data.put("11B", Arrays.asList("Foxtrot", "Golf", "Lima", "Oscar", "Papa"));
        data.put("20A", Arrays.asList("India", "Juliet", "November", "Papa", "Romeo"));
        data.put("20C", Arrays.asList("India", "Kilo", "Lima", "Mike", "Romeo"));
        data.put("42C", Arrays.asList("Juliet", "Kilo", "Lima", "Mike", "Oscar"));
        data.put("42D", Arrays.asList("Juliet", "November", "Oscar", "Quebec", "Romeo"));

        List<String> allPlaces = Arrays.asList(
                "Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot",
                "Golf", "Hotel", "India", "Juliet", "Kilo", "Lima",
                "Mike", "November", "Oscar", "Papa", "Quebec", "Romeo"
        );

        if (placeRepo.count() == 0) {
            allPlaces.forEach(placeName -> {
                Place place = new Place(placeName);
                placeRepo.save(place);
            });
        }

        if (jeepCodeRepo.count() == 0) {
            data.forEach((code, places) -> {
                JeepCode jeepCode = new JeepCode();
                jeepCode.setCode(code);

                List<Place> placeList = new ArrayList<>();
                places.forEach(placeName -> {
                    Place place = placeRepo.findById(placeName).orElseThrow(() ->
                            new IllegalArgumentException("Invalid place name: " + placeName));
                    placeList.add(place);
                });

                jeepCode.setPlaces(placeList);
                jeepCodeRepo.save(jeepCode);
            });
        }
    }
}