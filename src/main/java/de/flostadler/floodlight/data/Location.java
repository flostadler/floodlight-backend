package de.flostadler.floodlight.data;

import com.google.maps.model.LatLng;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Location {
    private double longitude;
    private double latitude;

    public LatLng toLatLng() {
        return new LatLng(latitude, longitude);
    }

    public static Location fromLatLng(LatLng latLng) {
        return new Location(latLng.lng, latLng.lat);
    }
}
