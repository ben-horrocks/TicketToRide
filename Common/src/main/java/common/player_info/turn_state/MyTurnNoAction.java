package common.player_info.turn_state;

import common.cards.HandDestinationCards;
import java.io.Serializable;
import java.util.List;

import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.map.Edge;
import common.map.EdgeGraph;
import common.player_info.Player;

/**
 * Created by Kavika F.
 */

public class MyTurnNoAction implements ITurnState, Serializable
{
	@Override
	public void drawFaceUp(Player player, TrainCard trainCard)
	{
		if (trainCard.getType().equals(TrainColor.LOCOMOTIVE))
		{
			drawFaceUpLocomotive(player);
		}
		HandTrainCards hand = player.getHand();
		hand.add(trainCard);
		player.setTurnState(new DrewFaceUpCard());
	}

	@Override
	public void drawFaceUpLocomotive(Player player)
	{
		HandTrainCards hand = player.getHand();
		hand.add(new TrainCard(TrainColor.LOCOMOTIVE));
		player.setTurnState(new NotMyTurn());
	}

	@Override
	public void drawFromDeck(Player player, TrainCard trainCard)
	{
		HandTrainCards hand = player.getHand();
		hand.add(trainCard);
		player.setTurnState(new DrewDeckCard());
	}

	@Override
	public boolean drawDestinationCards(Player player, HandDestinationCards pickedCards, boolean isMyTurn)
	{
		player.getDestinationCards().addAll(pickedCards);
		player.setTurnState(new NotMyTurn());

		return true;
	}

	@Override
	public void claimEdge(Player player, Edge edge, List<TrainCard> spent)
	{
		player.getClaimedEdges().addEdge(edge);
		player.checkDestinationCards();
		player.getHand().getTrainCards().removeAll(spent);
		player.getPieces().useTrainPieces(spent.size());
		player.addPoints(edge.computePointValue());
		player.setTurnState(new NotMyTurn());
	}

	@Override
	public void turnStarted(Player player) {}

	@Override
	public boolean canTakeAction() { return true; }
}
