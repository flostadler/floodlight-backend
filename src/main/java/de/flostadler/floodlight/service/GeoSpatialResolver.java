package de.flostadler.floodlight.service;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import de.flostadler.floodlight.data.Location;
import de.flostadler.floodlight.service.RouteBoxer.LatLngBounds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GeoSpatialResolver {

    @Autowired
    private GeoApiContext geoApiContext;
    private RouteBoxer routeBoxer = new RouteBoxer();


    public List<Location> getRoute(long arrivalTime, Location origin, Location destination, Location... stops) throws IOException {
        var request = DirectionsApi.newRequest(geoApiContext)
                .arrivalTime(Instant.ofEpochMilli(arrivalTime))
                .origin(origin.toLatLng())
                .destination(destination.toLatLng())
                .optimizeWaypoints(true)
                .waypoints(Arrays.stream(stops).map(Location::toLatLng).toArray(LatLng[]::new))
                .mode(TravelMode.DRIVING);

        DirectionsRoute[] routes;

        try {
            routes = request.await().routes;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while querying google direction api for origin {}, destination {}", origin, destination, e);
            throw new IOException("Interrupted while querying google direction api!", e);
        } catch (ApiException e) {
            throw new IOException(e);
        }

        if (routes.length == 0) {
            throw new IOException("No Route Found!");
        }

        return routes[0].overviewPolyline.decodePath().stream()
                .map(Location::fromLatLng)
                .collect(Collectors.toList());
    }

    public Collection<LatLngBounds> boxRoute(List<Location> path, double meterRange) {
        return routeBoxer.box(convertToCustomLatLng(path), meterRange / 1000);
    }

    public Collection<LatLngBounds> boxRoute(long arrivalTime, Location origin, Location destination, double meterRange, Location... stops) throws IOException {
        return boxRoute(getRoute(arrivalTime, origin, destination, stops), meterRange);
    }

    private List<RouteBoxer.LatLng> convertToCustomLatLng(List<Location> path) {
        return path.stream().map(x -> new RouteBoxer.LatLng(x.getLatitude(), x.getLongitude())).collect(Collectors.toList());
    }
}
