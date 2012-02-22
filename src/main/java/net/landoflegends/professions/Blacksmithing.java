package net.landoflegends.professions;

import net.landoflegends.LandOfLegends;
import net.landoflegends.data.SmithingData;

public class Blacksmithing implements Profession {
    @SuppressWarnings("unused")
    private final LandOfLegends plugin;
    private final SmithingData data;

    public Blacksmithing(LandOfLegends plugin) {
        this.plugin = plugin;

        this.data = new SmithingData(plugin);

        plugin.getProfessionsManager().addProfession(this);
    }

    public String getName() {
        return "Blacksmithing";
    }

    public SmithingData getData() {
        return data;
    }
}
