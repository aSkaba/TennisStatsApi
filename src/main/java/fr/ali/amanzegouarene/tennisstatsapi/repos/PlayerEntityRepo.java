package fr.ali.amanzegouarene.tennisstatsapi.repos;

import fr.ali.amanzegouarene.tennisstatsapi.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlayerEntityRepo extends CrudRepository<PlayerEntity, Long> {
    PlayerEntity findByFirstName(String s);
}
