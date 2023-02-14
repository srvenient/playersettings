package com.srvenient.playersettings.data;

import com.google.gson.JsonSerializationContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface SettingDataManager {

    Collection<SettingData> getSettings();

    void loadData(@NotNull String path);

    void removeData();

}
