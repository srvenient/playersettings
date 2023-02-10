package com.srvenient.playersettings.executor;

import org.jetbrains.annotations.NotNull;

public interface SettingExecutorManager {

    SettingExecutor validExecutor(@NotNull String settingId);

}
