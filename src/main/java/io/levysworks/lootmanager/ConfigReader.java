package io.levysworks.lootmanager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigReader {
    private File file;
    private File filepath;
    private final JavaPlugin plugin = Lootmanager.getPlugin(Lootmanager.class);
    private final String name;
    private final String path;
    private FileConfiguration config;

    public void saveDefaultConfig() {
        if (!filepath.exists()) {
            boolean success = filepath.mkdirs();
            if (!success)
                Lootmanager.getPlugin(Lootmanager.class).getLogger().severe("[Main] Error creating the config. Please try again.");
        }
        if (!file.exists())
            this.plugin.saveResource(path + name, false);
    }

    public FileConfiguration getConfig() {
        if (!filepath.exists())
            this.saveDefaultConfig();
        if (config == null)
            this.reloadConfig();
        return config;
    }

    public void reloadConfig() {
        if (filepath == null)
            filepath = new File(plugin.getDataFolder(), path);
        if (file == null)
            file = new File(filepath, name);
        config = YamlConfiguration.loadConfiguration(file);
        InputStream stream = plugin.getResource(name);
        if (stream != null) {
            YamlConfiguration YmlFile = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
            config.setDefaults(YmlFile);
        }
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (Throwable t) {
            Lootmanager.getPlugin(Lootmanager.class).getLogger().severe("[Main] Error saving the config. Please try again.");
        }
    }

    public ConfigReader(String pathname, String filename) {
        this.path = pathname;
        this.name = filename;
        this.filepath = new File(plugin.getDataFolder(), path);
        this.file = new File(filepath, name);
    }

    public static void save(ConfigReader config) {
        config.saveConfig();
        config.reloadConfig();
    }
}