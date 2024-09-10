package io.levysworks.lootmanager.blockdrops;

import dev.lone.itemsadder.api.CustomStack;
import io.levysworks.lootmanager.Lootmanager;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static io.levysworks.lootmanager.Lootmanager.*;

public class BlockBreakEventListener implements Listener {
    public static Random random = new Random();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if (!blocks_is_enabled) {
            return;
        }

        for (BlockDrop block : Lootmanager.blockDrops) {
            Material sourceMaterial = Material.valueOf(block.sourceBlock.toUpperCase());
            int amount = 1;

            try {
                amount = random.nextInt(block.min, block.max);
            } catch (IllegalArgumentException e) {
                Lootmanager.getPlugin(Lootmanager.class).getLogger().warning(
                        String.format("[Blocks] Maximum amount (%s) cannot be smaller than the minimum amount (%s). Defaulting to 1", block.max, block.min)
                );
            }

            event.setDropItems(false);
            if (event.getBlock().getType() == sourceMaterial) {
                if (random.nextInt(100) < block.override_chance) {
                    Location brokenBlockLoc = event.getBlock().getLocation();
                    World currentWorld = event.getBlock().getWorld();

                    event.setDropItems(false);
                    ItemStack tempStack = block.itemStack;

                    if (tempStack != null) {
                        tempStack.setAmount(amount);
                        currentWorld.dropItem(brokenBlockLoc, tempStack);
                    } else {
                        Lootmanager.getPlugin(Lootmanager.class).getLogger().severe(
                                String.format("[Blocks] There was an error while building an item, check the spellings in your BlockDrops.yml")
                        );
                        event.setDropItems(true);
                    }

                } else {
                    event.setDropItems(true);
                }
            }
        }
    }
}
