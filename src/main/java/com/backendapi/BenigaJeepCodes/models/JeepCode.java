package com.backendapi.BenigaJeepCodes.models;

import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "jeepcode")
public class JeepCode {
    @Id
    @Pattern(regexp = "\\d{2}[A-Z]", message = "Invalid jeep code")
    private String code;

    @ManyToMany
    @JoinTable(
            name = "routes",
            joinColumns = @JoinColumn(name = "code"),
            inverseJoinColumns = @JoinColumn(name = "place")
    )
    private List<Place> places;

    public JeepCode() { }

    public JeepCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "JeepCode{" +
                "code='" + code + '\'' +
                ", places=" + places +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, places);
    }
}
