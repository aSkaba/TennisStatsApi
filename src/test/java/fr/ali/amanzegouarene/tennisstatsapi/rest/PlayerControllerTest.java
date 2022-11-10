package fr.ali.amanzegouarene.tennisstatsapi.rest;

import fr.ali.amanzegouarene.tennisstatsapi.exceptions.InternalServerErrorErrorException;
import fr.ali.amanzegouarene.tennisstatsapi.model.Data;
import fr.ali.amanzegouarene.tennisstatsapi.model.Player;
import fr.ali.amanzegouarene.tennisstatsapi.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
@AutoConfigureJsonTesters
class PlayerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JacksonTester<List<Player>> listJacksonTester;

    @Autowired
    JacksonTester<Player> playerJacksonTester;

    @MockBean
    PlayerService playerServiceMock;

    @BeforeEach
    void setUp() {
    }

    @Test
    void givenListOfPlayers_whenCallGetPlayers_shouldReturnTheListOfPlayers() throws Exception {
        // Given
        List<Player> players = List.of(new Player(), new Player());
        when(playerServiceMock.getPlayersOrderedByRank()).thenReturn(Optional.ofNullable(players));

        mockMvc.perform( //When
                get("/tennis-stats/api/players").accept(MediaType.APPLICATION_JSON_VALUE)
        )       // Then
                .andExpect(status().isOk())
                .andExpect(content().json(listJacksonTester.write(players).getJson()));
    }

    @Test
    void givenEmptyListOfPlayers_whenCallGetPlayers_shouldRespondDataNotFound() throws Exception {
        // Given
        when(playerServiceMock.getPlayersOrderedByRank()).thenReturn(Optional.ofNullable(null));

        //When
        MockHttpServletResponse response = mockMvc.perform(
                        get("/tennis-stats/api/players").accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn()
                .getResponse();


        //Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("No data found !", response.getContentAsString());
    }


    @Test
    void givenThrowenException_whenCallGetPlayers_shouldRespondInternalError() throws Exception {
        // Given
        String internal_error = "Internal Error";
        when(playerServiceMock.getPlayersOrderedByRank()).thenThrow(new InternalServerErrorErrorException(internal_error));

        //When
        MockHttpServletResponse response = mockMvc.perform(
                        get("/tennis-stats/api/players").accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(internal_error, response.getContentAsString());
    }

    @Test
    void givenNonExistingPlayerID_whenCallGetPlayerWithID_shouldRespondDataNotFound() throws Exception {
        // Given
        when(playerServiceMock.getPlayerById(any())).thenReturn(Optional.ofNullable(null));

        //When
        MockHttpServletResponse response = mockMvc.perform(
                        get("/tennis-stats/api/player/1").accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn()
                .getResponse();


        //Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Player not found with id 1", response.getContentAsString());
    }


    @Test
    void givenErroWhenLoadingData_whenCallGetPlayerWithAnID_shouldRespondDataNotFound() throws Exception {
        // Given
        String internal_error = "Internal Error";
        when(playerServiceMock.getPlayerById(any())).thenThrow(new InternalServerErrorErrorException(internal_error));

        //When
        MockHttpServletResponse response = mockMvc.perform(
                        get("/tennis-stats/api/player/1").accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(internal_error, response.getContentAsString());
    }

    @Test
    void givenPlayers_whenCallGetPlayerWithExistingPlayerId_shouldReturnPlayer() throws Exception {
        //Given
        Player un = new Player(1, "un", null, null, null, null, null,
                new Data(20, null, null, null, null, null));
        when(playerServiceMock.getPlayerById(1)).thenReturn(Optional.of(un));

        //When
        mockMvc.perform(
                        get("/tennis-stats/api/player/1").accept(MediaType.APPLICATION_JSON)
                )
                //Then
                .andExpect(status().isOk())
                .andExpect(content().json(playerJacksonTester.write(un).getJson()));

    }
}