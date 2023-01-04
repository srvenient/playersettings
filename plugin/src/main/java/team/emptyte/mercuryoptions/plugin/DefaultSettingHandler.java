package team.emptyte.mercuryoptions.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import team.emptyte.mercuryoptions.api.SettingHandler;
import team.emptyte.mercuryoptions.api.data.SettingData;
import team.emptyte.mercuryoptions.api.registry.KeyedRegistry;
import team.emptyte.mercuryoptions.plugin.MercurySettings;
import team.unnamed.gui.menu.item.ItemClickable;
import team.unnamed.gui.menu.type.MenuInventory;
import team.unnamed.gui.menu.type.MenuInventoryBuilder;

import static team.emptyte.mercuryoptions.api.utils.Utils.colorize;

public class DefaultSettingHandler implements SettingHandler {

    private final String PATH = "menu.items";

    private final KeyedRegistry<SettingData> settings;
    private final FileConfiguration config;

    public DefaultSettingHandler(
            KeyedRegistry<SettingData> settings,
            FileConfiguration config
    ) {
        this.settings = settings;
        this.config = config;
    }

    @Override
    public void openMenu(Player player) {
        MenuInventoryBuilder inventory = MenuInventory.newBuilder(
                colorize(config.getString("menu.title")),
                config.getInt("menu.rows")
        );

        settings.getAll().forEach(data ->
                inventory.item(
                        ItemClickable.builder(data.getSlot())
                                .item(data.getItem())
                                .action(event -> {


                                    return false;
                                })
                                .build()
                )
        );

        player.openInventory(inventory.build());
    }

    @Override
    public void updateItem(Inventory inventory, ItemStack itemStack, byte slot) {
        if (config.getBoolean("config.menu.item-state")) {

        } else {

        }
    }
}
