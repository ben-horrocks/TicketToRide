package common.DataModels;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kavika F.
 */

public class HandDestinationCards implements Serializable
{
	private List<DestinationCard> destinationCards;

	public HandDestinationCards(List<DestinationCard> destinationCards)
	{
		this.destinationCards = destinationCards;
	}

	public List<DestinationCard> getDestinationCards() { return destinationCards; }

	public void addToDestinationCards(DestinationCard destinationCard) { destinationCards.add(destinationCard); }

	public DestinationCard get(int position) { return destinationCards.get(position); }
}
