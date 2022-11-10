package fr.ali.amanzegouarene.tennisstatsapi.service;

import fr.ali.amanzegouarene.tennisstatsapi.exceptions.InternalServerErrorErrorException;
import fr.ali.amanzegouarene.tennisstatsapi.model.Player;
import fr.ali.amanzegouarene.tennisstatsapi.util.DataUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    public Optional<List<Player>> getPlayersOrderedByRank(){
        List<Player> players = null;
        try {
            players = DataUtil.getPlayers();
        } catch (IOException e) {
            throw new InternalServerErrorErrorException("An error occurred while trying to retrieve data, please contact your service provider");
        }
        players = Collections.unmodifiableList(players.stream()
                .sorted(Comparator.comparingInt(p -> p.getData() != null ? p.getData().getRank() : Integer.MAX_VALUE))
                .toList());
        return Optional.of(players);
    }

    public Optional<Player> getPlayerById(Integer playerId)  {
        if (playerId==null) {
            return Optional.ofNullable(null);
        }
        try {
            return DataUtil.getPlayers()
                    .stream()
                    .filter(p -> Integer.compare(p.getId(), playerId)==0)
                    .findFirst();
        } catch (IOException e) {
            throw new InternalServerErrorErrorException("An error occurred while trying to retrieve data, please contact your service provider");
        }

    }
}
