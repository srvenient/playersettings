package team.emptyte.mercurysettings.plugin;

import org.bukkit.Bukkit;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import team.emptyte.mercurysettings.api.SettingHandler;
import team.emptyte.mercurysettings.api.data.SettingData;
import team.emptyte.mercurysettings.api.data.SettingDataManager;
import team.emptyte.mercurysettings.api.registry.KeyedRegistry;
import team.emptyte.mercurysettings.api.user.User;
import team.emptyte.mercurysettings.plugin.command.MainCommand;
import team.emptyte.mercurysettings.plugin.data.DefaultSettingDataManager;
import team.emptyte.mercurysettings.plugin.listener.PlayerJoinListener;
import team.emptyte.mercurysettings.plugin.listener.PlayerMoveListener;
import team.emptyte.mercurysettings.plugin.listener.PlayerToggleFlightListener;
import team.emptyte.mercurysettings.plugin.user.DefaultUserManager;
import team.unnamed.gui.menu.listener.InventoryClickListener;
import team.unnamed.gui.menu.listener.InventoryCloseListener;
import team.unnamed.gui.menu.listener.InventoryOpenListener;

import java.util.Objects;

public class MercurySettings extends JavaPlugin {

    private SettingDataManager settingDataManager;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        KeyedRegistry<User> users = new KeyedRegistry<>();
        KeyedRegistry<SettingData> settings = new KeyedRegistry<>();

        settingDataManager = new DefaultSettingDataManager(settings, getConfig());
        settingDataManager.register();

        SettingHandler settingHandler = new DefaultSettingHandler(this, users, settings);

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(new DefaultUserManager(users)), this);

        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new InventoryOpenListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(this), this);

        pluginManager.registerEvents(new PlayerToggleFlightListener(this, users), this);
        pluginManager.registerEvents(new PlayerMoveListener(users), this);

        Objects.requireNonNull(getCommand("mercurysettings")).setExecutor(new MainCommand(settingHandler));
        //Objects.requireNonNull(getCommand("options")).setExecutor(new MenuCommand(handler));
    }

    @Override
    public void onDisable() {
        settingDataManager.unregister();
    }
}
