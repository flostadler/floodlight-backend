package de.flostadler.floodlight.repository;

import de.flostadler.floodlight.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository  extends MongoRepository<User, String> {
}
