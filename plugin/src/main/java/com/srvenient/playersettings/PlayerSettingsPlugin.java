package com.srvenient.playersettings;

import com.srvenient.playersettings.client.SQLClient;
import com.srvenient.playersettings.command.SettingCommand;
import com.srvenient.playersettings.data.SettingDataManager;
import com.srvenient.playersettings.data.SettingLayoutManagerImpl;
import com.srvenient.playersettings.executor.SettingExecutorManager;
import com.srvenient.playersettings.executor.SettingExecutorManagerImpl;
import com.srvenient.playersettings.listener.*;
import com.srvenient.playersettings.user.UserHandler;
import com.srvenient.playersettings.user.UserHandlerImpl;
import com.srvenient.playersettings.user.UserManager;
import com.srvenient.playersettings.user.UserManagerImpl;
import com.srvenient.playersettings.user.sql.UserSQLManager;
import com.srvenient.playersettings.user.sql.UserSQLManagerImpl;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerSettingsPlugin extends JavaPlugin {

    private UserSQLManager userSQLManager;
    private UserManager userManager;
    private SettingExecutorManager settingExecutorManager;
    private SettingDataManager settingDataManager;
    private UserHandler userHandler;

    @Override
    public void onLoad() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        final SQLClient client;

        try {
            client = new SQLClient.Builder("mysql")
                    .setHost(getConfig().getString("config.database.host"))
                    .setPort(getConfig().getInt("config.database.port"))
                    .setDatabase(getConfig().getString("config.database.database"))
                    .setUsername(getConfig().getString("config.database.username"))
                    .setPassword(getConfig().getString("config.database.password"))
                    .setMaximumPoolSize(10)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.userSQLManager = new UserSQLManagerImpl(client);
        this.userManager = new UserManagerImpl(userSQLManager, getConfig());
        this.settingExecutorManager = new SettingExecutorManagerImpl(this);
        this.settingDataManager = new SettingLayoutManagerImpl(getConfig(), settingExecutorManager);
        this.userHandler = new UserHandlerImpl(this, settingDataManager, userManager);
    }

    @Override
    public void onEnable() {
        settingDataManager.loadData("menu.settings");

        List<Listener> listeners = new ArrayList<>();

        listeners.add(new InventoryClickListener());
        listeners.add(new InventoryOpenListener());
        listeners.add(new InventoryCloseListener(this));

        listeners.add(new AsyncPlayerChatListener(userManager, userHandler, getConfig()));
        listeners.add(new PlayerChangedWorldListener(userManager, userHandler));
        listeners.add(new PlayerInteractAtEntityListener(userManager, userHandler, getConfig()));
        listeners.add(new PlayerJoinListener(userManager, userHandler));
        listeners.add(new PlayerQuitListener(userManager));

        listen(listeners);

        registerCommand("settings", new SettingCommand(getConfig(), userHandler));
    }

    @Override
    public void onDisable() {
        settingDataManager.removeData();
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
