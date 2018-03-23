package common.player_info.turn_state;

import java.io.Serializable;

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
	public void drawDestinationCards(Player player, HandDestinationCards pickedCards)
	{
		player.getDestinationCards().addAll(pickedCards);
		player.setTurnState(new NotMyTurn());
	}

	@Override
	public void claimEdge(Player player, Edge edge) {}

	@Override
	public void turnStarted(Player player) {}
}
