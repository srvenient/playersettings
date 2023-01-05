package team.emptyte.mercurysettings.api.user;

import org.bukkit.entity.Player;

public interface UserManager {

    void register(Player player);

    void unregister(Player player);

}
