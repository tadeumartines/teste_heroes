package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PowerStatsRepository extends JpaRepository<PowerStats, UUID> {



//    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
//            " (strength, agility, dexterity, intelligence)" +
//            " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";
//
//    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    UUID create(PowerStats powerStats) {
//        return namedParameterJdbcTemplate.queryForObject(
//                CREATE_POWER_STATS_QUERY,
//                new BeanPropertySqlParameterSource(powerStats),
//                UUID.class);
//    }
}