package com.srvenient.playersettings.user;

import com.srvenient.playersettings.storage.ModelService;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserManagerImpl implements UserManager {

    private final ModelService<User> delegate;
    private final Map<String, User> cache;
    private final FileConfiguration configuration;

    public UserManagerImpl(
            ModelService<User> delegate,
            FileConfiguration configuration
    ) {
        this.delegate = delegate;
        this.configuration = configuration;

        this.cache = new ConcurrentHashMap<>();
    }

    /**
     * @Override public void updateUser(@NotNull UUID uuid) {
     * final User userInCache = getUser(uuid);
     * <p>
     * if (userInCache == null) {
     * if (sqlManager.getPlayer(uuid) != null) {
     * users.put(uuid, sqlManager.getPlayer(uuid));
     * } else {
     * final Map<String, Byte> settings = new HashMap<>();
     * <p>
     * settings.put("visibility", (byte) configuration.getInt("config.default-values.visibility"));
     * settings.put("chat", (byte) configuration.getInt("config.default-values.chat"));
     * settings.put("jump", (byte) configuration.getInt("config.default-values.jump"));
     * settings.put("mount", (byte) configuration.getInt("config.default-values.mount"));
     * settings.put("fly", (byte) configuration.getInt("config.default-values.fly"));
     * <p>
     * final User newUser = new User(uuid, settings);
     * <p>
     * sqlManager.saveUser(newUser);
     * users.put(uuid, newUser);
     * }
     * }
     * }
     */

    @Override
    public @Nullable User getSync(@NotNull String id) {
        return cache.get(id);
    }

    @Override
    public @Nullable User getOrFindSync(@NotNull String id) {
        User user = getSync(id);

        if (user == null) {
            return delegate.findSync(id);
        }

        return user;
    }

    @Override
    public @Nullable List<User> getAllSync() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void uploadSync(@NotNull User model) {
        delegate.updateSync(model);
    }

    @Override
    public User saveSync(@NotNull User model, boolean saveInDatabase) {
        if (saveInDatabase) {
            delegate.saveSync(model);
        }
        cache.put(model.getId(), model);

        return model;
    }

    @Override
    public void deleteSync(@NotNull String id) {
        cache.remove(id);
    }

}
