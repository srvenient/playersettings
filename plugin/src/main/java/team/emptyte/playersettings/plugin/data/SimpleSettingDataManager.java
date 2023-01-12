package team.emptyte.playersettings.plugin.data;

import org.bukkit.configuration.file.FileConfiguration;
import team.emptyte.playersettings.api.data.SettingData;
import team.emptyte.playersettings.api.data.SettingDataManager;
import team.emptyte.playersettings.api.message.MessageHandler;
import team.emptyte.playersettings.api.storage.ModelService;

import java.util.Objects;

public class SimpleSettingDataManager implements SettingDataManager {

    private final FileConfiguration config;
    private final MessageHandler messageHandler;
    private final ModelService<SettingData> settings;

    public SimpleSettingDataManager(
            FileConfiguration config,
            MessageHandler messageHandler,
            ModelService<SettingData> settings) {
        this.config = config;
        this.messageHandler = messageHandler;
        this.settings = settings;
    }

    @Override
    public void load() {
        String DEFAULT_PATH = "menu.settings.";

        Objects.requireNonNull(config.getConfigurationSection("menu.settings")).getKeys(false)
                .forEach(key -> {
                    if (settings.findSync(key) == null) {
                        settings.saveSync(
                                SettingData.builder(key)
                                        .setItem(messageHandler.getItem(DEFAULT_PATH + key))
                                        .setSlot(config.getInt(DEFAULT_PATH + key + ".slot"))
                                        .setPermission(config.getString("config.menu.permissions." + key))
                                        .setSecondItem(config.getBoolean("config.menu.item-state-enabled"))
                                        .build()
                        );
                    }
                });
    }

    @Override
    public void remove() {

    }
}
