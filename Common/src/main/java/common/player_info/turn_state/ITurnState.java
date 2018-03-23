package common.player_info.turn_state;

import common.cards.TrainCard;
import common.map.Edge;
import common.player_info.Player;

public interface ITurnState
{
	void drawFaceUp(Player player, TrainCard trainCard);

	void drawFaceUpLocomotive(Player player);

	void drawFromDeck(Player player, TrainCard trainCard);

	void drawDestinationCards(Player player);

	void claimEdge(Player player, Edge edge);

	void turnStarted(Player player);
}
