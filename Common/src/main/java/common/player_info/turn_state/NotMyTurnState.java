package common.player_info.turn_state;

import common.cards.HandDestinationCards;
import common.cards.TrainCard;
import common.map.Edge;
import common.player_info.Player;

/**
 * Created by Kavika F.
 */

public class NotMyTurnState implements ITurnState
{

	@Override
	public TrainCard drawFaceUp(Player player)
	{
		return null;
	}

	@Override
	public TrainCard drawFromDeck(TrainCard trainCard)
	{
		return null;
	}

	@Override
	public HandDestinationCards drawDestinationCards(Player player)
	{
		return null;
	}

	@Override
	public void claimEdge(Player player, Edge edge)
	{

	}

	@Override
	public void turnStarted(Player player)
	{

	}

	@Override
	public void turnEnded(Player player)
	{

	}
}
