package team.emptyte.mercurysettings.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import team.emptyte.mercurysettings.api.SettingHandler;
import team.emptyte.mercurysettings.api.data.SettingData;
import team.emptyte.mercurysettings.api.registry.KeyedRegistry;
import team.emptyte.mercurysettings.api.user.User;
import team.emptyte.mercurysettings.plugin.event.DefaultCallEvent;
import team.unnamed.gui.menu.item.ItemClickable;
import team.unnamed.gui.menu.type.MenuInventory;
import team.unnamed.gui.menu.type.MenuInventoryBuilder;

import static team.emptyte.mercurysettings.api.utils.Utils.colorize;

public class DefaultSettingHandler implements SettingHandler {

    private final String PATH = "menu.items";

    private final MercurySettings plugin;

    private final KeyedRegistry<User> users;
    private final KeyedRegistry<SettingData> settings;

    public DefaultSettingHandler(MercurySettings plugin,
                                 KeyedRegistry<User> users,
                                 KeyedRegistry<SettingData> settings) {
        this.plugin = plugin;

        this.settings = settings;
        this.users = users;
    }

    @Override
    public void openMenu(Player player) {
        FileConfiguration config = plugin.getConfig();
        User user = users.get(player.getUniqueId().toString());

        MenuInventoryBuilder inventory = MenuInventory.newBuilder(
                colorize(config.getString("menu.title")),
                config.getInt("menu.rows")
        );

        settings.getAll().forEach(data ->
                inventory.item(
                        ItemClickable.builder(data.getSlot())
                                .item(data.getItem())
                                .action(event -> {
                                    new DefaultCallEvent(plugin).callEvent(user, data.getId());

                                    return true;
                                })
                                .build()
                )
        );

        player.openInventory(inventory.build());
    }

    @Override
    public void updateItem(Inventory inventory, ItemStack itemStack, byte slot) {
        /*if (config.getBoolean("config.menu.item-state")) {

        } else {

        }*/
    }
}
