package daos;

import java.util.List;

import common.communication.Command;
import common.game_data.GameID;

/**
 * Created by Kavika F.
 */

public interface ICommandDAO
{
	boolean addNewCommand(Command command);
	public List<Command> getCommandsByGameId(GameID id);
	public boolean deleteCommandsByGameId(GameID id);
}
