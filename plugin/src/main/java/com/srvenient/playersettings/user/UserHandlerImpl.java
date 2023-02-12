package com.srvenient.playersettings.user;

import com.srvenient.playersettings.data.SettingData;
import com.srvenient.playersettings.data.SettingDataManager;
import com.srvenient.playersettings.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import team.unnamed.gui.menu.item.ItemClickable;
import team.unnamed.gui.menu.type.MenuInventory;
import team.unnamed.gui.menu.type.MenuInventoryBuilder;

import java.util.List;
import java.util.Objects;

import static com.srvenient.playersettings.utils.ColorUtils.colorize;
import static com.srvenient.playersettings.utils.ItemUtils.getItem;
import static com.srvenient.playersettings.utils.ItemUtils.getItemclickable;

public class UserHandlerImpl implements UserHandler {

    private final Plugin plugin;
    private final FileConfiguration configuration;
    private final SettingDataManager settingDataManager;
    private final UserManager userManager;

    public UserHandlerImpl(
            Plugin plugin,
            SettingDataManager settingDataManager,
            UserManager userManager
    ) {
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
        this.settingDataManager = settingDataManager;
        this.userManager = userManager;
    }

    @Override
    public Inventory createMenu(@NotNull Player player) {
        final User user = userManager.getUser(player.getUniqueId());
        final MenuInventoryBuilder builder = MenuInventory.newBuilder(
                colorize(configuration.getString("menu.tile")), configuration.getInt("menu.rows")
        );

        builder.item(
                ItemClickable.builder(configuration.getInt("menu.common-items.close.slot"))
                        .item(getItem(
                                Objects.requireNonNull(configuration.getString("menu.common-items.close.material")),
                                Objects.requireNonNull(configuration.getString("menu.common-items.close.displayName")),
                                configuration.getStringList("menu.common-items.close.lore"),
                                configuration.getInt("menu.common-items.close.amount")
                        ))
                        .action(event -> {
                            player.closeInventory();
                            player.playSound(player, Sound.valueOf(configuration.getString("config.sound.close-inventory")), 1, 1);

                            return true;
                        })
                        .build()
        );

        for (SettingData data :
                settingDataManager.getSettings()) {
            if (configuration.getBoolean("config.enabled-status-items")) {
                builder.item(getItemclickable(user, configuration, data, this));
            }

            builder.item(ItemClickable.builder(data.slot())
                    .item(data.item())
                    .action(event -> {
                        if (isWorldDenied(player.getWorld())) {
                            player.sendMessage(colorize(configuration.getString("messages.world-denied")));

                            return false;
                        }

                        if (!player.hasPermission(data.permission())) {
                            player.sendMessage(colorize(configuration.getString("messages.no-permission")));

                            return false;
                        }

                        if (configuration.getBoolean("config.enabled-status-items")) {
                            Objects.requireNonNull(event.getClickedInventory())
                                    .setItem(data.slot(), getItemclickable(user, configuration, data, this).getItemStack());
                        }

                        data.executor().execute(user);
                        player.playSound(player, Sound.valueOf(configuration.getString("config.sound.change-state")), 1, 1);

                        return true;
                    })
                    .build());
        }

        return builder.build();
    }

    @Override
    public boolean isWorldDenied(@NotNull World world) {
        List<String> deniedWorldNames = configuration.getStringList("seating.denied-worlds");
        return deniedWorldNames.contains(world.getName());
    }

    @Override
    public void performActions(@NotNull User user) {
        final Player player = user.getPlayer();

        if (isWorldDenied(player.getWorld())) {
            Bukkit.getOnlinePlayers().forEach(players -> player.showPlayer(plugin, players));
            player.removePotionEffect(PotionEffectType.JUMP);
            player.setAllowFlight(false);

            return;
        }

        for (String settingId :
                user.settings().keySet()) {
            switch (settingId) {
                case "visibility" -> {
                    if (user.getSettingState("visibility") == 0) {
                        Bukkit.getOnlinePlayers().forEach(players -> player.showPlayer(plugin, players));
                    }

                    if (user.getSettingState("visibility") == 1) {
                        Bukkit.getOnlinePlayers().forEach(players -> {
                            if (!players.hasPermission("playersettings.visibility.rank")) {
                                player.hidePlayer(plugin, players);
                            } else {
                                player.showPlayer(plugin, players);
                            }
                        });

                        return;
                    }

                    if (user.getSettingState("visibility") == 2) {
                        Bukkit.getOnlinePlayers().forEach(players -> player.hidePlayer(plugin, players));

                        return;
                    }
                }
                case "jump" -> {
                    if (user.getSettingState("jump") == 0) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));

                        return;
                    }

                    if (user.getSettingState("jump") == 1) {
                        player.removePotionEffect(PotionEffectType.JUMP);
                    }
                }

                case "fly" -> {
                    if (user.getSettingState("fly") == 0) {
                        player.setFallDistance(0.0F);
                        player.setAllowFlight(true);

                        if (!player.getAllowFlight()) {
                            player.setFlying(false);
                        }

                        return;
                    }

                    if (user.getSettingState("fly") == 1) {
                        player.setAllowFlight(false);
                    }
                }
            }
        }
    }
}
