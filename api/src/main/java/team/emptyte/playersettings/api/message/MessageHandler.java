package team.emptyte.playersettings.api.message;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface MessageHandler {

    String BASE_MESSAGES_PATH = "messages.%s";

    String getString(String path);

    List<String> getStringList(String path);

    void sendMessage(CommandSender player, String path);

    void sendMessages(CommandSender player, String path);

    boolean hasPermission(Player player, String permission);

    String makePath(String path);

    ItemStack getItem(String key);

    default String colorize(String text) {
        if (
                Bukkit.getVersion().contains("1.16")
                        || Bukkit.getVersion().contains("1.17")
                        || Bukkit.getVersion().contains("1.18")
                        || Bukkit.getVersion().contains("1.19")
        ) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher match = pattern.matcher(text);

            while (match.find()) {
                String color = text.substring(match.start(), match.end());
                text = text.replace(color, ChatColor.of(color) + "");

                match = pattern.matcher(text);
            }
        }

        text = ChatColor.translateAlternateColorCodes('&', text);


        return text;
    }

}
