package com.srvenient.playersettings;

import com.srvenient.playersettings.command.SettingCommand;
import com.srvenient.playersettings.data.SettingDataManager;
import com.srvenient.playersettings.data.SettingLayoutManagerImpl;
import com.srvenient.playersettings.executor.SettingExecutorManager;
import com.srvenient.playersettings.executor.SettingExecutorManagerImpl;
import com.srvenient.playersettings.user.UserHandler;
import com.srvenient.playersettings.user.UserHandlerImpl;
import com.srvenient.playersettings.user.UserManager;
import com.srvenient.playersettings.user.UserManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.unnamed.gui.menu.listener.InventoryClickListener;
import team.unnamed.gui.menu.listener.InventoryCloseListener;
import team.unnamed.gui.menu.listener.InventoryOpenListener;

import java.util.ArrayList;
import java.util.List;

public class PlayerSettingsPlugin extends JavaPlugin {

    private UserManager userManager;
    private SettingExecutorManager settingExecutorManager;
    private SettingDataManager settingDataManager;
    private UserHandler userHandler;

    @Override
    public void onLoad() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        this.userManager = new UserManagerImpl();
        this.settingExecutorManager = new SettingExecutorManagerImpl(this);
        this.settingDataManager = new SettingLayoutManagerImpl(getConfig(), settingExecutorManager);
        this.userHandler = new UserHandlerImpl(getConfig(), settingDataManager, userManager);
    }

    @Override
    public void onEnable() {
        settingDataManager.uploadSettings("menu.settings.");

        List<Listener> listeners = new ArrayList<>();

        listeners.add(new InventoryClickListener());
        listeners.add(new InventoryOpenListener());
        listeners.add(new InventoryCloseListener(this));

        listen(listeners);

        registerCommand("settings", new SettingCommand(getConfig(), userHandler));
    }

    @Override
    public void onDisable() {
        super.onDisable();
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
