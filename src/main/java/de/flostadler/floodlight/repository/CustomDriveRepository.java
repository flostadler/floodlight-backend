package de.flostadler.floodlight.repository;

import de.flostadler.floodlight.data.Drive;
import de.flostadler.floodlight.data.Location;

public interface CustomDriveRepository {
    Iterable<Drive> findDrivesForGameByLocation(Location location, String gameId);

    Iterable<Drive> findDrivesForParticipant(String username);
}
