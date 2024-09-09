package io.levysworks.lootmanager.piglintrades;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class VanillaTrades {
    static Random random = new Random();

    public static void addVanillaTrades(TradeItems trades) {
        // Enchanted Book with Soul Speed
        ItemStack enchanted_book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) enchanted_book.getItemMeta();
        enchantmentStorageMeta.addStoredEnchant(Enchantment.SOUL_SPEED,
                random.nextInt(1, 4), false);
        enchanted_book.setItemMeta(enchantmentStorageMeta);
        trades.items.put(enchanted_book,
                new ItemData(1, 1, 1));

        // Iron Boots with Soul Speed
        ItemStack iron_boots = new ItemStack(Material.IRON_BOOTS);
        iron_boots.addEnchantment(Enchantment.SOUL_SPEED,
                random.nextInt(1, 4));
        trades.items.put(iron_boots,
                new ItemData(2, 1, 1));

        // Splash Potion of Fire Resistance
        ItemStack splash_potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta splash_meta = (PotionMeta) splash_potion.getItemMeta();
        PotionEffect splash_effect = new PotionEffect(
                PotionEffectType.FIRE_RESISTANCE, 1800, 1, true, true, true);
        splash_meta.addCustomEffect(splash_effect, true);
        splash_potion.setItemMeta(splash_meta);
        trades.items.put(splash_potion, new ItemData(2, 1, 1));

        // Potion of Fire Resistance
        ItemStack potion_of_fire_resistance = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion_of_fire_resistance.getItemMeta();
        PotionEffect effect = new PotionEffect(
                PotionEffectType.FIRE_RESISTANCE, 1800, 1, true, true, true);
        meta.addCustomEffect(effect, true);
        potion_of_fire_resistance.setItemMeta(meta);
        trades.items.put(potion_of_fire_resistance, new ItemData(2, 1, 1));

        // Water Bottle
        ItemStack water_bottle = new ItemStack(Material.POTION);
        trades.items.put(water_bottle, new ItemData(2, 1, 1));

        // Iron Nugget
        ItemStack iron_nugget = new ItemStack(Material.IRON_NUGGET);
        trades.items.put(iron_nugget, new ItemData(2, 10, 36));

        // Ender Pearl
        ItemStack ender_pearl = new ItemStack(Material.ENDER_PEARL);
        trades.items.put(ender_pearl, new ItemData(2, 2, 4));

        // String
        ItemStack string = new ItemStack(Material.STRING);
        trades.items.put(string, new ItemData(5, 3, 9));

        // Nether Quartz
        ItemStack nether_quartz = new ItemStack(Material.QUARTZ);
        trades.items.put(nether_quartz, new ItemData(5, 5, 12));

        // Obsidian
        ItemStack obsidian = new ItemStack(Material.OBSIDIAN);
        trades.items.put(obsidian, new ItemData(9, 1, 1));

        // Crying Obsidian
        ItemStack crying_obsidian = new ItemStack(Material.CRYING_OBSIDIAN);
        trades.items.put(crying_obsidian, new ItemData(9, 1, 3));

        // Fire Charge
        ItemStack fire_charge = new ItemStack(Material.FIRE_CHARGE);
        trades.items.put(fire_charge, new ItemData(9, 1, 1));

        // Leather
        ItemStack leather = new ItemStack(Material.LEATHER);
        trades.items.put(leather, new ItemData(9, 2, 4));

        // Soul Sand
        ItemStack soul_sand = new ItemStack(Material.SOUL_SAND);
        trades.items.put(soul_sand, new ItemData(9, 2, 8));

        // Nether Brick
        ItemStack nether_brick = new ItemStack(Material.NETHER_BRICK);
        trades.items.put(nether_brick, new ItemData(9, 2, 8));

        // Spectral Arrow
        ItemStack spectral_arrow = new ItemStack(Material.SPECTRAL_ARROW);
        trades.items.put(spectral_arrow, new ItemData(9, 6, 12));

        // Gravel
        ItemStack gravel = new ItemStack(Material.GRAVEL);
        trades.items.put(gravel, new ItemData(9, 6, 12));

        // Blackstone
        ItemStack blackstone = new ItemStack(Material.BLACKSTONE);
        trades.items.put(blackstone, new ItemData(9, 8, 16));

    }
}
