package br.com.gubee.interview.model.dto;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComparedHeroDto {
    private UUID id1;
    private UUID id2;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;

    public ComparedHeroDto(Hero heroi1, Hero heroi2, PowerStats pw1, PowerStats pw2) {
        this.id1 = heroi1.getId();
        this.id2 = heroi2.getId();
        this.strength = pw1.getStrength() - pw2.getStrength();
        this.agility = pw1.getAgility() - pw2.getAgility();
        this.dexterity = pw1.getDexterity() - pw2.getDexterity();
        this.intelligence = pw1.getIntelligence() - pw2.getIntelligence();
    }
}