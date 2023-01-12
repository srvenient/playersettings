package team.emptyte.playersettings.plugin;

import org.bukkit.Bukkit;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import team.emptyte.playersettings.api.SettingHandler;
import team.emptyte.playersettings.api.data.SettingData;
import team.emptyte.playersettings.api.data.SettingDataManager;
import team.emptyte.playersettings.api.message.MessageHandler;
import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.storage.dist.LocalModelService;
import team.emptyte.playersettings.api.user.User;
import team.emptyte.playersettings.api.user.UserManager;
import team.emptyte.playersettings.plugin.command.SettingCommand;
import team.emptyte.playersettings.plugin.data.SimpleSettingDataManager;
import team.emptyte.playersettings.plugin.listener.AsyncPlayerChatListener;
import team.emptyte.playersettings.plugin.listener.PlayerJoinListener;
import team.emptyte.playersettings.plugin.listener.PlayerMoveListener;
import team.emptyte.playersettings.plugin.listener.PlayerToggleFlightListener;
import team.emptyte.playersettings.plugin.message.DefaultMessageHandler;
import team.emptyte.playersettings.plugin.user.DefaultUserManager;
import team.unnamed.gui.menu.listener.InventoryClickListener;
import team.unnamed.gui.menu.listener.InventoryCloseListener;
import team.unnamed.gui.menu.listener.InventoryOpenListener;

import java.util.ArrayList;
import java.util.List;

public class PlayerSettingsPlugin extends JavaPlugin {

    private MessageHandler messageHandler;
    private SettingHandler settingHandler;
    private UserManager userManager;
    private SettingDataManager settingDataManager;
    private ModelService<User> users;

    @Override
    public void onLoad() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        messageHandler = new DefaultMessageHandler(getConfig());

        users = LocalModelService.concurrent();
        ModelService<SettingData> settings = LocalModelService.hashMap();

        settingHandler = new DefaultSettingHandler(
                this, getConfig(), messageHandler, users, settings
        );
        userManager = new DefaultUserManager(users);
        settingDataManager = new SimpleSettingDataManager(
                getConfig(), messageHandler, settings
        );
    }

    @Override
    public void onEnable() {
        settingDataManager.load();

        List<Listener> listeners = new ArrayList<>();

        listeners.add(new PlayerJoinListener(userManager));
        listeners.add(new PlayerToggleFlightListener(users));
        listeners.add(new PlayerMoveListener(users));
        listeners.add(new AsyncPlayerChatListener(messageHandler, users));
        listeners.add(new InventoryClickListener());
        listeners.add(new InventoryOpenListener());
        listeners.add(new InventoryCloseListener(this));

        listen(listeners);
        registerCommand("settings", new SettingCommand(messageHandler, settingHandler));
    }

    @Override
    public void onDisable() {
        settingDataManager.remove();
    }

    private <T extends CommandExecutor & TabCompleter> void registerCommand(
            @NotNull String name, T command
    ) {
        PluginCommand pluginCommand = getCommand(name);
        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }

    private void listen(List<Listener> listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }
}