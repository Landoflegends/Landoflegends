package net.landoflegends.professions;

import net.landoflegends.LandOfLegends;
import net.landoflegends.data.MysticData;

// Developer Notes:
// The Mystic class will be a non-combat skill class option.
// Rather than mana, mysticism will consume a reagent, in most cases redstone dust (redstone).
// Mark (16) and Recall (32) (teleportation) skills modeled similar to the Heroes Skill.
// Recall should also have a configurable delay to prevent knee-jerk pvp escape.
// Group(party) summon (16+16 per member) to current location.
// Enchanting - Rather than consuming levels or xp, enchanting should consume
//              the reagent based on the level of the enchant, available enchants
//              calculated based on mysticism skill level.  Redstone consumption:
//              (1) per level 1-10 (2) for 11-20 (3) for 21-30 (4) for 31-40 (5) for 41-50.
//              I.E. Lv 43 enchant consumes 10+20+30+40+15 redstone for 115 redstone.
// Disenchanting (8)
// Enchanted item repair (difficulty based on mystic level and enchant levels/complexity?)
//              Chance of failure and disenchant.
// Only class which can craft the enchanting table, books and maps.
// Other skill ideas are welcome, but as it is a noncombat choice they need to be
// Carefully balanced for combination with the combat options.
// Skill gain should be based on the above skills mostly, especially enchanting, with
// XP growing as enchant level grows (possibly grant xp based on the level of enchant
//    plus bonuses based on the type and complexity of enchants received.
// Please also see my non-combat profession notes in Profession.java, destroy or cut&paste these after reading.
// -dionnsai

public class Mystic implements Profession {
    @SuppressWarnings("unused")
    private final LandOfLegends plugin;
    private final MysticData data;

    public Mystic(LandOfLegends plugin) {
        this.plugin = plugin;

        this.data = new MysticData(plugin);

        plugin.getProfessionsManager().addProfession(this);
    }

    public String getName() {
        return "Mystic";
    }

    public MysticData getData() {
        return data;
    }
}
