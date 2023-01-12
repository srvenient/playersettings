package team.emptyte.playersettings.api;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.emptyte.playersettings.api.data.event.EventType;
import team.emptyte.playersettings.api.user.User;

public interface SettingHandler {

    String SETTING_RANK_PERMISSION = "setting.rank";

    void openMenu(Player player);

    boolean isWorldDenied(World world);

    default void runEvent(Plugin plugin, User user, String type) {
        switch (type){
            case "visibility" -> EventType.VISIBILITY.getAction().get(plugin, user);
            case "chat" -> EventType.CHAT.getAction().get(plugin, user);
            case "double-jump" -> EventType.DOUBLE_JUMP.getAction().get(plugin, user);
            case "mount" -> EventType.MOUNT.getAction().get(plugin, user);
            case "fly" -> EventType.FLY.getAction().get(plugin, user);
        }
    }

}
