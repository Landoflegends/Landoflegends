package net.landoflegends.professions;

import net.landoflegends.LandOfLegends;
import net.landoflegends.data.HuntingData;

public class Hunting implements Profession {
    @SuppressWarnings("unused")
    private final LandOfLegends plugin;
    private final HuntingData data;

    public Hunting(LandOfLegends plugin) {
        this.plugin = plugin;

        data = new HuntingData(plugin);

        plugin.getProfessionsManager().addProfession(this);
    }

    public String getName() {
        return "Hunting";
    }

    public HuntingData getData() {
        return data;
    }
}
