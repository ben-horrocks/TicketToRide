package common.cards;

import java.io.Serializable;
import java.util.*;

public class HandTrainCards implements Serializable
{

    private List<TrainCard> trainCards;
    private Map<TrainColor, Integer> trainColorCounts = new HashMap<>();

    public HandTrainCards(List<TrainCard> trainCards)
    {
        this.trainCards = trainCards;
        updateMap();
    }

    public HandTrainCards()
    {
        trainCards = new ArrayList<>();
        updateMap();
    }

    private void updateMap()
    {
        trainColorCounts.clear();
        for (TrainCard card : trainCards)
        {
            addToMap(card.getType());
        }
    }

    public List<TrainCard> getTrainCards()
    {
        return trainCards;
    }

    public Map<TrainColor, Integer> getColorCounts()
    {
        return trainColorCounts;
    }

    private void addToMap(TrainColor color)
    {
        if (trainColorCounts.keySet().contains(color))
        {
            int current = trainColorCounts.get(color);
            current += 1;
            trainColorCounts.put(color, current);
        } else
        {
            trainColorCounts.put(color, 1);
        }
    }

    public void add(TrainCard destinationCard)
    {
        trainCards.add(destinationCard);
        updateMap();
    }

    public TrainCard get(int position)
    {
        return trainCards.get(position);
    }

    public void addAll(Collection<TrainCard> cards)
    {
        trainCards.addAll(cards);
        updateMap();
    }

    public void addAll(HandTrainCards cards)
    {
        trainCards.addAll(cards.getTrainCards());
        updateMap();
    }

    public boolean remove(TrainCard card)
    {
        boolean temp = trainCards.remove(card);
        updateMap();
        return temp;
    }

    public TrainCard remove(int position)
    {
        TrainCard card = trainCards.remove(position);
        updateMap();
        return card;
    }

    public boolean removeAll(Collection<TrainCard> collection)
    {
        boolean temp = trainCards.removeAll(collection);
        updateMap();
        return temp;
    }

    public TrainCard[] toArray()
    {
        return trainCards.toArray(new TrainCard[size()]);
    }

    public int size()
    {
        return trainCards.size();
    }
}
