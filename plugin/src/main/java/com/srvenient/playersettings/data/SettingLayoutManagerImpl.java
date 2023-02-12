package com.srvenient.playersettings.data;

import com.srvenient.playersettings.executor.SettingExecutorManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.srvenient.playersettings.utils.ItemUtils.getItem;

public class SettingLayoutManagerImpl implements SettingDataManager {

    private final Map<String, SettingData> settingDataMap;
    private final SettingExecutorManager executorManager;
    private final FileConfiguration configuration;

    public SettingLayoutManagerImpl(
            FileConfiguration config,
            SettingExecutorManager executorManager
    ) {
        this.settingDataMap = new HashMap<>(5);
        this.executorManager = executorManager;
        this.configuration = config;
    }

    @Override
    public Collection<SettingData> getSettings() {
        return settingDataMap.values();
    }

    @Override
    public void loadData(@NotNull String path) {
        final ConfigurationSection section = configuration.getConfigurationSection(path);

        for (String settingId :
                section.getKeys(false)) {

            final SettingData data = settingDataMap.get(settingId);

            if (data != null) {
                return;
            }

            settingDataMap.put(
                    settingId, new SettingData(
                            settingId,
                            Objects.requireNonNull(configuration.getString(path + "." + settingId + ".permission")),
                            getItem(
                                    Objects.requireNonNull(configuration.getString(path + "." +  settingId + ".material")),
                                    Objects.requireNonNull(configuration.getString(path + "." +  settingId + ".displayName")),
                                    configuration.getStringList(path + "." +  settingId + ".lore"),
                                    configuration.getInt(path + "." + settingId + ".amount")
                            ),
                            configuration.getInt(path + "." + settingId + ".slot"),
                            executorManager.validExecutor(settingId)
                    )
            );
        }
    }

    @Override
    public void removeData() {
        settingDataMap.clear();
    }


}
