package com.srvenient.playersettings.executor;

import com.srvenient.playersettings.executor.type.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SettingExecutorManagerImpl implements SettingExecutorManager{

    private final Plugin plugin;

    public SettingExecutorManagerImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public SettingExecutor validExecutor(@NotNull String settingId) {
        switch (settingId) {
            case "visibility" -> new VisibilityModelExecutor(plugin);
            case "chat" -> new ChatModelExecutor();
            case "jump" -> new JumpModelExecutor();
            case "mount" -> new MountModelExecutor();
            case "fly" -> new FlyModelExecutor();
        }

        return null;
    }

}
