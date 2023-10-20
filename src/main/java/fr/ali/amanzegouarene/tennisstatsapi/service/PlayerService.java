package fr.ali.amanzegouarene.tennisstatsapi.service;

import fr.ali.amanzegouarene.tennisstatsapi.entity.PlayerEntity;
import fr.ali.amanzegouarene.tennisstatsapi.exceptions.InternalServerErrorErrorException;
import fr.ali.amanzegouarene.tennisstatsapi.model.Player;
import fr.ali.amanzegouarene.tennisstatsapi.repos.PlayerEntityRepo;
import fr.ali.amanzegouarene.tennisstatsapi.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class PlayerService {

    @Autowired
    PlayerEntityRepo playerRepository;

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

    public PlayerEntity createPlayer(PlayerEntity player) {
        playerRepository.save(player);
        return player;
    }

    public List<PlayerEntity> getOfficialPlayers() throws IOException {
        List<PlayerEntity> officialPlayers = new ArrayList<>();

        List<String> playerNameList = DataUtil.getPlayers()
                .stream()
                .map(Player::getFirstname)
                .distinct()
                .toList();

        for (String s : playerNameList) {
            PlayerEntity playerEntity = playerRepository.findByFirstName(s);
            if (playerEntity!=null) {
                playerEntity.setOfficialPlayer(true);
                officialPlayers.add(playerEntity);
            }
        }

        return  officialPlayers;
    }
}
