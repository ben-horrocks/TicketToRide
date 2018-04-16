package daos;

import java.util.List;

import common.game_data.GameID;
import common.player_info.Player;
import common.player_info.Username;

/**
 * Created by Kavika F.
 */

public interface IPlayerDAO
{
	boolean addNewPlayer(GameID game, Player player);
	Player getPlayer(GameID game, Username player);
	List<Player> getAllPlayers();
	List<Player> getAllPlayersInGame(GameID game);
	boolean updatePlayer(GameID game, Player player);
	boolean deletePlayer(GameID game, Player player);
}
