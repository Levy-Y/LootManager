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

import static io.levysworks.lootmanager.piglintrades.ReloadPiglinConfig.reloadPluginConfig_Piglin;
import static io.levysworks.lootmanager.blockdrops.ReloadBlocksConfig.reloadPluginConfig_Blocks;
import static io.levysworks.lootmanager.structureloot.ReloadStructureConfig.reloadPluginConfig_Structure;

public class ReloadCommandExecutor implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lootreload")) {
            if (args.length == 0) {
                sender.sendMessage("Usage: /lootreload <all|piglin|structure|block>");
                return false;
            }

            if (args[0].equalsIgnoreCase("all") && sender.hasPermission("lootmanager.reload.all")) {
                reloadAllLoot();
                sender.sendMessage("LootManager: All config files have been reloaded.");
            } else if (args[0].equalsIgnoreCase("piglin") && sender.hasPermission("lootmanager.reload.piglin")) {
                reloadPluginConfig_Piglin();
                sender.sendMessage("LootManager: Piglin loot tables have been reloaded.");
            } else if (args[0].equalsIgnoreCase("structure") && sender.hasPermission("lootmanager.reload.structure")) {
                reloadPluginConfig_Structure();
                sender.sendMessage("LootManager: Structure loot tables have been reloaded.");
            } else if (args[0].equalsIgnoreCase("block") && sender.hasPermission("lootmanager.reload.block")) {
                reloadPluginConfig_Blocks();
                sender.sendMessage("LootManager: Block drops have been reloaded.");
            } else {
                sender.sendMessage("Invalid argument. Usage: /lootreload <all|piglin|structure|block>");
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
                List<String> options = Arrays.asList("all", "piglin", "structure", "block");

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
        reloadPluginConfig_Blocks();
        reloadPluginConfig_Structure();
    }

}