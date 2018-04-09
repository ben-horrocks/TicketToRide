package daos;

import java.util.List;

import common.communication.Command;

/**
 * Created by Kavika F.
 */

public interface ICommandDAO
{
	boolean addNewCommand(Command command);
	Command getCommand(Command command);
	List<Command> getAllCommands(Command command);
	boolean updateCommand(Command command);
	boolean deleteCommand(Command command);
}
