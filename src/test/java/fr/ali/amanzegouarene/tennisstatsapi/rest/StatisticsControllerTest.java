package fr.ali.amanzegouarene.tennisstatsapi.rest;

import fr.ali.amanzegouarene.tennisstatsapi.exceptions.InternalServerErrorErrorException;
import fr.ali.amanzegouarene.tennisstatsapi.exceptions.ResourceNotFoundException;
import fr.ali.amanzegouarene.tennisstatsapi.model.Country;
import fr.ali.amanzegouarene.tennisstatsapi.model.Statistics;
import fr.ali.amanzegouarene.tennisstatsapi.service.StatisticsCalculationService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    @MockBean
    StatisticsCalculationService statisticsCalculationServiceMock;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JacksonTester<Statistics> jacksonTester;

    @Test
    void givenCalculatedStats_whenCallGetStats_shouldSuccessWithGoodData() throws Exception {
        //Given
        Statistics stats = new Statistics(new Country("pics","FR"),24.20,180);
        when(statisticsCalculationServiceMock.processStatsCalculation()).thenReturn(stats);

                //When
        mockMvc.perform(
                get("/tennis-stats/api/").accept(MediaType.APPLICATION_JSON)
        )
                //Then
                .andExpect(status().isOk())
                .andExpect(content().json(jacksonTester.write(stats).getJson()));
    }

    @Test
    void givenThrownInternalServerErrorOnStatsCalculation_whenCallGetStats_shouldHandleInternalServerErrorErrorException() throws Exception {
        //Given
        String error_handled = "Error handled";
        when(statisticsCalculationServiceMock.processStatsCalculation()).thenThrow(new InternalServerErrorErrorException(error_handled));

                //When
        MockHttpServletResponse response = mockMvc.perform(
                get("/tennis-stats/api/").accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(error_handled, response.getContentAsString());
    }
    @Test
    void givenThrownResourceNotFoundExceptionOnStatsCalculation_whenCallGetStats_shouldHandleResourceNotFoundException() throws Exception {
        //Given
        String error_handled = "Resource not found";
        when(statisticsCalculationServiceMock.processStatsCalculation()).thenThrow(new ResourceNotFoundException(error_handled));

        //When
        MockHttpServletResponse response = mockMvc.perform(
                get("/tennis-stats/api/").accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(error_handled, response.getContentAsString());
    }
}