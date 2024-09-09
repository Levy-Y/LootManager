package io.levysworks.lootmanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.levysworks.lootmanager.piglintrades.ReloadConfig.reloadPluginConfig_Piglin;

public class ReloadCommandExecutor implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lootreload")) {
            if (args.length == 0) {
                sender.sendMessage("Usage: /lootreload <all|piglin|structure>");
                return false;
            }

            if (args[0].equalsIgnoreCase("all") && sender.hasPermission("lootmanager.reload.all")) {
                reloadAllLoot();
                sender.sendMessage("LootManager: All loot tables have been reloaded.");
            } else if (args[0].equalsIgnoreCase("piglin") && sender.hasPermission("lootmanager.reload.piglin")) {
                reloadPluginConfig_Piglin();
                sender.sendMessage("LootManager: Piglin loot tables have been reloaded.");
            } else if (args[0].equalsIgnoreCase("structure") && sender.hasPermission("lootmanager.reload.structure")) {
                // Reload config of the structure loot tables
                // TODO: Add reload structure loot table config method here
                sender.sendMessage("LootManager: Structure loot tables have been reloaded.");
            } else {
                sender.sendMessage("Invalid argument. Usage: /lootreload <all|piglin|structure>");
                return false;
            }

            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("lootreload")) {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                List<String> options = Arrays.asList("all", "piglin", "structure");

                for (String option : options) {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase())) {
                        completions.add(option);
                    }
                }

                return completions;
            }
        }
        return List.of();
    }

    private void reloadAllLoot() {
        reloadPluginConfig_Piglin();
        // TODO: Add logging, and reload structure loot table config method here
    }

}