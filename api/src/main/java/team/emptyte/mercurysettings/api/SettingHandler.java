package team.emptyte.mercurysettings.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface SettingHandler {

    void openMenu(Player player);

    void updateItem(Inventory inventory, ItemStack itemStack, byte slot);

}
