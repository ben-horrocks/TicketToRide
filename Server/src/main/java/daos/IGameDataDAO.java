package daos;

import java.util.List;

import common.game_data.ServerGameData;

/**
 * Created by Kavika F.
 */

public interface IGameDataDAO
{
	boolean addNewGameData(ServerGameData gameData);
	ServerGameData getGameData(ServerGameData gameData);
	List<ServerGameData> getAllGameData();
	boolean updateGameData(ServerGameData gameData);
	boolean deleteGameData(ServerGameData gameData);
}
