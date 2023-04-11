package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HeroRepository extends JpaRepository<Hero, UUID> {

    public List<Hero> findByName(String name);
}