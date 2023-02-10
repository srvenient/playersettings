package com.srvenient.playersettings.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.srvenient.playersettings.material.XMaterial;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.srvenient.playersettings.utils.ColorUtils.colorize;

public final class ItemUtils {

    public static ItemStack getItem(
            @NotNull String material,
            @NotNull String displayName,
            @NotNull List<String> lore,
            int amount
    ) {
        ItemStack item;

        if (Objects.requireNonNull(material).startsWith("eyJ")) {
            item = new ItemStack(Objects.requireNonNull(XMaterial.PLAYER_HEAD.parseItem()));

            final SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            final GameProfile profile = new GameProfile(UUID.randomUUID(), null);

            profile.getProperties().put("textures", new Property("textures", material));

            try {
                final Field profileField = skullMeta.getClass().getDeclaredField("profile");

                profileField.setAccessible(true);
                profileField.set(skullMeta, profile);
            } catch (IllegalArgumentException | NoSuchFieldException | SecurityException |
                     IllegalAccessException error) {
                error.printStackTrace();
            }
            item.setItemMeta(skullMeta);
        } else {
            item = new ItemStack(Objects.requireNonNull(XMaterial.valueOf(material).parseItem()));
        }

        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(colorize(displayName));
        meta.setLore(colorize(lore));

        item.setItemMeta(meta);
        item.setAmount(amount);

        return item;
    }

}
