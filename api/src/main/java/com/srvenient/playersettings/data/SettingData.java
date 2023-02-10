package com.srvenient.playersettings.data;

import com.srvenient.playersettings.executor.SettingExecutor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public record SettingData
        (@NotNull String id,
         @NotNull String permission,
         @NotNull ItemStack item,
         int slot,
         @NotNull SettingExecutor executor) {
}
