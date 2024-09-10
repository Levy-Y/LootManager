package io.levysworks.lootmanager.blockdrops;

import org.bukkit.inventory.ItemStack;

public class BlockDrop {
    public String sourceBlock;
    public ItemStack itemStack;
    public int min;
    public int max;
    public int override_chance;

    public BlockDrop(String sourceBlock, ItemStack itemStack, int min, int max, int override_chance) {
        this.sourceBlock = sourceBlock;
        this.itemStack = itemStack;
        this.min = min;
        this.max = max;
        this.override_chance = override_chance;
    }
}
