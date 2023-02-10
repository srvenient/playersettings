package com.srvenient.playersettings.user;

import com.srvenient.playersettings.data.SettingDataManager;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import team.unnamed.gui.menu.item.ItemClickable;
import team.unnamed.gui.menu.type.MenuInventory;
import team.unnamed.gui.menu.type.MenuInventoryBuilder;

import static com.srvenient.playersettings.utils.ColorUtils.colorize;

public class UserHandlerImpl implements UserHandler {

    private final FileConfiguration config;
    private final SettingDataManager settingDataManager;
    private final UserManager userManager;

    public UserHandlerImpl(
            FileConfiguration config,
            SettingDataManager settingDataManager,
            UserManager userManager
    ) {
        this.config = config;
        this.settingDataManager = settingDataManager;
        this.userManager = userManager;
    }

    @Override
    public Inventory createMenu(@NotNull Player player) {
        final User user = userManager.getUser(player.getUniqueId());
        final MenuInventoryBuilder builder = MenuInventory.newBuilder(
                colorize(config.getString("menu.tile")), config.getInt("menu.rows")
        );

        settingDataManager.getSettings()
                .forEach(settingData -> builder.item(
                        ItemClickable.builder(settingData.slot())
                                .item(settingData.item())
                                .action(event -> {
                                    if (isWorldDenied(player.getWorld())) {
                                        player.sendMessage(colorize(config.getString("messages.world-denied")));

                                        return false;
                                    }

                                    if (!player.hasPermission(settingData.permission())) {
                                        player.sendMessage(colorize(config.getString("messages.no-permission")));

                                        return false;
                                    }

                                    settingData.executor().execute(user);

                                    return true;
                                })
                                .build()
                ));

        return builder.build();
    }

    @Override
    public boolean isWorldDenied(@NotNull World world) {
        return false;
    }
}
