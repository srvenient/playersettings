package team.emptyte.playersettings.plugin;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.emptyte.playersettings.api.SettingHandler;
import team.emptyte.playersettings.api.data.SettingData;
import team.emptyte.playersettings.api.message.MessageHandler;
import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.user.User;
import team.unnamed.gui.menu.item.ItemClickable;
import team.unnamed.gui.menu.type.MenuInventory;
import team.unnamed.gui.menu.type.MenuInventoryBuilder;

import java.util.Objects;

public class DefaultSettingHandler implements SettingHandler {

    private final PlayerSettingsPlugin plugin;
    private final FileConfiguration config;
    private final MessageHandler messageHandler;
    private final ModelService<User> users;
    private final ModelService<SettingData> settings;

    public DefaultSettingHandler(
            PlayerSettingsPlugin plugin,
            FileConfiguration config,
            MessageHandler messageHandler,
            ModelService<User> users, ModelService<SettingData> settings) {
        this.plugin = plugin;
        this.config = config;
        this.messageHandler = messageHandler;
        this.users = users;
        this.settings = settings;
    }

    @Override
    public void openMenu(Player player) {
        User user = users.findSync(player.getUniqueId().toString());

        MenuInventoryBuilder inventory = MenuInventory.newBuilder(
                messageHandler.getString("menu.title"),
                config.getInt("menu.rows")
        );

        Objects.requireNonNull(settings.findAllSync()).forEach(data -> {
            inventory.item(
                    ItemClickable.builder(data.getSlot())
                            .item(data.getItem())
                            .action(event -> {
                                if (messageHandler.hasPermission(player, data.getPermission())) {
                                    runEvent(plugin, user, data.getId());

                                    Objects.requireNonNull(event.getClickedInventory()).setItem(data.getSlot(), data.getItem());

                                    if (data.isSecondItem()) {
                                        Objects.requireNonNull(event.getClickedInventory()).setItem(data.getSlot() + 9, stateItem(user, data).getItemStack());
                                    }

                                    player.playSound(player, Sound.valueOf(config.getString("config.menu.click-settings")), 1, 1);

                                    return true;
                                }

                                player.closeInventory();

                                return true;
                            })
                            .build()
            );

            if (data.isSecondItem()) {
                inventory.item(stateItem(user, data));
            }
        });

        inventory.item(
                ItemClickable.builder(config.getInt("menu.secondary-items.close.slot"))
                        .item(messageHandler.getItem("menu.secondary-items.close"))
                        .action(event -> {
                            player.closeInventory();
                            player.playSound(player, Sound.valueOf(config.getString("config.menu.click-item-close")), 1, 1);

                            return true;
                        })
                        .build()
        );

        player.openInventory(inventory.build());
    }

    @Override
    public boolean isWorldDenied(World world) {
        return false;
    }

    private ItemClickable stateItem(User user, SettingData data) {
        return ItemClickable.builder(data.getSlot() + 9)
                .item(statusItem(user, data.getId()))
                .action(event -> {
                    if (messageHandler.hasPermission(user.getPlayer(), data.getPermission())) {
                        if (config.getBoolean("config.menu.item-state-click-change")) {
                            runEvent(plugin, user, data.getId());

                            Objects.requireNonNull(event.getClickedInventory()).setItem(data.getSlot(), data.getItem());
                            Objects.requireNonNull(event.getClickedInventory()).setItem(data.getSlot(), data.getItem());

                            user.getPlayer().playSound(user.getPlayer(), Sound.valueOf(config.getString("config.menu.click-settings")), 1, 1);
                        }

                        return true;
                    }

                    user.getPlayer().closeInventory();

                    return true;
                })
                .build();
    }

    private ItemStack statusItem(User user, String type) {
        String DEFAULT_PATH = "menu.secondary-items.";

        switch (type) {
            case "visibility", "chat" -> {
                switch (user.getSetting(type).getState()) {
                    case 0 -> {
                        return messageHandler.getItem(DEFAULT_PATH + "status.enabled");
                    }
                    case 1 -> {
                        return messageHandler.getItem(DEFAULT_PATH + "status.disabled");
                    }
                    case 2 -> {
                        return messageHandler.getItem(DEFAULT_PATH + "status.only-ranks");
                    }
                }
            }
            case "double-jump", "mount", "fly" -> {
                switch (user.getSetting(type).getState()) {
                    case 0 -> {
                        return messageHandler.getItem(DEFAULT_PATH + "status.enabled");
                    }
                    case 1 -> {
                        return messageHandler.getItem(DEFAULT_PATH + "status.disabled");
                    }
                }
            }
        }

        return null;
    }

}
