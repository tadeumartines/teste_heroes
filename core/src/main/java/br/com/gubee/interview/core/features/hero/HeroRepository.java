package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HeroRepository extends JpaRepository<Hero, UUID> {


//    CreateHeroRequest getHeroById(UUID id);
//    void deleteHeroById(UUID id);

//    @Query("SELECT new br.com.gubee.interview.model.request.CreateHeroRequest(h1.name, h1.power, h1.speed, h1.abilities) " +
//            "FROM CreateHeroRequest h1, CreateHeroRequest h2 " +
//            "WHERE h1.id = :id1 AND h2.id = :id2")
//    Hero compareHeroesById(UUID id1, UUID id2);

    public List<Hero> findByName(String name);
}