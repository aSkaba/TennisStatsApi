package fr.ali.amanzegouarene.tennisstatsapi;

import fr.ali.amanzegouarene.tennisstatsapi.service.PlayerService;
import fr.ali.amanzegouarene.tennisstatsapi.service.StatisticsCalculationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TennisStatsApiApplicationTests {


	@Test
	void contextLoads(ApplicationContext applicationContext) {
		assertNotNull(applicationContext);
		assertNotNull(applicationContext.getBean(PlayerService.class));
		assertNotNull(applicationContext.getBean(StatisticsCalculationService.class));
	}

}
