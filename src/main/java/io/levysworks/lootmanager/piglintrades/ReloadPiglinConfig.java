package io.levysworks.lootmanager.piglintrades;

import io.levysworks.lootmanager.Lootmanager;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

import static io.levysworks.lootmanager.Lootmanager.*;
import static io.levysworks.lootmanager.Lootmanager.piglin_log;
import static io.levysworks.lootmanager.Lootmanager.PiglinTradesConfig;

public class ReloadPiglinConfig {

    private static final JavaPlugin plugin = Lootmanager.getPlugin(Lootmanager.class);

    @SuppressWarnings("deprecation")
    public static void reloadPluginConfig_Piglin() {
        PiglinTradesConfig.saveDefaultConfig();

        trades.emptyTradeItemCache();
        PiglinTradesConfig.reloadConfig();

        piglin_is_enabled = PiglinTradesConfig.getConfig().getBoolean("enabled");
        piglin_log = PiglinTradesConfig.getConfig().getBoolean("log");
        piglin_override_vanilla = PiglinTradesConfig.getConfig().getBoolean("override_vanilla");

        plugin.getLogger().info(String.format("[Bartering] Piglin bartering enabled: %s", piglin_is_enabled));

        if (!piglin_override_vanilla) {
            VanillaTrades.addVanillaTrades(trades);
            plugin.getLogger().info("[Bartering] Vanilla trades are enabled, building them...");
        }

        ConfigurationSection tradesSection = PiglinTradesConfig.getConfig().getConfigurationSection("trades");

        if (tradesSection == null) {
            plugin.getLogger().info("[Bartering] Trades section not found in config!");
            return;
        }

        List<Map<?, ?>> vanillaTrades = tradesSection.getMapList("vanilla_trades");
        List<Map<?, ?>> oraxenTrades = tradesSection.getMapList("oraxen_trades");
        List<Map<?, ?>> mmoitemsTrades = tradesSection.getMapList("mmoitems_trades");

        if (!vanillaTrades.isEmpty()) {
            for (Map<?, ?> tradeMap : vanillaTrades) {
                for (Map.Entry<?, ?> entry : tradeMap.entrySet()) {
                    String key = entry.getKey().toString();
                    Map<?, ?> trade = (Map<?, ?>) entry.getValue();

                    int min_amount = (int) trade.get("min_amount");
                    int max_amount = (int) trade.get("max_amount");
                    int chance = (int) trade.get("chance");

                    if (min_amount < 0) {
                        min_amount *= -1;
                    }

                    try {
                        trades.items.put(new ItemStack(Material.valueOf(key.toUpperCase()), 1), new ItemData(chance, min_amount, max_amount));
                    } catch (IllegalArgumentException e) {
                        if (piglin_log) {
                            plugin.getLogger().warning(String.format("[Bartering] Couldn't find item with name %s", key));
                        }
                    }
                }
            }
        } else if (piglin_log) {
            plugin.getLogger().info("[Bartering] No vanilla trades found!");
        }

        if (compatibilityChecker.has_mmoitems) {
            if (!mmoitemsTrades.isEmpty()) {
                for (Map<?, ?> tradeMap : mmoitemsTrades) {
                    for (Map.Entry<?, ?> entry : tradeMap.entrySet()) {
                        String key = entry.getKey().toString();
                        Map<?, ?> trade = (Map<?, ?>) entry.getValue();

                        String itemID = (String) trade.get("item");

                        int min_amount = (int) trade.get("min_amount");
                        int max_amount = (int) trade.get("max_amount");
                        int chance = (int) trade.get("chance");

                        if (min_amount < 0) {
                            min_amount *= -1;
                        }

                        ItemStack mmoItem = MMOItems.plugin.getItems().getItem(MMOItems.plugin.getTypes().get(key), itemID);
                        if (mmoItem != null) {
                            trades.items.put(mmoItem, new ItemData(chance, min_amount, max_amount));
                        } else {
                            plugin.getLogger().warning(String.format("[Bartering] Couldn't build mmoitem with ID: %s and Category: %s", itemID, key));
                        }
                    }
                }
            } else if (piglin_log) {
                plugin.getLogger().info("[Bartering] No mmoitems trades found.");
            }
        } else if (piglin_log) {
            plugin.getLogger().warning("[Bartering] Skipping MMOItems trades section.");
        }

        if (compatibilityChecker.has_oraxen) {
            if (!oraxenTrades.isEmpty()) {
                for (Map<?, ?> tradeMap : oraxenTrades) {
                    for (Map.Entry<?, ?> entry : tradeMap.entrySet()) {
                        String key = entry.getKey().toString();
                        Map<?, ?> trade = (Map<?, ?>) entry.getValue();

                        int min_amount = (int) trade.get("min_amount");
                        int max_amount = (int) trade.get("max_amount");
                        int chance = (int) trade.get("chance");

                        if (min_amount < 0) {
                            min_amount *= -1;
                        }

                        try {
                            ItemBuilder item = OraxenItems.getItemById(key);
                            if (item != null) {
                                item.setAmount(1);
                                ItemStack builtItem = item.build();
                                trades.items.put(builtItem, new ItemData(chance, min_amount, max_amount));
                            } else if (piglin_log) {
                                plugin.getLogger().warning(String.format("[Bartering] Couldn't build oraxen item with item ID: %s", key));
                            }
                        } catch (NullPointerException e) {
                            plugin.getLogger().severe("[Bartering] An error occurred while trying to retrieve an item from oraxen!");
                        }
                    }
                }
            } else if (piglin_log) {
                plugin.getLogger().info("[Bartering] No oraxen trades found.");
            }
        } else if (piglin_log) {
            plugin.getLogger().warning("[Bartering] Skipping Oraxen trades section.");
        }
    }
}
