package io.levysworks.lootmanager.blockdrops;

import io.levysworks.lootmanager.ItemProviders;
import org.bukkit.Material;

public class BlockDrop {
    public String sourceBlock;
    public String dropName;
    public ItemProviders itemProvider;
    public int min;
    public int max;
    public int override_chance;

    public BlockDrop(String sourceBlock, String dropName, ItemProviders itemProvider, int min, int max, int override_chance) {
        this.dropName = dropName;
        this.sourceBlock = sourceBlock;
        this.itemProvider = itemProvider;
        this.min = min;
        this.max = max;
        this.override_chance = override_chance;
    }
}
