package com.srvenient.playersettings.data;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface SettingDataManager {

    Collection<SettingData> getSettings();

    void uploadSettings(@NotNull String path);

}