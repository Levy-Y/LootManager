package io.levysworks.lootmanager.structureloot;

import io.levysworks.lootmanager.ItemProviders;
import io.levysworks.lootmanager.Lootmanager;
import io.levysworks.lootmanager.ProviderBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.levysworks.lootmanager.Lootmanager.StructureLootConfig;

public class ReloadStructureConfig {
    private static final Lootmanager pluginInstance = Lootmanager.getPlugin(Lootmanager.class);

    public static void reloadPluginConfig_Structure() {
        Lootmanager.structure_loadedItems = new ArrayList<>();
        StructureLootConfig.reloadConfig();

        Lootmanager.structure_enabled = StructureLootConfig.getConfig().getBoolean("enabled");
        pluginInstance.getLogger().info("[Structure] Custom structure loot enabled: " + Lootmanager.structure_enabled);

        FileConfiguration config = StructureLootConfig.getConfig();
        List<Map<?, ?>> structures = config.getMapList("structures");

        for (Map<?, ?> structure : structures) {
            for (Map.Entry<?, ?> entry : structure.entrySet()) {
                String structureName = (String) entry.getKey();
                List<Map<?, ?>> items = (List<Map<?, ?>>) entry.getValue();

                CustomLootContainer structureInstance = new CustomLootContainer();

                try {
                    structureInstance.lootTableName = LootTables.valueOf(structureName);
                } catch (IllegalArgumentException e) {
                    pluginInstance.getLogger().severe(String.format("[Structure] Cannot load loot into loot table of %s, as it is not a valid loot table.", structureName));
                }

                if (!items.isEmpty()) {
                    for (Map<?, ?> itemMap : items) {
                        for (Map.Entry<?, ?> itemEntry : itemMap.entrySet()) {
                            String itemName = (String) itemEntry.getKey();
                            Map<String, Object> itemDetails = (Map<String, Object>) itemEntry.getValue();

                            String type = (itemDetails.get("Type") != null ? (String) itemDetails.get("Type") : "VANILLA");
                            String o_category = null;
                            int amount = 1;

                            try {
                                amount = (Integer) itemDetails.get("Amount");
                            } catch (ClassCastException | NullPointerException e) {
                                if (e instanceof ClassCastException) {
                                    pluginInstance.getLogger().warning(String.format("[Structure] '%s' is not a valid amount for %s.", itemDetails.get("Amount"), itemName));
                                } else {
                                    pluginInstance.getLogger().warning(String.format("[Structure] amount not defined for %s.", itemName));
                                }
                                continue;
                            }

                            try {
                                o_category = (String) itemDetails.get("Category");
                            } catch (NullPointerException e) {
                                pluginInstance.getLogger().warning(String.format("[Structure] MMOItem category is missing for %s", itemName));
                            }

                            ItemStack providersItem = new ProviderBuilder().ProviderBuilder(ItemProviders.valueOf(type.toUpperCase()), itemName, amount, o_category);
                            if (providersItem != null) {
                                structureInstance.itemStacks.add(providersItem);
                            } else {
                                pluginInstance.getLogger().warning(String.format("[Structure] Couldn't build %s (%s) item.", itemName, type));
                            }

                        }
                    }

                    Lootmanager.structure_loadedItems.add(structureInstance);
                } else {
                    pluginInstance.getLogger().warning("[Structure] You must define at least one item in a structure's section, in the config!");
                }
            }
        }

        if (Lootmanager.structure_loadedItems.isEmpty()) {
            pluginInstance.getLogger().warning("[Structure] Either there are no custom loots in the config, or the 'structures' section is missing. This is not an error!");
        }

    }
}
