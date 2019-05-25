package de.flostadler.floodlight;

import de.flostadler.floodlight.data.Location;
import de.flostadler.floodlight.repository.DriveRepository;
import de.flostadler.floodlight.service.GeoSpatialResolver;
import de.flostadler.floodlight.service.RouteBoxer;
import lombok.AllArgsConstructor;
import okhttp3.Route;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FloodlightApplicationTests {

    @Autowired
    private GeoSpatialResolver geoSpatialResolver;

    @Autowired
    private DriveRepository driveRepository;

    @Test
    public void contextLoads() throws Exception {
        var latitude = 48.8815948;
        var longitude = 12.577541;

        var location = new Location(longitude, latitude);

        var drive = driveRepository.findById("5ce879e9ae63377b7375f158");
        var drives = driveRepository.findDrivesForGameByLocation(location, "5ce81397ae6337957d6646e1");

        var contains = drive.get()
                .getRouteBox()
                .stream()
                .anyMatch(x -> x.contains(new RouteBoxer.LatLng(latitude, longitude)));

        int i = 10;
    }

}
