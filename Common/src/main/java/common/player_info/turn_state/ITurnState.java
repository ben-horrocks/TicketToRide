package common.player_info.turn_state;

import java.util.List;

import common.cards.HandDestinationCards;
import common.cards.TrainCard;
import common.map.Edge;
import common.player_info.Player;

public interface ITurnState
{
	void drawFaceUp(Player player, TrainCard trainCard);

	void drawFaceUpLocomotive(Player player);

	void drawFromDeck(Player player, TrainCard trainCard);

	boolean drawDestinationCards(Player player, HandDestinationCards pickedCards, boolean isMyTurn);

	void claimEdge(Player player, Edge edge, List<TrainCard> spent);

	void turnStarted(Player player);

	boolean canTakeAction();
}
