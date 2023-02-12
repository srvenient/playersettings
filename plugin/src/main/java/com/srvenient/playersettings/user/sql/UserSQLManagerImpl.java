package com.srvenient.playersettings.user.sql;

import com.srvenient.playersettings.client.SQLClient;
import com.srvenient.playersettings.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserSQLManagerImpl implements UserSQLManager {

    private final SQLClient client;

    public UserSQLManagerImpl(
            SQLClient client
    ) {
        this.client = client;

        try (Connection connection = client.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(
                            "CREATE TABLE IF NOT EXISTS playersettings_data (`id` varchar(200), " +
                                    "`visibility` INT(5), `chat` INT(5), `jump` INT(5), `mount` INT(5), `fly` INT(5))");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getPlayer(UUID uuid) {
        try (Connection connection = client.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM playersettings_data WHERE id=?");
            statement.setString(1, uuid.toString());

            final ResultSet result = statement.executeQuery();
            if (result.next()) {
                final Map<String, Byte> settings = new HashMap<>();

                settings.put("visibility", (byte) result.getInt("visibility"));
                settings.put("chat", (byte) result.getInt("chat"));
                settings.put("jump", (byte) result.getInt("jump"));
                settings.put("mount", (byte) result.getInt("mount"));
                settings.put("fly", (byte) result.getInt("fly"));

                return new User(uuid, settings);
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveUser(User user) {
        try (Connection connection = client.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO playersettings_data (id,visibility,chat,jump,mount,fly) VALUE (?,?,?,?,?,?)"
            );

            statement.setString(1, user.id().toString());
            statement.setInt(2, user.getSettingState("visibility"));
            statement.setInt(3, user.getSettingState("chat"));
            statement.setInt(4, user.getSettingState("jump"));
            statement.setInt(5, user.getSettingState("mount"));
            statement.setInt(6, user.getSettingState("fly"));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateUser(User user) {

        try (Connection connection = client.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "UPDATE playersettings_data SET visibility=?, chat=?, jump=?, mount=?, fly=? WHERE (id=?)"
            );

            statement.setInt(1, user.getSettingState("visibility"));
            statement.setInt(2, user.getSettingState("chat"));
            statement.setInt(3, user.getSettingState("jump"));
            statement.setInt(4, user.getSettingState("mount"));
            statement.setInt(5, user.getSettingState("fly"));
            statement.setString(6, user.id().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
