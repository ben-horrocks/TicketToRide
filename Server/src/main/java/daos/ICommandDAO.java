package daos;

import java.util.List;

import common.communication.Command;

/**
 * Created by Kavika F.
 */

public interface ICommandDAO
{
	boolean addNewCommand(Command command);
	List<Command> getAllCommands(Command command);
}
