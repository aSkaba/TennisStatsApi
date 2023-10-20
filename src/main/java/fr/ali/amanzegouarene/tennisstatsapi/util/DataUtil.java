package fr.ali.amanzegouarene.tennisstatsapi.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.ali.amanzegouarene.tennisstatsapi.model.Player;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    private static final String DATA_FILE = "dataPlayers.json";
    private static List<Player> players;

    public static List<Player> getPlayers() throws IOException {
        if (!CollectionUtils.isEmpty(players)) {
            return players;
        }else{
            return retrieveData();
        }
    }

    private static List<Player> retrieveData() throws IOException {
        InputStream playerFileInputStream = new ClassPathResource(DATA_FILE).getInputStream();
        Gson gson = new Gson();
        PlayersWrapper playersWrapper = gson.fromJson(new InputStreamReader(playerFileInputStream), new TypeToken<PlayersWrapper>() {
        }.getType());
        return players = playersWrapper.getPlayers();
    }
    class PlayersWrapper {
        private List<Player> players;
        public PlayersWrapper(List<Player> players) {
            this.players = players;
        }
        public PlayersWrapper() {
        }
        public List<Player> getPlayers() {
            return players;
        }
        public void setPlayers(List<Player> players) {
            this.players = players;
        }
    }

}
