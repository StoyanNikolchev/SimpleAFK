package org.nikolchev98.simpleafk.enums;

public enum Constants {
    ;
    public static final String LIST_OF_AFK_FORMAT = "Players currently AFK: %s";
    public static final String NO_LONGER_AFK_FORMAT = "%s is no longer AFK!";
    public static final String NO_SUCH_PLAYER = "No such player online!";
    public static final String IS_NOW_AFK_FORMAT = "%s is now AFK!";
    public static final String YOU_ARE_AFK = "You are now AFK!";
    public static final String YOU_ARE_NOT_AFK = "You are no longer AFK!";
    public static final String IS_NOT_AFK = "%s is not AFK!";
    public static final String IS_AFK_FORMAT = "%s is AFK!";
    public static final String INVALID_COMMAND = "Invalid command structure.";
    public static final Long AFK_TRIGGER_MILLISECONDS = 300000L;
    public static final String SENDER_MUST_BE_PLAYER = "Sender must be a player!";
    public static final String ENABLED_MESSAGES_FORMAT = "AFK messages are now %senabled!";
    public static final String DISABLED_MESSAGES_FORMAT = "AFK messages are now %sdisabled!";
    public static final String FAILED_TO_ADD_PLAYER = "Failed to add player to database. Plugin will not work correctly!";
    public static final String GET_UUIDS_WITH_ENABLED_MESSAGES_QUERY = """
            SELECT * FROM afk_db
            WHERE has_afk_messages_enabled = true
            AND uuid = ?
            """;
    public static final String UPDATE_PLAYER_MESSAGES_QUERY = """
                        UPDATE afk_db
                        SET has_afk_messages_enabled = ?
                        WHERE uuid = ?
                        """;
    public static final String ADD_PLAYER_TO_DB = """
                            INSERT INTO afk_db (uuid, has_afk_messages_enabled)
                            VALUES (?, ?)
                            """;
    public static final String CREATE_DB = """
                CREATE TABLE IF NOT EXISTS afk_db (
                uuid TEXT PRIMARY KEY,
                has_afk_messages_enabled boolean NOT NULL);
                """;
    public static final String GET_PLAYER_QUERY = "SELECT * FROM afk_db WHERE uuid = ?";
}
