package io.levysworks.lootmanager;

import dev.lone.itemsadder.api.CustomStack;
import io.th0rgal.oraxen.api.OraxenItems;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.manager.TypeManager;

public class CompatibilityChecker {
    public boolean has_oraxen = false;
    public boolean has_mmoitems = false;
    public boolean has_itemsadder = false;

    public void checkForPlugins() {
        // Check for oraxen
        try {
            OraxenItems.getItemById("Check");

            has_oraxen = true;
            Lootmanager.getPlugin(Lootmanager.class).getLogger().info("[Main] Oraxen found, hooking into it.");
        } catch (NoClassDefFoundError ignored) {}

        // Check for mmoitems
        try {
            TypeManager types = MMOItems.plugin.getTypes();
            types.has("Check");

            has_mmoitems = true;
            Lootmanager.getPlugin(Lootmanager.class).getLogger().info("[Main] MMOItems found, hooking into it.");
        } catch (NoClassDefFoundError ignored) {}

        // Check for itemsadder
        try {
            CustomStack.isInRegistry("Check");

            has_itemsadder = true;
            Lootmanager.getPlugin(Lootmanager.class).getLogger().info("[Main] ItemsAdder found, hooking into it.");
        } catch (NoClassDefFoundError ignored) {}
    }
}
