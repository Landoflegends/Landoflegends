package net.landoflegends.professions;

import net.landoflegends.LandOfLegends;
import net.landoflegends.data.WoodcuttingData;

public class Woodcutting implements Profession {
    @SuppressWarnings("unused")
    private final LandOfLegends plugin;
    private final WoodcuttingData data;

    public Woodcutting(LandOfLegends plugin) {
        this.plugin = plugin;

        this.data = new WoodcuttingData(plugin);

        plugin.getProfessionsManager().addProfession(this);
    }

    public String getName() {
        return "Woodcutting";
    }

    public WoodcuttingData getData() {
        return data;
    }
}
