package team.emptyte.mercurysettings.api.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.emptyte.mercurysettings.api.user.User;
import team.emptyte.mercurysettings.api.user.internal.Setting;

public interface EventAction {

    void execute(Plugin plugin, User user);

}
