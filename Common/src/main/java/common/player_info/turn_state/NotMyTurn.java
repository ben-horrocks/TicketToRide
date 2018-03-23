package common.player_info.turn_state;

import common.cards.TrainCard;
import common.map.Edge;
import common.player_info.Player;

public class NotMyTurn implements ITurnState
{
	@Override
	public void drawFaceUp(Player player, TrainCard trainCard) {}

	@Override
	public void drawFaceUpLocomotive(Player player) {}

	@Override
	public void drawFromDeck(Player player, TrainCard trainCard) {}

	@Override
	public void drawDestinationCards(Player player) {}

	@Override
	public void claimEdge(Player player, Edge edge) {}

	@Override
	public void turnStarted(Player player) { player.setTurnState(new MyTurnNoAction()); }
}