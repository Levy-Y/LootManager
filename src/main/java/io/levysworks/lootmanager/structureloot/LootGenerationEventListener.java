package io.levysworks.lootmanager.structureloot;

import io.levysworks.lootmanager.Lootmanager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootGenerationEventListener implements Listener {
    @EventHandler
    public void onLootGenerate(LootGenerateEvent event) {
        if (!Lootmanager.structure_enabled) {
            return;
        }

        if (Lootmanager.structure_loadedItems.isEmpty()) {
            Lootmanager.getPlugin(Lootmanager.class).getLogger().warning("[Structure] No item in the internal cache, try reloading the plugin!");
            return;
        }

        for (CustomLootContainer structureInstance : Lootmanager.structure_loadedItems) {
            if (event.getLootTable().getKey().equals(structureInstance.lootTableName.getKey())) {
                Random random = new Random();

                List<ItemStack> items = new ArrayList<>(event.getLoot());

                int result = random.nextInt(structureInstance.itemStacks.size());
                ItemStack chosenItem = structureInstance.itemStacks.get(result);

                if (chosenItem != null) {
                    items.add(chosenItem);
                    event.setLoot(items);
                } else {
                    Lootmanager.getPlugin(Lootmanager.class).getLogger().severe(String.format("[Structure] Error, while trying to add an item to the internal cache"));
                }
            }
        }
    }
}