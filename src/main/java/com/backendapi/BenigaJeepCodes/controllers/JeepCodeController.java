package com.backendapi.BenigaJeepCodes.controllers;

import com.backendapi.BenigaJeepCodes.models.JeepCode;
import com.backendapi.BenigaJeepCodes.models.Place;
import com.backendapi.BenigaJeepCodes.services.JeepCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/jeepcodes")
@CrossOrigin
public class JeepCodeController {

    private final JeepCodeService jeepCodeService;

    @Autowired
    public JeepCodeController(JeepCodeService jeepCodeService) {
        this.jeepCodeService = jeepCodeService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<List<Place>> getPlacesByJeepCode(@PathVariable String code) {
        List<Place> places = jeepCodeService.getPlacesByJeepCode(code);
        return ResponseEntity.ok(places);
    }

    @PostMapping("/add")
    public ResponseEntity<JeepCode> addJeepCode(@RequestBody JeepCode jeepCode) {
        JeepCode newJeepCode = jeepCodeService.addJeepCode(jeepCode);
        return new ResponseEntity<>(newJeepCode, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<JeepCode>> getAllJeepCodes() {
        List<JeepCode> allJeepCodes = jeepCodeService.getAllJeepCodes();
        return ResponseEntity.ok(allJeepCodes);
    }

    @GetMapping("/multiple")
    public ResponseEntity<Map<String, Object>> getMultipleJeepRoutesByCode(@RequestParam List<String> codes) {
        Map<String, List<Place>> jeepRoutes = new HashMap<>();
        Map<Set<String>, Set<Place>> commonRoutesMap = new HashMap<>();

        for (String code : codes) {
            List<Place> places = jeepCodeService.getPlacesByJeepCode(code);
            jeepRoutes.put(code, places);
        }

        int n = codes.size();
        for (int i = 1; i < (1 << n); i++) {
            Set<String> subset = new HashSet<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subset.add(codes.get(j));
                }
            }

            if (subset.size() > 1) {
                Iterator<String> iterator = subset.iterator();
                Set<Place> commonPlaces = new HashSet<>(jeepRoutes.get(iterator.next()));
                while (iterator.hasNext()) {
                    commonPlaces.retainAll(jeepRoutes.get(iterator.next()));
                }
                if (!commonPlaces.isEmpty()) {
                    commonRoutesMap.put(subset, commonPlaces);
                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("jeepRoutes", jeepRoutes);
        response.put("commonRoutes", commonRoutesMap);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{code}/addPlace")
    public ResponseEntity<JeepCode> addPlaceToJeepCode(@PathVariable String code, @RequestBody Place place) {
        JeepCode updatedJeepCode = jeepCodeService.addPlaceToJeepCode(code, place);
        return ResponseEntity.ok(updatedJeepCode);
    }

    @DeleteMapping("/{code}/removePlace")
    public ResponseEntity<JeepCode> removePlaceFromJeepCode(@PathVariable String code, @RequestBody Place place) {
        JeepCode updatedJeepCode = jeepCodeService.removePlaceFromJeepCode(code, place);
        return ResponseEntity.ok(updatedJeepCode);
    }
}