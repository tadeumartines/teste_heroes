package br.com.gubee.interview.model;

import br.com.gubee.interview.model.dto.HeroDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Entity
@Table(name = "hero", schema = "interview_service")
public class Hero {

    @Id
    @Column(name = "id")
    private UUID id= UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "race")
    private String race;

    @Column(name = "power_stats_id")
    private UUID powerStatsId;

    @Column(name = "created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @Column(name = "enabled")
    private boolean enabled;


    public Hero(HeroDto heroDto, UUID powerStatsId) {
        this.name = heroDto.getName();
        this.race = String.valueOf(heroDto.getRace());
        this.enabled = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.powerStatsId = powerStatsId;
    }
}