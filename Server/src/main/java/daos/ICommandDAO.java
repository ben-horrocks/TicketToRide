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
	List<Command> getCommandsByGameId(GameID id);
	boolean deleteCommandsByGameId(GameID id);
}
