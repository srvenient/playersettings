package team.emptyte.mercurysettings.plugin.user;

import org.bukkit.entity.Player;
import team.emptyte.mercurysettings.api.registry.KeyedRegistry;
import team.emptyte.mercurysettings.api.user.User;
import team.emptyte.mercurysettings.api.user.UserManager;
import team.emptyte.mercurysettings.api.user.internal.Setting;

import java.util.HashMap;
import java.util.Map;

public class DefaultUserManager implements UserManager {

    private final KeyedRegistry<User> users;

    public DefaultUserManager(KeyedRegistry<User> users) {
        this.users = users;
    }

    @Override
    public void register(Player player) {
        String id = player.getUniqueId().toString();

        User user = users.get(id);

        if (user == null) {
            user = new User(id);

            users.register(user);
            registerSettings(user);

            return;
        }
    }

    @Override
    public void unregister(Player player) {

    }
    private void registerSettings(User user) {
        Map<String, Setting> settings = new HashMap<>();

        settings.put("visibility", new Setting("visibility", (byte) 0));
        settings.put("chat", new Setting("chat", (byte) 0));
        settings.put("double-jump", new Setting("double-jump", (byte) 1));
        settings.put("mount", new Setting("mount", (byte) 1));
        settings.put("fly", new Setting("fly", (byte) 1));

        user.setSettings(settings);
    }
}
