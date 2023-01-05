package team.emptyte.mercurysettings.api.data;

import org.bukkit.inventory.ItemStack;

public interface SettingDataManager {

    void register();

    void unregister();

    ItemStack getItemStack(String key);

}
