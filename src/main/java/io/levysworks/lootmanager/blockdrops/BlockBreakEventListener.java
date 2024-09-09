package io.levysworks.lootmanager.blockdrops;

import dev.lone.itemsadder.api.CustomStack;
import io.levysworks.lootmanager.Lootmanager;
import io.levysworks.lootmanager.piglintrades.ItemData;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import net.Indyuce.mmoitems.MMOItems;
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
            Material sourceMaterial;
            int amount = 1;

            try {
                sourceMaterial = Material.valueOf(block.sourceBlock.toUpperCase());
            } catch (IllegalArgumentException e) {
                if (blocks_log) {
                    Lootmanager.getPlugin(Lootmanager.class).getLogger().warning(String.format("[Blocks] %s is not a valid block type.", block.sourceBlock));
                }
                continue;
            }

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

                    switch (block.itemProvider) {
                        case VANILLA -> {
                            try {
                                currentWorld.dropItem(
                                        brokenBlockLoc,
                                        new ItemStack(
                                                Material.valueOf(block.dropName.toUpperCase()),
                                                amount
                                                )
                                        );
                            } catch (IllegalArgumentException e) {
                                if (blocks_log) {
                                    Lootmanager.getPlugin(Lootmanager.class).getLogger().warning(String.format("[Blocks] %s isn't a valid %s item.", block.dropName, block.itemProvider.toString()));
                                }
                                event.setDropItems(true);
                            }
                        }
                        case ORAXEN -> {
                            if (!providers.has_oraxen) {
                                return;
                            }

                            ItemBuilder itemBuilder = OraxenItems.getItemById(block.dropName);
                            ItemStack builtItem = itemBuilder.build();

                            if (builtItem != null) {
                                builtItem.setAmount(amount);
                                currentWorld.dropItem(brokenBlockLoc, builtItem);
                            } else if (blocks_log) {
                                Lootmanager.getPlugin(Lootmanager.class).getLogger().warning(String.format("[Blocks] Couldn't build oraxen item, with ID %s", block.dropName));
                                event.setDropItems(true);
                            }
                        }

                        // TODO: No MMOItem category option in the config yet, add it!
//                        case MMOITEMS -> {
//                            if (!providers.has_mmoitems) {
//                                return;
//                            }
//
//                            ItemStack mmoItem = MMOItems.plugin.getItems().getItem(MMOItems.plugin.getTypes().get(), itemID);
//                            if (mmoItem != null) {
//                                trades.items.put(mmoItem, new ItemData(chance, min_amount, max_amount));
//                            } else {
//                                plugin.getLogger().warning(String.format("Couldn't build mmoitem with ID: %s and Category: %s", itemID, key));
//                            }
//                        }
                        case ITEMSADDER -> {
                            if (!providers.has_itemsadder) {
                                return;
                            }

                            CustomStack stack = CustomStack.getInstance(block.dropName);
                            if (stack != null) {
                                ItemStack itemStack = stack.getItemStack();
                                itemStack.setAmount(amount);

                                currentWorld.dropItem(brokenBlockLoc, itemStack);
                            } else {
                                Lootmanager.getPlugin(Lootmanager.class).getLogger().warning(
                                        String.format("Cannot build ItemsAdder item, with namespaced ID %s", block.dropName)
                                );
                                event.setDropItems(true);
                            }
                        }
                    }

                } else {
                    event.setDropItems(true);
                }
            }
        }
    }
}
