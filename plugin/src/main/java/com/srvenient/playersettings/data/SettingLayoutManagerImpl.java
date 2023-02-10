package com.srvenient.playersettings.data;


import com.srvenient.playersettings.executor.SettingExecutorManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.srvenient.playersettings.utils.ItemUtils.getItem;


public class SettingLayoutManagerImpl implements SettingDataManager {

    private final Map<String, SettingData> settingDataMap;
    private final FileConfiguration config;
    private final SettingExecutorManager executorManager;

    public SettingLayoutManagerImpl(
            FileConfiguration config,
            SettingExecutorManager executorManager
    ) {
        this.settingDataMap = new HashMap<>(5);
        this.config = config;
        this.executorManager = executorManager;
    }

    @Override
    public Collection<SettingData> getSettings() {
        return settingDataMap.values();
    }

    @Override
    public void uploadSettings(@NotNull String path) {
        config.getConfigurationSection(path).getKeys(false)
                .forEach(id -> {
                    if (settingDataMap.get(id) != null) {
                        return;
                    }

                    settingDataMap.put(
                            id, new SettingData(
                                    id,
                                    Objects.requireNonNull(config.getString(path + id + ".permission")),
                                    getItem(
                                            Objects.requireNonNull(config.getString(path + id + ".material")),
                                            Objects.requireNonNull(config.getString(path + id + ".displayName")),
                                            config.getStringList(path + id + ".lore"),
                                            config.getInt(path + id + ".amount")
                                    ),
                                    config.getInt(path + id + ".slot"),
                                    executorManager.validExecutor(id)
                            )
                    );
                });
    }



}
