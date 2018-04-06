package daos;

import java.util.List;

import common.player_info.Player;
import common.player_info.Username;

/**
 * Created by Kavika F.
 */

public interface IPlayerDAO
{
	boolean addNewPlayer(Player player);
	Player getPlayer(Username player);
	List<Player> getAllPlayers();
	boolean updatePlayer(Player player);
	boolean deletePlayer(Player player);
}
