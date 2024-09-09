package io.levysworks.lootmanager;

import io.levysworks.lootmanager.piglintrades.PiglinTradeListener;
import io.levysworks.lootmanager.piglintrades.ReloadConfig;
import io.levysworks.lootmanager.piglintrades.TradeItems;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lootmanager extends JavaPlugin {
    public static boolean piblin_is_enabled = true;
    public static boolean piglin_log = true;
    public static boolean piglin_override_vanilla = true;

    public static TradeItems trades = new TradeItems();

    @Override
    public void onEnable() {
        saveDefaultConfig(); // TODO: Default config changed, trying to read it will fail. FIX!

        ReloadConfig.reloadPluginConfig_Piglin();

        this.getCommand("lootreload").setExecutor(new ReloadCommandExecutor());
        this.getCommand("lootreload").setTabCompleter(new ReloadCommandExecutor());

        getServer().getPluginManager().registerEvents(new PiglinTradeListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
