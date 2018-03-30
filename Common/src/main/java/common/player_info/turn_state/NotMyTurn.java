package common.player_info.turn_state;

import common.cards.HandDestinationCards;
import java.io.Serializable;
import java.util.List;

import common.cards.TrainCard;
import common.map.Edge;
import common.player_info.Player;

public class NotMyTurn implements ITurnState, Serializable
{
	@Override
	public void drawFaceUp(Player player, TrainCard trainCard) {}

	@Override
	public void drawFaceUpLocomotive(Player player) {}

	@Override
	public void drawFromDeck(Player player, TrainCard trainCard) {}

	@Override
	public boolean drawDestinationCards(Player player, HandDestinationCards pickedCards, boolean isMyTurn) { return false; }

	@Override
	public void claimEdge(Player player, Edge edge, List<TrainCard> spent) {}

	@Override
	public void turnStarted(Player player) { player.setTurnState(new MyTurnNoAction()); }

	@Override
	public boolean canTakeAction() { return false; }

	@Override
	public boolean hasRestrictedAction() { return true; }
}
