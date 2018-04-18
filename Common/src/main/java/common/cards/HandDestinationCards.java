package common.cards;

import java.io.Serializable;
import java.util.*;

public class HandDestinationCards implements Serializable
{
    private List<DestinationCard> destinationCards;

    public HandDestinationCards(List<DestinationCard> destinationCards)
    {
        this.destinationCards = destinationCards;
    }

    public HandDestinationCards()
    {
        destinationCards = new ArrayList<>();
    }

    public List<DestinationCard> getDestinationCards()
    {
        return destinationCards;
    }

    public void add(DestinationCard destinationCard)
    {
        destinationCards.add(destinationCard);
    }

    public DestinationCard get(int position)
    {
        try
        {
            return destinationCards.get(position);
        }catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    public void addAll(Collection<DestinationCard> cards)
    {
        destinationCards.addAll(cards);
    }

    public void addAll(HandDestinationCards cards)
    {
        destinationCards.addAll(cards.getDestinationCards());
    }

    public DestinationCard[] toArray()
    {
        return destinationCards.toArray(new DestinationCard[size()]);
    }

    public boolean remove(DestinationCard card)
    {
        return destinationCards.remove(card);
    }

    public DestinationCard remove(int position)
    {
        return destinationCards.remove(position);
    }

    public boolean removeAll(Collection<DestinationCard> collection)
    {
        return destinationCards.removeAll(collection);
    }

    public int size()
    {
        return destinationCards.size();
    }

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HandDestinationCards that = (HandDestinationCards) o;

		return destinationCards.equals(that.destinationCards);
	}

	@Override
	public int hashCode()
	{
		return destinationCards.hashCode();
	}
}
