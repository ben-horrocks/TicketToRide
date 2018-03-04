package cs340.TicketClient.Game;

import java.util.List;

import common.DataModels.DestinationCard;
import common.DataModels.EdgeGraph;
import common.DataModels.GameData.Opponent;
import common.DataModels.TrainCard;

public class GameModel
{
	List<Opponent> opponents;
	EdgeGraph gameBoard;
	List<TrainCard> faceUp;
	// History history;
	// Chat chat;

	boolean isValidDesinationCard(DestinationCard destinationCard)
	{
		// TODO: implement isValidDestinationCard
		return false;
	}

	boolean canDrawCard(TrainCard trainCard)
	{
		// TODO: implement canDrawCard
		return false;
	}
}
