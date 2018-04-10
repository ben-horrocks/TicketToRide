package daos;

import java.util.List;

import common.communication.Command;
import common.game_data.GameID;

/**
 * Created by Ben_D on 4/9/2018.
 */

public class FlatCommandDAO implements ICommandDAO
{
    @Override
    public boolean addNewCommand(Command command)
    {
        return false;
    }

    @Override
    public List<Command> getCommandsByGameId(GameID id)
    {
        return null;
    }

    @Override
    public boolean deleteCommandsByGameId(GameID id)
    {
        return false;
    }
}
