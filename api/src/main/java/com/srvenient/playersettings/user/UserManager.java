package com.srvenient.playersettings.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UserManager {

    @Nullable User getSync(@NotNull String id);

    @Nullable User getOrFindSync(@NotNull String id);

    @Nullable List<User> getAllSync();

    /**
     * Uploads the model to the server
     *
     * @param model The model to be uploaded.
     */
    void uploadSync(@NotNull User model);

    User saveSync(@NotNull User model, boolean saveInDatabase);

    void deleteSync(@NotNull String id);

}
