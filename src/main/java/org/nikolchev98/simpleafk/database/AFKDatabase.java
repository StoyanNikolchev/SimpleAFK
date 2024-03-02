package org.nikolchev98.simpleafk.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

import static org.nikolchev98.simpleafk.enums.Constants.*;

public class AFKDatabase {
    private final Connection connection;

    public AFKDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        Statement statement = connection.createStatement();
        statement.execute(CREATE_DB);
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void addPlayer(Player player) throws SQLException {
        if (!playerExists(player)) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(ADD_PLAYER_TO_DB);

            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setBoolean(2, true);
            preparedStatement.executeUpdate();
        }
    }

    public boolean playerExists(Player player) throws SQLException {
        return getPlayerResult(player).next();
    }

    private boolean playerHasMessagesEnabled(Player player) throws SQLException {
        return getPlayerResult(player).getBoolean(2);
    }

    public boolean triggerPlayerMessages(Player player) throws SQLException {
        boolean newStatus = !playerHasMessagesEnabled(player);

        PreparedStatement preparedStatement =
                connection.prepareStatement(UPDATE_PLAYER_MESSAGES_QUERY);

        preparedStatement.setBoolean(1, newStatus);
        preparedStatement.setString(2, player.getUniqueId().toString());
        preparedStatement.executeUpdate();
        return newStatus;
    }

    public Set<Player> getOnlinePlayersWithEnabledMessages() {
        Set<Player> result = new HashSet<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_UUIDS_WITH_ENABLED_MESSAGES_QUERY)) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                preparedStatement.setString(1, player.getUniqueId().toString());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        result.add(Bukkit.getPlayer(UUID.fromString(resultSet.getString("uuid"))));
                    }
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                }
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return result;
    }

    private ResultSet getPlayerResult(Player player) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_PLAYER_QUERY);
        preparedStatement.setString(1, player.getUniqueId().toString());
        return preparedStatement.executeQuery();
    }
}
