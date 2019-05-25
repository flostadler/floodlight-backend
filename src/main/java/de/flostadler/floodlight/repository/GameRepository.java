package de.flostadler.floodlight.repository;

import de.flostadler.floodlight.data.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GameRepository  extends MongoRepository<Game, String> {



}
