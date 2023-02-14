package com.srvenient.playersettings.storage;

import com.srvenient.playersettings.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRemoteModelService implements ModelService<User> {

    private final Connection connection;

    public UserRemoteModelService(Connection connection) {
        this.connection = connection;

        try {
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
    public @Nullable User findSync(@NotNull String id) {
        try {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM playersettings_data " +
                    "WHERE id=?");
            statement.setString(1, id);

            final ResultSet result = statement.executeQuery();
            if (result.next()) {
                final Map<String, Byte> settings = new HashMap<>();

                settings.put("visibility", (byte) result.getInt("visibility"));
                settings.put("chat", (byte) result.getInt("chat"));
                settings.put("jump", (byte) result.getInt("jump"));
                settings.put("mount", (byte) result.getInt("mount"));
                settings.put("fly", (byte) result.getInt("fly"));

                return new User(id, settings);
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public @Nullable List<User> findAllSync() {
        return null;
    }

    @Override
    public void saveSync(@NotNull User model) {
        try {
            PreparedStatement statement;

            if (findSync(model.getId()) == null) {
                statement = connection.prepareStatement(
                        "INSERT INTO playersettings_data (id,visibility,chat,jump,mount,fly) VALUE (?,?,?,?,?,?)"
                );

                statement.setString(1, model.getId());
                statement.setInt(2, model.getSettingState("visibility"));
                statement.setInt(3, model.getSettingState("chat"));
                statement.setInt(4, model.getSettingState("jump"));
                statement.setInt(5, model.getSettingState("mount"));
                statement.setInt(6, model.getSettingState("fly"));

                statement.executeUpdate();
            } else {
                statement = connection.prepareStatement(
                        "UPDATE playersettings_data SET visibility=?, chat=?, jump=?, mount=?, fly=? WHERE (id=?)"
                );

                statement.setInt(1, model.getSettingState("visibility"));
                statement.setInt(2, model.getSettingState("chat"));
                statement.setInt(3, model.getSettingState("jump"));
                statement.setInt(4, model.getSettingState("mount"));
                statement.setInt(5, model.getSettingState("fly"));
                statement.setString(6, model.getId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateSync(@NotNull User model) {
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    "UPDATE playersettings_data SET visibility=?, chat=?, jump=?, mount=?, fly=? WHERE (id=?)"
            );

            statement.setInt(1, model.getSettingState("visibility"));
            statement.setInt(2, model.getSettingState("chat"));
            statement.setInt(3, model.getSettingState("jump"));
            statement.setInt(4, model.getSettingState("mount"));
            statement.setInt(5, model.getSettingState("fly"));
            statement.setString(6, model.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User deleteSync(@NotNull User model) {
        deleteSync(model.getId());

        return model;
    }

    @Override
    public void deleteSync(@NotNull String id) {
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM playersettings_data WHERE id=?"
            );

            statement.setString(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
