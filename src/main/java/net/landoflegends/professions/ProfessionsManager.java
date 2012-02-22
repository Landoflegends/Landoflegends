package net.landoflegends.professions;

import java.util.HashSet;
import java.util.Set;

import net.landoflegends.LandOfLegends;

public class ProfessionsManager {
    @SuppressWarnings("unused")
    private final LandOfLegends plugin;
    private Set<Profession> professions;

    public ProfessionsManager(LandOfLegends plugin) {
        this.plugin = plugin;

        this.professions = new HashSet<Profession>();
    }

    public Profession getProfession(String name) {
        for (Profession p : professions) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    protected void addProfession(Profession p) {
        professions.add(p);
    }
}
