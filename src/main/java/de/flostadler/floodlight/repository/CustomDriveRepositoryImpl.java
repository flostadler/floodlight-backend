package de.flostadler.floodlight.repository;

import de.flostadler.floodlight.data.Drive;
import de.flostadler.floodlight.data.Location;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@AllArgsConstructor
public class CustomDriveRepositoryImpl implements CustomDriveRepository {

    private MongoOperations mongoOperations;

    @Override
    public Iterable<Drive> findDrivesForGameByLocation(Location location, String gameId) {
        Query query = new Query();

        query.addCriteria(Criteria.where("game.$id").is(new ObjectId(gameId)).and("routeBox").elemMatch(
                Criteria
                        .where("southwest.lat").lt(location.getLatitude())
                        .and("southwest.lng").lt(location.getLongitude())
                        .and("northeast.lat").gt(location.getLatitude())
                        .and("northeast.lng").gt(location.getLongitude())
        ));

        query.with(new Sort(Sort.Direction.ASC, "arrivalDate"));

        return mongoOperations.find(query, Drive.class);
    }

    @Override
    public Iterable<Drive> findDrivesForParticipant(String username) {
        Query query = new Query();

        query.addCriteria(new Criteria().orOperator(
                Criteria.where("driverMapping.user.$id").is(username),
                Criteria.where("passengerMappings").elemMatch(Criteria.where("user.$id").is(username))
        ));

        query.with(new Sort(Sort.Direction.ASC, "arrivalDate"));

        return mongoOperations.find(query, Drive.class);
    }
}
