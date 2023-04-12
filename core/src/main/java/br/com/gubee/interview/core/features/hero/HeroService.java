package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsRepository;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.dto.ComparedHeroDto;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.dto.HeroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;
    private final PowerStatsRepository powerStatsRepository;

    public UUID createHero(HeroDto heroDto) {
        PowerStats powerStats = new PowerStats(heroDto);
        powerStats = powerStatsRepository.save(powerStats);
        Hero hero = mapperToHero(heroDto, powerStats);
        hero = heroRepository.save(hero);
        return hero.getId();
    }

    private Hero mapperToHero(HeroDto heroDto, PowerStats powerStats) {
        Hero hero = new Hero(heroDto,powerStats.getId());
        return hero;
    }

    public List<HeroDto> findAllHeroes() {
        List<HeroDto> heroDtos = new ArrayList<>();
        List<Hero> heroes = heroRepository.findAll();
        heroes.stream().forEach(hero -> {
            Optional<PowerStats> powerStats = powerStatsRepository.findById(hero.getPowerStatsId());
            HeroDto heroRequest = mapperToCreateHeroRequest(hero,powerStats.get());
            heroDtos.add(heroRequest);
        });
        return heroDtos;
    }

    public ResponseEntity<Optional<HeroDto>>  getHeroById(UUID id) {
        try {
            Hero hero = heroRepository.findById(id).orElseThrow();
            Optional<PowerStats> powerStats = powerStatsRepository.findById(hero.getPowerStatsId());
            HeroDto heroDto = mapperToCreateHeroRequest(hero,powerStats.get());
            return ResponseEntity.ok(Optional.ofNullable(heroDto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    public List<HeroDto>  getHeroByName(String name) {
        try {
            List<HeroDto> heroDto = new ArrayList<>();
            List<Hero> heroes = (List<Hero>) heroRepository.findByName(name.toUpperCase());
            heroes.stream().forEach(hero -> {
                Optional<PowerStats> powerStats = powerStatsRepository.findById(hero.getPowerStatsId());
                HeroDto heroRequest = mapperToCreateHeroRequest(hero,powerStats.get());
                heroDto.add(heroRequest);
            });
            return heroDto;
        } catch (Exception e) {
            return (List<HeroDto>) ResponseEntity.notFound().build();
        }

    }

    private HeroDto mapperToCreateHeroRequest(Hero hero, PowerStats powerStats) {
        return HeroDto.builder().id(hero.getId()).race(Race.valueOf(hero.getRace())).name(hero.getName())
                .agility(powerStats.getAgility())
                .intelligence(powerStats.getIntelligence())
                .dexterity(powerStats.getDexterity())
                .strength(powerStats.getStrength()).build();
    }

    public ResponseEntity<Void> deleteHeroById(UUID id) {
        try {
            heroRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> updateHero(UUID heroId, HeroDto heroDto) throws ChangeSetPersister.NotFoundException {

        try {
            Hero hero = heroRepository.findById(heroId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
            PowerStats powerStats = powerStatsRepository.findById(hero.getPowerStatsId()).orElseThrow(() -> new ChangeSetPersister.NotFoundException());


            hero.setName(Objects.nonNull(heroDto.getName()) ? heroDto.getName() : hero.getName());
            hero.setRace(Objects.nonNull(heroDto.getRace()) ? String.valueOf(heroDto.getRace()) : hero.getRace());
            powerStats.setStrength(heroDto.getStrength());
            powerStats.setAgility(heroDto.getAgility());
            powerStats.setDexterity(heroDto.getDexterity());
            powerStats.setIntelligence(heroDto.getIntelligence());

            powerStatsRepository.save(powerStats);
            heroRepository.save(hero);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }


    }


    public ComparedHeroDto compareHeroes(UUID hero1, UUID hero2) throws ChangeSetPersister.NotFoundException {
        Hero heroi1 = heroRepository.findById(hero1).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        PowerStats powerStats1 = powerStatsRepository.findById(heroi1.getPowerStatsId()).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        Hero heroi2 = heroRepository.findById(hero2).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        PowerStats powerStats2 = powerStatsRepository.findById(heroi2.getPowerStatsId()).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        ComparedHeroDto comparedHeroDto = new ComparedHeroDto(heroi1, heroi2, powerStats1, powerStats2);
        return comparedHeroDto;
    }
}