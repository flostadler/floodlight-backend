package de.flostadler.floodlight.controller;

import de.flostadler.floodlight.data.Game;
import de.flostadler.floodlight.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/game", produces = "application/json")
public class GameController {

    private GameRepository gameRepository;

    @GetMapping("/getAllSorted")
    public ResponseEntity<Iterable<Game>> getAllGamesSorted() {
        return ResponseEntity.ok(gameRepository.findAll(new Sort(Sort.Direction.ASC, "date")));
    }
}
