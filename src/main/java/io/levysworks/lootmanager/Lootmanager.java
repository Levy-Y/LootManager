package io.levysworks.lootmanager;

// Piglin trades
import io.levysworks.lootmanager.blockdrops.BlockDrop;
import io.levysworks.lootmanager.blockdrops.ReloadBlocksConfig;
import io.levysworks.lootmanager.piglintrades.PiglinTradeListener;
import io.levysworks.lootmanager.piglintrades.ReloadPiglinConfig;
import io.levysworks.lootmanager.piglintrades.TradeItems;
//

// Block drops
import io.levysworks.lootmanager.blockdrops.BlockBreakEventListener;
//

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Lootmanager extends JavaPlugin {
    // Compatibility checker
    public static CompatibilityChecker providers = new CompatibilityChecker();
    //

    // Separated config file readers
    public static ConfigReader BlockDropsConfig;
    public static ConfigReader PiglinTradesConfig;
    public static ConfigReader StructureLootConfig;
    //

    // Piglin trade module
    public static boolean piglin_is_enabled = true;
    public static boolean piglin_log = true;
    public static boolean piglin_override_vanilla = true;
    public static TradeItems trades = new TradeItems();
    //

    // Block drop class module
    public static boolean blocks_is_enabled = true;
    public static boolean blocks_log = true;
    public static List<BlockDrop> blockDrops = new ArrayList<>();
    //

    @Override
    public void onEnable() {
        providers.checkForPlugins();

        // Separated config readers
        BlockDropsConfig = new ConfigReader("modules/","BlockDrops.yml");
        PiglinTradesConfig = new ConfigReader("modules/","PiglinTrades.yml");
        StructureLootConfig = new ConfigReader("modules/","StructureLoot.yml");

        // Save default configs
        BlockDropsConfig.saveDefaultConfig();
        PiglinTradesConfig.saveDefaultConfig();
        StructureLootConfig.saveDefaultConfig();

        // Load config files
        ReloadPiglinConfig.reloadPluginConfig_Piglin();
        ReloadBlocksConfig.reloadPluginConfig_Blocks();

        // Register command, with tab complete
        this.getCommand("lootreload").setExecutor(new ReloadCommandExecutor());
        this.getCommand("lootreload").setTabCompleter(new ReloadCommandExecutor());

        // Register event listeners for each "module"
        getServer().getPluginManager().registerEvents(new PiglinTradeListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakEventListener(), this);
    }

    @Override
    public void onDisable() {
        // Save each config file
        ConfigReader.save(BlockDropsConfig);
        ConfigReader.save(PiglinTradesConfig);
        ConfigReader.save(StructureLootConfig);
        //
    }
}
