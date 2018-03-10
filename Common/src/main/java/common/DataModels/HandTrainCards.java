package common.DataModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jhens on 3/10/2018.
 */

public class HandTrainCards implements Serializable {

    private List<TrainCard> trainCards;

    public HandTrainCards(List<TrainCard> trainCards)
    {
        this.trainCards = trainCards;
    }

    public HandTrainCards()
    {
        trainCards = new ArrayList<>();
    }

    public List<TrainCard> getTrainCards() { return trainCards; }

    public void add(TrainCard destinationCard) { trainCards.add(destinationCard); }

    public TrainCard get(int position) { return trainCards.get(position); }

    public void addAll(Collection<TrainCard> cards) { trainCards.addAll(cards); }

    public void addAll(HandTrainCards cards) { trainCards.addAll(cards.getTrainCards()); }

    public TrainCard[] toArray() { return trainCards.toArray(new TrainCard[size()]); }

    public int size() { return trainCards.size(); }
}
