package io.levysworks.lootmanager;

import dev.lone.itemsadder.api.CustomStack;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProviderBuilder {
    public ItemStack ProviderBuilder(ItemProviders provider, @NotNull String identifier, Integer amount, @Nullable String category) {
        return switch (provider) {
            case ItemProviders.VANILLA -> {
                try {
                    yield new ItemStack(Material.valueOf(identifier.toUpperCase()), amount);
                }  catch (IllegalArgumentException ignored) { }
                yield null;
            }
            case ItemProviders.ORAXEN -> {
               if (!Lootmanager.compatibilityChecker.has_oraxen) {
                   yield null;
               } else {
                   ItemBuilder oraxenItem = OraxenItems.getItemById(identifier);
                   yield oraxenItem.build();
               }
            }
            case ItemProviders.ITEMSADDER -> {
                if (!Lootmanager.compatibilityChecker.has_itemsadder) {
                    yield null;
                } else {
                    CustomStack itemsAdderItem = CustomStack.getInstance(identifier);
                    yield itemsAdderItem.getItemStack();
                }
            }
            case MMOITEMS -> {
                if (!Lootmanager.compatibilityChecker.has_mmoitems) {
                    yield null;
                } else {
                    MMOItem mmoitemsItemStack = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(category.toUpperCase()), identifier.toUpperCase());
                    if (mmoitemsItemStack != null) {
                        ItemStack builtMMOItem = mmoitemsItemStack.newBuilder().build();
                        builtMMOItem.setAmount(amount);
                        yield builtMMOItem;
                    }
                }
                yield null;
            }
        };
    }
}
