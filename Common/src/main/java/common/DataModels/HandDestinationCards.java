package common.DataModels;

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
        return destinationCards.get(position);
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
}
