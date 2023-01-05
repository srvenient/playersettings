package team.emptyte.mercurysettings.plugin.data;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import team.emptyte.mercurysettings.api.data.SettingData;
import team.emptyte.mercurysettings.api.data.SettingDataManager;
import team.emptyte.mercurysettings.api.registry.KeyedRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static team.emptyte.mercurysettings.api.utils.Utils.colorize;

public class DefaultSettingDataManager implements SettingDataManager {

    private final String PATH = "menu.items";

    private final KeyedRegistry<SettingData> settings;
    private final FileConfiguration config;

    public DefaultSettingDataManager(
            KeyedRegistry<SettingData> settings,
            FileConfiguration config
    ) {
        this.settings = settings;
        this.config = config;
    }

    @Override
    public void register() {
        if (!config.contains(PATH)) {
            return;
        }

        Objects.requireNonNull(config.getConfigurationSection(PATH))
                .getKeys(false)
                .forEach(key -> {
                    SettingData data = new SettingData(
                            key,
                            getItemStack(key),
                            (byte) config.getInt(PATH + "." + key + ".slot"),
                            config.getBoolean("config.menu.item-state")
                            );

                    settings.register(data);
                });
    }

    @Override
    public void unregister() {
    }

    @Override
    public ItemStack getItemStack(String key) {
        String ITEM_PATH = PATH + "." + key + ".";
        ItemStack item;

        if (Objects.requireNonNull(config.getString(ITEM_PATH + "material")).startsWith("eyJ")) {
            item = new ItemStack(Material.PLAYER_HEAD);

            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", config.getString(ITEM_PATH + "material")));
            try {
                Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, profile);
            } catch (IllegalArgumentException | NoSuchFieldException | SecurityException |
                     IllegalAccessException error) {
                error.printStackTrace();
            }
            item.setItemMeta(skullMeta);
        } else {
            item = new ItemStack(Material.valueOf(config.getString(ITEM_PATH + "material")));
        }

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(colorize(config.getString(ITEM_PATH + "displayName")));

        List<String> lore = new ArrayList<>();
        for (String line : config.getStringList(ITEM_PATH + "lore")) {
            lore.add(colorize(line));
        }
        meta.setLore(lore);


        item.setItemMeta(meta);
        item.setAmount(config.getInt(ITEM_PATH + "amount"));

        return item;
    }
}
