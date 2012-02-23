// Professions Notes:
// Non-Combat professions should be granted to players on a "points" basis.
// New Players are automatically granted 4 Non-Combat Points.
// New Players are automatically granted Mining and Woodcutting skills.
// New Players use the remaining points to choose from the other skills.
// Points Distribution:
// Mining and Woodcutting: 1 Point Each
// All others: 2 Points Each
// Players can choose to give up any non-combat skill in exchange for the points.
// I.E. A player could give up Mining and Woodcutting in order to gain one of the
// other skill options.
// Points should be stored in the player data files, and extra points should be
// awardable for donations or other reasons.
// -dionnsai
// Please also see my non-combat profession notes in Mystic.java, destroy or cut&paste these after reading.
// -dionnsai  Server roadmap: 

package net.landoflegends.professions;

import net.landoflegends.data.Data;

public interface Profession {

    public Data getData();

    public String getName();

}
