package team.emptyte.playersettings.plugin.message;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import team.emptyte.playersettings.api.message.MessageHandler;

import java.lang.reflect.Field;
import java.util.*;

public class DefaultMessageHandler
        implements MessageHandler {

    private final FileConfiguration config;

    public DefaultMessageHandler(FileConfiguration config) {
        this.config = config;
    }


    @Override
    public String getString(String path) {
        String line = config.getString(path);

        if (line == null) {
            return path;
        }

        return colorize(line);
    }

    @Override
    public List<String> getStringList(String path) {
        List<String> messages = config.getStringList(path);

        if (messages.isEmpty()) {
            return Collections.singletonList(path);
        }

        messages.replaceAll(this::colorize);

        return messages;
    }

    @Override
    public void sendMessage(CommandSender player, String path) {
        player.sendMessage(getString(makePath(path)));
    }

    @Override
    public void sendMessages(CommandSender player, String path) {
        player.sendMessage(String.join("\n", getString(makePath(path))));
    }

    @Override
    public boolean hasPermission(Player player, String permission) {
        if (!player.hasPermission(permission)) {
            sendMessage(player, "no-permission");

            return false;
        }

        return true;
    }

    @Override
    public String makePath(String path) {
        return String.format(BASE_MESSAGES_PATH, path);
    }

    @Override
    public ItemStack getItem(String key) {
        ItemStack item;

        if (Objects.requireNonNull(getString(key + ".material")).startsWith("eyJ")) {
            item = new ItemStack(Material.PLAYER_HEAD);

            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", getString(key + ".material")));
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
            item = new ItemStack(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(config.getString(key + ".material")))));
        }

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(getString(key + ".displayName"));
        meta.setLore(getStringList(key + ".lore"));

        item.setItemMeta(meta);
        item.setAmount(config.getInt(key + ".amount"));

        return item;
    }
}
