package io.levysworks.lootmanager.piglintrades;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TradeItems {
    private final Random random = new Random();

    public HashMap<ItemStack, ItemData> items = new HashMap<>();

    public static ItemStack selectItem(HashMap<ItemStack, ItemData> items) {
        int totalWeight = 0;
        for (ItemData weight : items.values()) {
            totalWeight += weight.chance();
        }

        Random random = new Random();
        int randomWeight = random.nextInt(totalWeight);

        for (Map.Entry<ItemStack, ItemData> entry : items.entrySet()) {
            randomWeight -= entry.getValue().chance();
            if (randomWeight < 0 && entry.getKey() != null) {
                ItemStack item = entry.getKey();
                int min_value = entry.getValue().min_value();
                int max_value = entry.getValue().max_value();

                if (min_value > 0 && min_value < max_value ) {
                    item.setAmount(random.nextInt(min_value, max_value + 1));
                } else {
                    item.setAmount(1);
                }

                return item;
            }
        }

        return null;
    }

    public void emptyTradeItemCache() {
        items = new HashMap<>();
    }
}
