package daos;

import java.util.List;

import common.player_info.Player;
import common.player_info.Username;

/**
 * Created by Ben_D on 4/9/2018.
 */

public class FlatPlayerDAO implements IPlayerDAO
{
    @Override
    public boolean addNewPlayer(Player player)
    {
        return false;
    }

    @Override
    public Player getPlayer(Username player)
    {
        return null;
    }

    @Override
    public List<Player> getAllPlayers()
    {
        return null;
    }

    @Override
    public boolean updatePlayer(Player player)
    {
        return false;
    }

    @Override
    public boolean deletePlayer(Player player)
    {
        return false;
    }
}
