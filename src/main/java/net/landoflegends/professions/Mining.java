package net.landoflegends.professions;

import java.util.Random;

import net.landoflegends.LandOfLegends;
import net.landoflegends.data.MiningData;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Mining implements Profession {

    @SuppressWarnings("unused")
    private final LandOfLegends plugin;
    private final MiningData data;

    public Mining(LandOfLegends plugin) {
        this.plugin = plugin;

        data = new MiningData(plugin);

        plugin.getProfessionsManager().addProfession(this);
    }

    public MiningData getData() {
        return data;
    }

    public String getName() {
        return "Mining";
    }

    public boolean canUse(Player player) {
        Validate.notNull(player, "Player cannot be null!");

        int level = data.getLevel(player);
        Material inHand = player.getItemInHand().getType();
        if (level < 8) {
            if (inHand == Material.STONE_PICKAXE) {
                return false;
            }
            if (inHand == Material.IRON_PICKAXE) {
                return false;
            }
            if (inHand == Material.DIAMOND_PICKAXE) {
                return false;
            }
            if (inHand == Material.GOLD_PICKAXE) {
                return false;
            }
        } else if (level < 15) {
            if (inHand == Material.IRON_PICKAXE) {
                return false;
            }
            if (inHand == Material.DIAMOND_PICKAXE) {
                return false;
            }
            if (inHand == Material.GOLD_PICKAXE) {
                return true;
            }
        } else if (level < 25) {
            if (inHand == Material.DIAMOND_PICKAXE) {
                return false;
            }
            if (inHand == Material.GOLD_PICKAXE) {
                return false;
            }
        } else if (level < 50) {
            if (inHand == Material.GOLD_PICKAXE) {
                return false;
            }
        }
        return true;
    }

    public void dropExtra(Location loc, Player player) {
        Validate.notNull(loc, "Location cannot be null!");
        Validate.notNull(player, "Player cannot be null!");

        int level = data.getLevel(player);
        World world = loc.getWorld();
        Random random = LandOfLegends.RANDOM;
        if ((random.nextInt(100) + 1) <= (((level - 5) / 4) + 6)) {
            ItemStack item = null;
            int chance = random.nextInt(100);
            if (chance < 2) {
                int i = random.nextInt(3);
                int i2 = random.nextInt(3);
                if (i2 != 3) {
                    if (i == 0) {
                        item = new ItemStack(Material.IRON_HELMET, 1);
                    } else if (i == 1) {
                        item = new ItemStack(Material.IRON_CHESTPLATE, 1);
                    } else if (i == 2) {
                        item = new ItemStack(Material.IRON_LEGGINGS, 1);
                    } else if (i == 3) {
                        item = new ItemStack(Material.IRON_BOOTS, 1);
                    }
                } else {
                    if (i == 0) {
                        item = new ItemStack(Material.LEATHER_HELMET, 1);
                    } else if (i == 1) {
                        item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
                    } else if (i == 2) {
                        item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
                    } else if (i == 3) {
                        item = new ItemStack(Material.LEATHER_BOOTS, 1);
                    }
                }
            } else if (chance < 4) {
                item = new ItemStack(Material.NETHERRACK, 1);
            } else if (chance < 7) {
                int i = random.nextInt(2);
                if (i == 0) {
                    item = new ItemStack(Material.WOOD_PICKAXE, 1);
                } else if (i == 1) {
                    item = new ItemStack(Material.STONE_PICKAXE, 1);
                } else if (i == 2) {
                    item = new ItemStack(Material.IRON_PICKAXE, 1);
                }
            } else if (chance < 15) {
                item = new ItemStack(Material.LAPIS_BLOCK, 1);
            } else if (chance < 45) {
                item = new ItemStack(Material.REDSTONE, (random.nextInt(5) + 2));
            } else {
                item = new ItemStack(Material.BONE, (random.nextInt(2) + 1));
            }
            // msgplayer.itemDropped(player);
            world.dropItemNaturally(loc, item);
        }
    }
}
