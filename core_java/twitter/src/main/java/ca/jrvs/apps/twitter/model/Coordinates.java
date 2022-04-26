package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coordinates{

    private List<Double> coordinates;
    private String type;

    public List<Double> getCoordinates() {
        return coordinates;
    }
//    public double getX() {
//        return coordinates.get(0);
//    }
//    public double getY() {
//        return coordinates.get(1);
//    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}