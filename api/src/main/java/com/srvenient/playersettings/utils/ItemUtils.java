package com.srvenient.playersettings.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.srvenient.playersettings.data.SettingData;
import com.srvenient.playersettings.material.XMaterial;
import com.srvenient.playersettings.user.User;
import com.srvenient.playersettings.user.UserHandler;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import team.unnamed.gui.menu.item.ItemClickable;

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

    public static ItemClickable getItemclickable(
            User user,
            FileConfiguration configuration,
            SettingData data,
            UserHandler userHandler
    ) {
        return ItemClickable.builder(data.slot() + 9)
                .item(getStatusItem(user, configuration, data.id()))
                .action(event -> {
                    if (!configuration.getBoolean("config.interact-with-status-item")) {
                        return true;
                    }

                    final Player player = user.getPlayer();

                    if (userHandler.isWorldDenied(player.getWorld())) {
                        player.sendMessage(colorize(configuration.getString("messages.world-denied")));

                        return false;
                    }

                    if (!player.hasPermission(data.permission())) {
                        player.sendMessage(colorize(configuration.getString("messages.no-permission")));

                        return false;
                    }

                    if (configuration.getBoolean("config.enabled-status-items")) {
                        Objects.requireNonNull(event.getClickedInventory())
                                .setItem(data.slot(), getItemclickable(user, configuration, data, userHandler).getItemStack());
                    }

                    data.executor().execute(user);
                    player.playSound(player, Sound.valueOf(configuration.getString("config.sound.change-state")), 1, 1);

                    return true;
                })
                .build();
    }

    private static ItemStack getStatusItem(
            @NotNull User user,
            @NotNull FileConfiguration configuration,
            @NotNull String type
    ) {
        final String COMMON_ITEM_PATH = "menu.common-items.";

        switch (type) {
            case "visibility" -> {
                switch (user.getSettingState(type)) {
                    case 0 -> {
                        return getItem(
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "enabled.material")),
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "enabled.displayName")),
                                configuration.getStringList(COMMON_ITEM_PATH + "enabled.lore"),
                                configuration.getInt(COMMON_ITEM_PATH + "enabled.amount")
                        );
                    }
                    case 1 -> {
                        return getItem(
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "disabled.material")),
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "disabled.displayName")),
                                configuration.getStringList(COMMON_ITEM_PATH + "disabled.lore"),
                                configuration.getInt(COMMON_ITEM_PATH + "disabled.amount")
                        );
                    }
                    case 2 -> {
                        return getItem(
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "only-ranks.material")),
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "only-ranks.displayName")),
                                configuration.getStringList(COMMON_ITEM_PATH + "only-ranks.lore"),
                                configuration.getInt(COMMON_ITEM_PATH + "only-ranks.amount")
                        );
                    }
                }
            }
            case "chat", "double-jump", "mount", "fly" -> {
                switch (user.getSettingState(type)) {
                    case 0 -> {
                        return getItem(
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "enabled.material")),
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "enabled.displayName")),
                                configuration.getStringList(COMMON_ITEM_PATH + "enabled.lore"),
                                configuration.getInt(COMMON_ITEM_PATH + "enabled.amount")
                        );
                    }
                    case 1 -> {
                        return getItem(
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "disabled.material")),
                                Objects.requireNonNull(configuration.getString(COMMON_ITEM_PATH + "disabled.displayName")),
                                configuration.getStringList(COMMON_ITEM_PATH + "disabled.lore"),
                                configuration.getInt(COMMON_ITEM_PATH + "disabled.amount")
                        );
                    }
                }
            }
        }

        return null;
    }
}
