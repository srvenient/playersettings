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
            case "visibility" -> {
                return new VisibilityModelExecutor(plugin);
            }
            case "chat" -> {
                return new ChatModelExecutor();
            }
            case "jump" -> {
                return new JumpModelExecutor();
            }
            case "mount" -> {
                return new MountModelExecutor();
            }
            case "fly" -> {
                return new FlyModelExecutor();
            }
        }

        return null;
    }

}
