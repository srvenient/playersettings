package team.emptyte.mercuryoptions.plugin;

import org.bukkit.Bukkit;

import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import team.emptyte.mercuryoptions.api.SettingHandler;
import team.emptyte.mercuryoptions.api.data.SettingData;
import team.emptyte.mercuryoptions.api.data.SettingDataManager;
import team.emptyte.mercuryoptions.api.registry.KeyedRegistry;
import team.emptyte.mercuryoptions.plugin.command.MainCommand;
import team.emptyte.mercuryoptions.plugin.data.DefaultSettingDataManager;
import team.unnamed.gui.menu.listener.InventoryClickListener;
import team.unnamed.gui.menu.listener.InventoryCloseListener;
import team.unnamed.gui.menu.listener.InventoryOpenListener;

public class MercurySettings extends JavaPlugin {

    private SettingDataManager settingDataManager;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        KeyedRegistry<SettingData> users = new KeyedRegistry<>();
        KeyedRegistry<SettingData> settings = new KeyedRegistry<>();

        settingDataManager = new DefaultSettingDataManager(settings, getConfig());
        settingDataManager.register();

        SettingHandler settingHandler = new DefaultSettingHandler(settings, getConfig());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new InventoryOpenListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(this), this);

        getCommand("mercurysettings").setExecutor(new MainCommand(settingHandler));
        //Objects.requireNonNull(getCommand("options")).setExecutor(new MenuCommand(handler));
    }

    @Override
    public void onDisable() {
        settingDataManager.unregister();
    }
}
