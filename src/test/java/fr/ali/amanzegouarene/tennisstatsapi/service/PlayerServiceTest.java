package fr.ali.amanzegouarene.tennisstatsapi.service;

import fr.ali.amanzegouarene.tennisstatsapi.exceptions.InternalServerErrorErrorException;
import fr.ali.amanzegouarene.tennisstatsapi.model.Data;
import fr.ali.amanzegouarene.tennisstatsapi.model.Player;
import fr.ali.amanzegouarene.tennisstatsapi.util.DataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest {

    private PlayerService servicePlayer;
    private MockedStatic<DataUtil> dataUtilMockedStatic;

    @BeforeEach
    void setUp() {
        servicePlayer = new PlayerService();
        dataUtilMockedStatic = Mockito.mockStatic(DataUtil.class);
    }

    @AfterEach
    void closeStat(){
        dataUtilMockedStatic.close();
    }

    @Test
    void givenNonOrderedPlayers_whenGetPlayersOrderedByRank_shouldReturnOrderedPlayersList() {
        // Given
        List<Player> players = List.of(new Player(1,"un",null,null,null,null,null,
                new Data(20, null,null,null,null,null)),
                new Player(1,"deux",null,null,null,null,null,
                        new Data(10, null,null,null,null,null)));
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenReturn(players);

        // When
        List<Player> result = servicePlayer.getPlayersOrderedByRank().isPresent()?
                servicePlayer.getPlayersOrderedByRank().get(): null;

        //Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("deux", result.get(0).getFirstname());
    }

    @Test
    void givenPlayers_whenGetPlayerById_shouldReturnThePlayer() {
        // Given
        List<Player> players = List.of(new Player(1,"un",null,null,null,null,null,
                new Data(20, null,null,null,null,null)),
                new Player(2,"deux",null,null,null,null,null,
                        new Data(10, null,null,null,null,null)));
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenReturn(players);

        // When
        Optional<Player> player = servicePlayer.getPlayerById(2);

        //Then
        assertTrue(player.isPresent());
        assertEquals("deux", player.get().getFirstname());
    }

    @Test
    void givenPlayers_whenGetPlayerByNonExistingPlayerId_shouldReturnNull() {
        // Given
        List<Player> players = List.of(new Player(1,"un",null,null,null,null,null,
                new Data(20, null,null,null,null,null)),
                new Player(2,"deux",null,null,null,null,null,
                        new Data(10, null,null,null,null,null)));
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenReturn(players);

        // When
        Optional<Player> player = servicePlayer.getPlayerById(3);

        //Then
        assertFalse(player.isPresent());
    }

    @Test
    void givenPlayers_whenGetPlayerByNullId_shouldReturnNull() {
        // Given
        List<Player> players = List.of(new Player(1,"un",null,null,null,null,null,
                new Data(20, null,null,null,null,null)),
                new Player(2,"deux",null,null,null,null,null,
                        new Data(10, null,null,null,null,null)));
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenReturn(players);

        // When
        Optional<Player> player = servicePlayer.getPlayerById(null);

        //Then
        assertFalse(player.isPresent());
    }

    @Test
    void givenNoDataFile_whenGetPlayersOrderedByRank_shouldThrownInternalErrorException() {
        //Given
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenThrow(new IOException("Error retrieving data!"));

        //When, Then
        InternalServerErrorErrorException iee = assertThrows(InternalServerErrorErrorException.class, () -> servicePlayer.getPlayersOrderedByRank());
        assertEquals("An error occurred while trying to retrieve data, please contact your service provider"
                , iee.getMessage());

    }

    @Test
    void givenNoDataFile_whenGetPlayerById_shouldThrownInternalErrorException() {
        //Given
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenThrow(new IOException("Error retrieving data!"));

        //When, Then
        InternalServerErrorErrorException iee = assertThrows(InternalServerErrorErrorException.class, () -> servicePlayer.getPlayerById(1));
        assertEquals("An error occurred while trying to retrieve data, please contact your service provider"
                , iee.getMessage());

    }
}