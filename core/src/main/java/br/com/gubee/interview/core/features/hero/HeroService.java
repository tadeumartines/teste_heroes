package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsRepository;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;
    private final PowerStatsRepository powerStatsRepository;

    public UUID createHero(CreateHeroRequest createHeroRequest) {
        PowerStats powerStats = new PowerStats(createHeroRequest);
        powerStats = powerStatsRepository.save(powerStats);
        Hero hero = mapperToHero(createHeroRequest, powerStats);
        hero = heroRepository.save(hero);
        return hero.getId();
    }

    private Hero mapperToHero(CreateHeroRequest createHeroRequest, PowerStats powerStats) {
        Hero hero = new Hero(createHeroRequest,powerStats.getId());
        return hero;
    }

    public List<CreateHeroRequest> findAllHeroes() {
        List<CreateHeroRequest> createHeroRequests = new ArrayList<>();
        List<Hero> heroes = heroRepository.findAll();
        heroes.stream().forEach(hero -> {
            Optional<PowerStats> powerStats = powerStatsRepository.findById(hero.getPowerStatsId());
            CreateHeroRequest heroRequest = mapperToCreateHeroRequest(hero,powerStats.get());
            createHeroRequests.add(heroRequest);
        });
        return createHeroRequests;
    }

    public ResponseEntity<Optional<CreateHeroRequest>>  getHeroById(UUID id) {
        try {
            Hero hero = heroRepository.findById(id).orElseThrow();
            Optional<PowerStats> powerStats = powerStatsRepository.findById(hero.getPowerStatsId());
            CreateHeroRequest createHeroRequest = mapperToCreateHeroRequest(hero,powerStats.get());
            return ResponseEntity.ok(Optional.ofNullable(createHeroRequest));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    public List<CreateHeroRequest>  getHeroByName(String name) {
        try {
            List<CreateHeroRequest> createHeroRequest = new ArrayList<>();
            List<Hero> heroes = (List<Hero>) heroRepository.findByName(name);
            heroes.stream().forEach(hero -> {
                Optional<PowerStats> powerStats = powerStatsRepository.findById(hero.getPowerStatsId());
                CreateHeroRequest heroRequest = mapperToCreateHeroRequest(hero,powerStats.get());
                createHeroRequest.add(heroRequest);
            });
           // Optional<PowerStats> powerStats = powerStatsRepository.findById(hero.getPowerStatsId());
            //CreateHeroRequest createHeroRequest = mapperToCreateHeroRequest(hero,powerStats.get());
            return createHeroRequest;
        } catch (Exception e) {
            return (List<CreateHeroRequest>) ResponseEntity.notFound().build();
        }

    }

    private CreateHeroRequest mapperToCreateHeroRequest(Hero hero, PowerStats powerStats) {
        return CreateHeroRequest.builder().id(hero.getId()).race(Race.valueOf(hero.getRace())).name(hero.getName())
                .agility(powerStats.getAgility())
                .intelligence(powerStats.getIntelligence())
                .dexterity(powerStats.getDexterity())
                .strength(powerStats.getStrength()).build();
    }

    public void deleteHeroById(UUID id) {
        heroRepository.deleteById(id);
    }

//    public CreateHeroRequest compareHeroesById(UUID id1, UUID id2) {
//        return heroRepository.compareHeroesById(id1, id2);
//    }
}