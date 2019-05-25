package de.flostadler.floodlight.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.flostadler.floodlight.service.RouteBoxer.LatLngBounds;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Document
public class Drive {

    @Id
    private String id;

    private int seats;

    private UserLocationMapping driverMapping;

    @DBRef
    private Game game;

    private Set<UserLocationMapping> passengerMappings;

    private double meterRange;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date arrivalDate;

    private Location destination;

    @JsonIgnore
    private Collection<LatLngBounds> routeBox;

    public void addPassengerMapping(User user, Location location) {
        if (passengerMappings == null) {
            passengerMappings = new HashSet<>();
        }

        passengerMappings.add(new UserLocationMapping(user, location));
    }
}
