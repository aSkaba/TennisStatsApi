package fr.ali.amanzegouarene.tennisstatsapi.service;

import fr.ali.amanzegouarene.tennisstatsapi.exceptions.InternalServerErrorErrorException;
import fr.ali.amanzegouarene.tennisstatsapi.exceptions.ResourceNotFoundException;
import fr.ali.amanzegouarene.tennisstatsapi.model.Country;
import fr.ali.amanzegouarene.tennisstatsapi.model.Player;
import fr.ali.amanzegouarene.tennisstatsapi.model.Statistics;
import fr.ali.amanzegouarene.tennisstatsapi.util.DataUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class StatisticsCalculationService {

    public Statistics processStatsCalculation(){
        try {
            List<Player> players = DataUtil.getPlayers();
            if(CollectionUtils.isEmpty(players)){
                throw new ResourceNotFoundException("Data not found");
            }
            return new Statistics(
                    getCountryWithBestRatio(players),
                    calculatePlayersIMCAverage(players),
                    calculateHeightPlayersMedian(players)
            );

        } catch (IOException e) {
            throw new InternalServerErrorErrorException("An error occurred while trying to retrieve data, please contact your service provider");
        }
    }

    private Integer calculateHeightPlayersMedian(List<Player> players) {
        int[] heights =  players.stream()
                .filter(p->p.getData()!=null)
                .mapToInt(p->p.getData().getHeight())
                .toArray();
        Arrays.sort(heights);
        int n = heights.length;

        return n %2==0 ? (heights[n/2-1]+heights[n/2-1])/2
                        : heights[n/2];
    }

    private double calculatePlayersIMCAverage(List<Player> players) {

        Double average = players.stream()
                .filter(player ->
                        player.getData()!=null && player.getData().getWeight()!=null
                                && player.getData().getHeight()!=null)
                .collect(Collectors.averagingDouble(p ->
                        //IMC = Kg/m^2 = 10*(g/cm^2)
                        10 * p.getData().getWeight().doubleValue() / (p.getData().getHeight()*p.getData().getHeight())));

        return Math.round(average*100)/100d;
    }

    private Country getCountryWithBestRatio(List<Player> players) {
        return players.stream()
                .filter(p -> p.getData()!=null && !CollectionUtils.isEmpty(p.getData().getLast()))
                .max((p1, p2) ->
                        Double.compare(
                                (double)p1.getData().getLast().stream().reduce(0, (a,b)->a+b)
                                / p1.getData().getLast().size()
                                ,
                                (double)p2.getData().getLast().stream().mapToInt(Integer::intValue).sum()
                                /p2.getData().getLast().size())
                ).orElseGet(() -> {
                    Player player = new Player();
                    player.setCountry(new Country("",""));
                    return player;
                })
                .getCountry();
    }
}
