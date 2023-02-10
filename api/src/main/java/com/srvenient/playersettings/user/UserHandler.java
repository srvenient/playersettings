package com.srvenient.playersettings.user;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface UserHandler {

    Inventory createMenu(@NotNull Player player);

    boolean isWorldDenied(@NotNull World world);

}
