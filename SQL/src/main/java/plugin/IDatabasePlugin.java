package plugin;

import java.util.List;

import common.communication.Command;
import common.game_data.GameID;
import common.game_data.ServerGameData;
import common.player_info.User;
import common.player_info.Username;

public interface IDatabasePlugin {
    IDatabasePlugin accessDatabase();

    boolean initializeDatabase(boolean cleanSlate);

    User getUser(Username name);
    List<User> getAllUsers();
    boolean addUser(User user);
    boolean deleteUser(Username name);
    boolean updateUser(User user);

    ServerGameData getGame(GameID id);
    List<ServerGameData> getRunningGames();
    List<ServerGameData> getOpenGames();
    List<ServerGameData> getAllGames();
    List<ServerGameData> getRunningGames(Username user);
    boolean addGame(ServerGameData game);
    boolean deleteGame(GameID id);
    boolean updateGame(ServerGameData game);

    List<Command> getCommands(GameID id);
    boolean addCommand(Command command);
    /* No Delete commands because the database should handle that by itself */
}
