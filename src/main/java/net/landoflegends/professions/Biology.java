package net.landoflegends.professions;

import net.landoflegends.LandOfLegends;
import net.landoflegends.data.BiologyData;

public class Biology implements Profession {
    @SuppressWarnings("unused")
    private final LandOfLegends plugin;
    private final BiologyData data;

    public Biology(LandOfLegends plugin) {
        this.plugin = plugin;

        this.data = new BiologyData(plugin);

        plugin.getProfessionsManager().addProfession(this);
    }

    public String getName() {
        return "Biology";
    }

    public BiologyData getData() {
        return data;
    }
}
