package team.emptyte.playersettings.api.user;

import org.bukkit.entity.Player;

public interface UserManager {

    void load(String id);

    void save(String id);

}
