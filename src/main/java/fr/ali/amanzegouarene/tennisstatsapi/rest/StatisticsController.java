package fr.ali.amanzegouarene.tennisstatsapi.rest;

import fr.ali.amanzegouarene.tennisstatsapi.model.Statistics;
import fr.ali.amanzegouarene.tennisstatsapi.service.StatisticsCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tennis-stats/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {

    @Autowired
    private StatisticsCalculationService statService;

    @GetMapping()
    public Statistics calculateStatistics(){
        return statService.processStatsCalculation();
    }
}
