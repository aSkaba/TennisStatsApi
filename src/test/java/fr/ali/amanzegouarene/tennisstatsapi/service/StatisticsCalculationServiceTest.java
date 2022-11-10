package fr.ali.amanzegouarene.tennisstatsapi.service;

import fr.ali.amanzegouarene.tennisstatsapi.exceptions.InternalServerErrorErrorException;
import fr.ali.amanzegouarene.tennisstatsapi.exceptions.ResourceNotFoundException;
import fr.ali.amanzegouarene.tennisstatsapi.model.Country;
import fr.ali.amanzegouarene.tennisstatsapi.model.Data;
import fr.ali.amanzegouarene.tennisstatsapi.model.Player;
import fr.ali.amanzegouarene.tennisstatsapi.model.Statistics;
import fr.ali.amanzegouarene.tennisstatsapi.util.DataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsCalculationServiceTest {

    private MockedStatic<DataUtil> dataUtilMockedStatic;

    private StatisticsCalculationService statisticsCalculationService;

    @BeforeEach
    void setUp() {
        statisticsCalculationService = new StatisticsCalculationService();
        dataUtilMockedStatic = Mockito.mockStatic(DataUtil.class);
    }

    @AfterEach
    void clodeStatics(){
        dataUtilMockedStatic.close();
    }

    @Test
    void givenListOfPlayers_whenProcessCalculation_shouldCalculateHeightPLayersMedian() {
        //Given
        List<Player> players = List.of(new Player(1,"un",null,null,null,null,null,
                        new Data(20, null,null,180,null,null)),
                new Player(2,"deux",null,null,null,null,null,
                        new Data(10, null,null,174,null,null)),
                new Player(3,"trois",null,null,null,null,null,
                        new Data(10, null,null,170,null,null)));
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenReturn(players);

        //When
        Statistics statistics = statisticsCalculationService.processStatsCalculation();

        //Then
        assertNotNull(statistics);
        assertEquals(174, statistics.getHeightPlayersMedian());
    }
    @Test
    void givenListOfPlayers_whenProcessCalculation_shouldCalculateIMCPLayersAverage() {
        //Given
        List<Player> players = List.of(new Player(1,"un",null,null,null,null,null,
                        new Data(20, null,80000,180,null,null)),
                new Player(2,"deux",null,null,null,null,null,
                        new Data(10, null,72000,174,null,null)),
                new Player(3,"trois",null,null,null,null,null,
                        new Data(10, null,70000,170,null,null)));
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenReturn(players);

        //When
        Statistics statistics = statisticsCalculationService.processStatsCalculation();

        //Then
        assertNotNull(statistics);
        assertEquals(24.23, statistics.getPlayersIMCAverage());
    }

    @Test
    void givenListOfPlayers_whenProcessCalculation_shouldReturnBestCountryRatio() {
        //Given
        List<Player> players = List.of(new Player(1,"un",null,null,null,new Country("USA.png","USA"),null,
                        new Data(20, null,80000,180,null,List.of(1, 0, 0, 0, 1))),
                new Player(2,"deux",null,null,null,new Country("FR.png","FR"),null,
                        new Data(10, null,72000,174,null,List.of(1, 0, 1, 0, 1))),
                new Player(3,"trois",null,null,null,new Country("UK.png","UK"),null,
                        new Data(10, null,70000,170,null,List.of(1, 0, 0, 0, 0))));
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenReturn(players);

        //When
        Statistics statistics = statisticsCalculationService.processStatsCalculation();

        //Then
        assertNotNull(statistics);
        assertNotNull(statistics.getCountryWithBestRatio());
        assertEquals("FR", statistics.getCountryWithBestRatio().getCode());
    }

    @Test
    void givenNoDataFile_whenProcessCalculation_shouldThrownInternalErrorException() {
        //Given
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenThrow(new IOException("Error retrieving data!"));

        //When, Then
        InternalServerErrorErrorException iee = assertThrows(InternalServerErrorErrorException.class, () -> statisticsCalculationService.processStatsCalculation());
        assertEquals("An error occurred while trying to retrieve data, please contact your service provider"
                , iee.getMessage());
    }

    @Test
    void givenNoPlayerInData_whenProcessCalculation_shouldThrownResourceNotFoundException() {
        //Given
        dataUtilMockedStatic.when(DataUtil::getPlayers).thenReturn(null);

        //When, Then
        ResourceNotFoundException iee = assertThrows(ResourceNotFoundException.class, () -> statisticsCalculationService.processStatsCalculation());
        assertEquals("Data not found"
                , iee.getMessage());
    }
}