package common.player_info.turn_state;

import common.cards.HandDestinationCards;
import common.cards.TrainCard;
import common.map.Edge;
import common.player_info.Player;

public interface ITurnState
{
	TrainCard drawFaceUp(Player player);

	TrainCard drawFromDeck(TrainCard trainCard);

	HandDestinationCards drawDestinationCards(Player player);

	void claimEdge(Player player, Edge edge);

	void turnStarted(Player player);

	void turnEnded(Player player);
}
