package com.srvenient.playersettings.user;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserManager {

    User getUser(@NotNull UUID uuid);

    void updateUser(@NotNull UUID uuid);

    void removeUser(@NotNull User user);

}
