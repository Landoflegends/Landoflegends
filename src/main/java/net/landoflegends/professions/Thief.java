package net.landoflegends.professions;

import net.landoflegends.LandOfLegends;
import net.landoflegends.data.ThiefData;

// Developer Notes:
// Another non-combat option being considered.  Thief related skills such as:
// Lockpick, Pickpocket, Breaking & Entering, Disguise and other thief related
// Skills - Should be a High-Risk, High Danger but High Potential Reward skillset.
// Please also see my non-combat profession notes in Profession.java, destroy or cut&paste these after reading.
// -dionnsai

public class Thief implements Profession {
    @SuppressWarnings("unused")
    private final LandOfLegends plugin;
    private final ThiefData data;

    public Thief(LandOfLegends plugin) {
        this.plugin = plugin;

        this.data = new ThiefData(plugin);

        plugin.getProfessionsManager().addProfession(this);
    }

    public String getName() {
        return "Thief";
    }

    public ThiefData getData() {
        return data;
    }
}
