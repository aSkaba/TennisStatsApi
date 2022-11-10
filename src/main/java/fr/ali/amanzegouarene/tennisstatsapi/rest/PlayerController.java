package fr.ali.amanzegouarene.tennisstatsapi.rest;

import fr.ali.amanzegouarene.tennisstatsapi.exceptions.ResourceNotFoundException;
import fr.ali.amanzegouarene.tennisstatsapi.model.Player;
import fr.ali.amanzegouarene.tennisstatsapi.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tennis-stats/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public List<Player> getPlayers() {
        return playerService.getPlayersOrderedByRank()
                .orElseThrow(() -> new ResourceNotFoundException("No data found !"));
    }

    @GetMapping("/player/{id}")
    public Player getPlayerById(@PathVariable Integer id) {
        return playerService.getPlayerById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Player not found with id "+id));
    }
}
