package common.player_info.turn_state;

import java.io.Serializable;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.map.Edge;
import common.player_info.Player;

/**
 * Created by Kavika F.
 */

public class DrewFaceUpCard implements ITurnState, Serializable
{
	@Override
	public void drawFaceUp(Player player, TrainCard trainCard)
	{
		if (trainCard.getType().equals(TrainColor.LOCOMOTIVE))
		{
			drawFaceUpLocomotive(player);
			return;
		}
		HandTrainCards hand = player.getHand();
		hand.add(trainCard);
		player.setTurnState(new NotMyTurn());
	}

	@Override
	public void drawFaceUpLocomotive(Player player) {}

	@Override
	public void drawFromDeck(Player player, TrainCard trainCard)
	{
		HandTrainCards hand = player.getHand();
		hand.add(trainCard);
		player.setTurnState(new NotMyTurn());
	}

	@Override
	public boolean drawDestinationCards(Player player, HandDestinationCards pickedCards, boolean isMyTurn) { return false; }

	@Override
	public void claimEdge(Player player, Edge edge) {}

	@Override
	public void turnStarted(Player player) {}
}
