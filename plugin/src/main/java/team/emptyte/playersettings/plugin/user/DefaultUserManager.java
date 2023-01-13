package team.emptyte.playersettings.plugin.user;

import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.user.User;
import team.emptyte.playersettings.api.user.UserManager;

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
        Map<String, Byte> settings = new HashMap<>();

        settings.put("visibility", (byte) 0);
        settings.put("chat", (byte) 0);
        settings.put("double-jump", (byte) 1);
        settings.put("mount", (byte) 1);
        settings.put("fly", (byte) 1);

        user.setSettings(settings);
    }

}
