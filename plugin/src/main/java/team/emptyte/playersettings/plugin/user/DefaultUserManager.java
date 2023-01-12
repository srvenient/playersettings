package team.emptyte.playersettings.plugin.user;

import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.user.User;
import team.emptyte.playersettings.api.user.UserManager;
import team.emptyte.playersettings.api.user.internal.SettingStorage;

import java.util.HashMap;
import java.util.Map;

public class DefaultUserManager implements UserManager {

    private final ModelService<User> users;

    public DefaultUserManager(ModelService<User> users) {
        this.users = users;
    }


    @Override
    public void load(String id) {
        User user = users.findSync(id);

        if (user == null) {
            user = new User(id);

            users.saveSync(user);
            registerSettings(user);

            return;
        }
    }

    @Override
    public void save(String id) {

    }

    private void registerSettings(User user) {
        Map<String, SettingStorage> settings = new HashMap<>();

        settings.put("visibility", new SettingStorage("visibility", (byte) 0));
        settings.put("chat", new SettingStorage("chat", (byte) 0));
        settings.put("double-jump", new SettingStorage("double-jump", (byte) 1));
        settings.put("mount", new SettingStorage("mount", (byte) 1));
        settings.put("fly", new SettingStorage("fly", (byte) 1));

        user.setSettings(settings);
    }

}
