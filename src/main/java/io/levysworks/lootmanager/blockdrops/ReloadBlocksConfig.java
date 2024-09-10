package io.levysworks.lootmanager.blockdrops;

import io.levysworks.lootmanager.ItemProviders;
import io.levysworks.lootmanager.Lootmanager;
import io.levysworks.lootmanager.ProviderBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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

        blocks_is_enabled = BlockDropsConfig.getConfig().getBoolean("enabled");
        plugin.getLogger().info(String.format("[Blocks] Custom block drops enabled: %s", blocks_is_enabled));

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

                        try {
                            Material.valueOf(block.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            plugin.getLogger().warning(String.format("Block drop couldn't be added, as %s is not a valid source block", block));
                            continue;
                        }

                        // TODO: MMOITems is not yet supported
                        ItemStack builtItemStack = new ProviderBuilder().ProviderBuilder(provider, itemName, 1, null);

                        BlockDrop blockDrop = new BlockDrop(block, builtItemStack, min, max, overrideChance);
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
