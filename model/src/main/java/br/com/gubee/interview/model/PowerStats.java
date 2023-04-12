package br.com.gubee.interview.model;

import br.com.gubee.interview.model.dto.HeroDto;
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
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "power_stats", schema = "interview_service")
public class PowerStats {

    @Id
    @Column(name = "id")
    private UUID id= UUID.randomUUID();

    @Column(name = "strength")
    private int strength;

    @Column(name = "agility")
    private int agility;

    @Column(name = "dexterity")
    private int dexterity;

    @Column(name = "intelligence")
    private int intelligence;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PowerStats(HeroDto heroDto) {
        this.strength = heroDto.getStrength();
        this.agility = heroDto.getAgility();
        this.dexterity = heroDto.getDexterity();
        this.intelligence = heroDto.getIntelligence();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}