package de.flostadler.floodlight.repository;

import de.flostadler.floodlight.data.Drive;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DriveRepository extends MongoRepository<Drive, String>, CustomDriveRepository {

    Iterable<Drive> findDrivesByDriverMapping_UserUsername(String username);

}
