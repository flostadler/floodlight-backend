package de.flostadler.floodlight.repository;

import de.flostadler.floodlight.data.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TeamRepository extends MongoRepository<Team, String> {
}
