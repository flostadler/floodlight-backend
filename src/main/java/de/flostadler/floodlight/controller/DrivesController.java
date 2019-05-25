package de.flostadler.floodlight.controller;

import de.flostadler.floodlight.data.Drive;
import de.flostadler.floodlight.data.Location;
import de.flostadler.floodlight.data.UserLocationMapping;
import de.flostadler.floodlight.repository.DriveRepository;
import de.flostadler.floodlight.repository.GameRepository;
import de.flostadler.floodlight.repository.UserRepository;
import de.flostadler.floodlight.service.GeoSpatialResolver;
import de.flostadler.floodlight.service.RouteBoxer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/drive", produces = "application/json")
public class DrivesController {

    private GameRepository gameRepository;
    private UserRepository userRepository;
    private DriveRepository driveRepository;

    private GeoSpatialResolver geoSpatialResolver;
    private RouteBoxer routeBoxer = new RouteBoxer();

    @PostMapping("/create")
    public ResponseEntity<Drive> addDrive(
            @RequestParam String username,
            @RequestParam String gameId,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double meterRange,
            @RequestParam int seats
    ) throws IOException {
        if (!userRepository.existsById(username) || !gameRepository.existsById(gameId))
            return ResponseEntity.notFound().build();

        var user = userRepository.findById(username).orElseThrow();
        var game = gameRepository.findById(gameId).orElseThrow();
        var userLocation = new Location(longitude, latitude);
        var arrivalDate = game.getDate();
        var destination = game.getHomeTeam().getLocation();

        var drive = Drive.builder()
                .game(game)
                .seats(seats)
                .driverMapping(new UserLocationMapping(user, userLocation))
                .routeBox(geoSpatialResolver.boxRoute(arrivalDate.getTime(), userLocation, destination, meterRange))
                .arrivalDate(arrivalDate)
                .meterRange(meterRange)
                .destination(destination)
                .build();

        return ResponseEntity.ok(driveRepository.save(drive));
    }

    @GetMapping("getForUser")
    public ResponseEntity<Iterable<Drive>> getDrivesForUser(
            @RequestParam String username
    ) {
        if (!userRepository.existsById(username))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(driveRepository.findDrivesForParticipant(username));
    }

    @PostMapping("addPassenger")
    public ResponseEntity<Drive> addPassenger(
            @RequestParam String driveId,
            @RequestParam String username,
            @RequestParam double longitude,
            @RequestParam double latitude
    ) throws IOException {
        if (!driveRepository.existsById(driveId) || !userRepository.existsById(username))
            return ResponseEntity.notFound().build();

        var drive = driveRepository.findById(driveId).orElseThrow();
        var user = userRepository.findById(username).orElseThrow();

        var userLocation = new Location(longitude, latitude);

        drive.setRouteBox(
                geoSpatialResolver.boxRoute(
                        drive.getArrivalDate().getTime(),
                        drive.getDriverMapping().getLocation(),
                        drive.getDestination(),
                        drive.getMeterRange(),
                        userLocation
                )
        );

        drive.addPassengerMapping(user, userLocation);

        return ResponseEntity.ok(driveRepository.save(drive));
    }

    @GetMapping("getPossibleDrives")
    public ResponseEntity<Iterable<Drive>> getPossibleDrives(
            @RequestParam String gameId,
            @RequestParam double longitude,
            @RequestParam double latitude
    ) {
        if (!gameRepository.existsById(gameId))
            return ResponseEntity.notFound().build();

        var game = gameRepository.findById(gameId).orElseThrow();

        return ResponseEntity.ok(driveRepository.findDrivesForGameByLocation(new Location(longitude, latitude), game.getId()));
    }
}
