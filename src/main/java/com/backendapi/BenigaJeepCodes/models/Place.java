package com.backendapi.BenigaJeepCodes.models;

import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "place")
public class Place {
    @Id
    private String name;

    public Place() { }

    public Place(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
