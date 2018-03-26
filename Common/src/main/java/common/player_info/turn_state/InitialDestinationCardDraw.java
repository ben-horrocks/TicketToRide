package common.player_info.turn_state;

import java.io.Serializable;
import java.util.List;

import common.cards.HandDestinationCards;
import common.cards.TrainCard;
import common.map.Edge;
import common.player_info.Player;

/**
 * Created by Kavika F.
 */

public class InitialDestinationCardDraw implements ITurnState, Serializable
{
	@Override
	public void drawFaceUp(Player player, TrainCard trainCard) {}

	@Override
	public void drawFaceUpLocomotive(Player player) {}

	@Override
	public void drawFromDeck(Player player, TrainCard trainCard) {}

	@Override
	public boolean drawDestinationCards(Player player, HandDestinationCards pickedCards, boolean isMyTurn)
	{
		player.getDestinationCards().addAll(pickedCards);
		player.setTurnState(new NotMyTurn());
		if (isMyTurn)
		{
			player.setTurnState(new MyTurnNoAction());
		}

		return false;
	}

	@Override
	public void claimEdge(Player player, Edge edge, List<TrainCard> spent) {}

	@Override
	public void turnStarted(Player player) {}

	@Override
	public boolean canTakeAction() { return true; }
}
