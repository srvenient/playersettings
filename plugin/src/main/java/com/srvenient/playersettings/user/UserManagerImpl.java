package com.srvenient.playersettings.user;

import com.srvenient.playersettings.user.sql.UserSQLManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class UserManagerImpl implements UserManager {

    private final Map<UUID, User> users;
    private final UserSQLManager sqlManager;
    private final FileConfiguration configuration;

    public UserManagerImpl(
            UserSQLManager sqlManager,
            FileConfiguration configuration
    ) {
        this.users = new ConcurrentHashMap<>();
        this.sqlManager = sqlManager;
        this.configuration = configuration;
    }

    @Override
    public User getUser(@NotNull UUID uuid) {
        return users.get(uuid);
    }

    @Override
    public void updateUser(@NotNull UUID uuid) {
        final User userInCache = getUser(uuid);

        if (userInCache == null) {
            if (sqlManager.getPlayer(uuid) != null) {
                users.put(uuid, sqlManager.getPlayer(uuid));
            } else {
                final Map<String, Byte> settings = new HashMap<>();

                settings.put("visibility", (byte) configuration.getInt("config.default-values.visibility"));
                settings.put("chat", (byte) configuration.getInt("config.default-values.chat"));
                settings.put("jump", (byte) configuration.getInt("config.default-values.jump"));
                settings.put("mount", (byte) configuration.getInt("config.default-values.mount"));
                settings.put("fly", (byte) configuration.getInt("config.default-values.fly"));

                final User newUser = new User(uuid, settings);

                sqlManager.saveUser(newUser);
                users.put(uuid, newUser);
            }
        }
    }

    @Override
    public void removeUser(@NotNull User user) {
        sqlManager.updateUser(user);
        users.remove(user.id());
    }
}
