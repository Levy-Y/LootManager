package io.levysworks.lootmanager.blockdrops;

import io.levysworks.lootmanager.ItemProviders;
import io.levysworks.lootmanager.Lootmanager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.levysworks.lootmanager.Lootmanager.*;

public class ReloadBlocksConfig {
    public static final JavaPlugin plugin = Lootmanager.getPlugin(Lootmanager.class);

    public static void reloadPluginConfig_Blocks() {
        BlockDropsConfig.saveDefaultConfig();

        List<BlockDrop> reloadedBlockList = new ArrayList<>();
        BlockDropsConfig.reloadConfig();

        if (BlockDropsConfig.getConfig().isConfigurationSection("blocks")) {
            for (String block : BlockDropsConfig.getConfig().getConfigurationSection("blocks").getKeys(false)) {
                List<Map<?, ?>> itemList = BlockDropsConfig.getConfig().getMapList("blocks." + block);

                for (Map<?, ?> item : itemList) {
                    for (Map.Entry<?, ?> entry : item.entrySet()) {
                        String itemName = (String) entry.getKey();
                        Map<?, ?> itemProperties = (Map<?, ?>) entry.getValue();

                        String providerType = (String) itemProperties.get("type");
                        int min = (int) itemProperties.get("min");
                        int max = (int) itemProperties.get("max");
                        int overrideChance = (int) itemProperties.get("override_chance");

                        ItemProviders provider = ItemProviders.valueOf(providerType);

                        BlockDrop blockDrop = new BlockDrop(block, itemName, provider, min, max, overrideChance);
                        reloadedBlockList.add(blockDrop);
                    }
                }
            }
            blockDrops = reloadedBlockList;
        } else if (blocks_log) {
            plugin.getLogger().warning("[Blocks] No blocks section found in the BlockDrops.yml file.");
        }
    }
}
