package io.levysworks.lootmanager.piglintrades;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

import static io.levysworks.lootmanager.Lootmanager.trades;
import static io.levysworks.lootmanager.Lootmanager.piglin_is_enabled;

import java.util.Objects;

public class PiglinTradeListener implements Listener {
    @EventHandler
    public void onPiglinBarter(EntityDropItemEvent event) {
        if (!piglin_is_enabled) {
            return;
        }

        if (trades.items.isEmpty()) {
            return;
        }

        Entity entity = event.getEntity();
        if (entity instanceof Piglin piglin) {
            event.getItemDrop().setItemStack(Objects.requireNonNull(TradeItems.selectItem(trades.items)));
        }
    }
}
