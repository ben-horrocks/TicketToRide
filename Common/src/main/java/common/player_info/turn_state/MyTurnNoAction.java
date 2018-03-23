package common.player_info.turn_state;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.map.Edge;
import common.map.EdgeGraph;
import common.player_info.Player;

/**
 * Created by Kavika F.
 */

public class MyTurnNoAction implements ITurnState
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
	public void drawDestinationCards(Player player, HandDestinationCards pickedCards)
	{
		player.getDestinationCards().addAll(pickedCards);
		player.setTurnState(new NotMyTurn());
	}

	@Override
	public void claimEdge(Player player, Edge edge)
	{
		EdgeGraph edgeGraph = player.getClaimedEdges();
		edgeGraph.addEdge(edge);
		player.setTurnState(new NotMyTurn());
	}

	@Override
	public void turnStarted(Player player) {}
}
