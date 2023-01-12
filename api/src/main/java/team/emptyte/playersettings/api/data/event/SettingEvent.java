package team.emptyte.playersettings.api.data.event;

import org.bukkit.plugin.Plugin;
import team.emptyte.playersettings.api.user.User;

public interface SettingEvent {

    void get(Plugin plugin, User user);

}
