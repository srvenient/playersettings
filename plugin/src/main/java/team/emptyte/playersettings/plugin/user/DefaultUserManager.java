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
            User newUser = new User(id);
            newRegisterSettings(newUser);

            users.saveSync(newUser, true);
        }
    }

    @Override
    public void save(String id) {
        User user = users.findSync(id);

        if (user != null) {
            users.updateSync(user);
        }
    }

    private void newRegisterSettings(User user) {
        Map<String, Integer> settings = new HashMap<>();

        settings.put("visibility", 0);
        settings.put("chat", 0);
        settings.put("doublejump", 1);
        settings.put("mount", 1);
        settings.put("fly", 1);

        user.setSettings(settings);
    }

}
