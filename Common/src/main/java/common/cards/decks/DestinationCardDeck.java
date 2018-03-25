package common.cards.decks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import common.cards.DestinationCard;
import common.map.CityName;

/**
 * <h1>Destination Card Deck</h1>
 * Class to store and interact with Destination Card Deck
 *
 * @author Ben Horrocks
 * @since 2018-03-12
 */
public class DestinationCardDeck implements Serializable
{
    /**
     * Object to store the List of Destination Cards.
     */
    private List<DestinationCard> deck;

    /**
     * Constructor for the deck.
     *
     * @pre N/A
     * @post A newly created & shuffled deck with all 30 destination cards
     */
    public DestinationCardDeck()
    {
        initializeDestinationDeck();
        shuffle();
    }

    /**
     * Draws 3 Destination Cards for the player to choose from.
     *
     * @return A list of 3 different Destination cards for the player to choose from.
     * @pre DestinationCardDeck.getDeckSize() > 3
     * @post Destination Card Deck is the same, except for the first 3 cards are removed
     */
    public List<DestinationCard> draw()
    {
        List<DestinationCard> drawnCards = new ArrayList<>();
        for (int x = 0; x < 3; x++)
        {
            drawnCards.add(deck.get(0));
            deck.remove(0);
        }
        return drawnCards;
    }

    /**
     * Shuffles the deck randomly
     *
     * @pre DestinationDeck.getDeckSize() > 0
     * @post Destination Deck contains the same elements in a different order
     */
    public void shuffle()
    {
        List<DestinationCard> newdeck = new ArrayList<>();
		Random random = new Random();
        while (deck.size() > 0)
        {
            int randomcard = random.nextInt(deck.size());
            newdeck.add(deck.get(randomcard));
            deck.remove(randomcard);
        }
        this.deck = newdeck;
    }

    /**
     * Returns a list of Destination Cards to the bottom of the deck.
     *
     * @param cards LIst of cards to return to the deck
     * @pre cards.size() > 0 && cards.size() < 3
     * @post The Destination Card Deck contains the same elements in the same order, with the Destination Cards in cards at the bottom of the deck
     */
    public void putBackInDeck(List<DestinationCard> cards)
    {
        for (DestinationCard card : cards)
        {
            deck.add(card);
        }
    }

    /**
     * Gets the number of DestinationCards in the DestinationDeck.
     *
     * @return number of cards in the Destination Deck
     * @pre none
     * @post DestinationDeck contains same number of cards in the same order
     */
    public int getDeckSize()
    {
        return deck.size();
    }

    /**
     * Function to initalize the destination Deck
     *
     * @pre none
     * @post deck contains exactly 30 new Destination Cards
     */
    private void initializeDestinationDeck()
    {
        List<DestinationCard> newDeck = new ArrayList<>();
        newDeck.add(new DestinationCard(CityName.LOS_ANGELES, CityName.NEW_YORK_CITY, 21));
        newDeck.add(new DestinationCard(CityName.DULUTH, CityName.HOUSTON, 8));
        newDeck.add(new DestinationCard(CityName.SAULT_ST_MARIE, CityName.NASHVILLE, 8));
        newDeck.add(new DestinationCard(CityName.NEW_YORK_CITY, CityName.ATLANTA, 6));
        newDeck.add(new DestinationCard(CityName.PORTLAND, CityName.NASHVILLE, 17));
        newDeck.add(new DestinationCard(CityName.VANCOUVER, CityName.MONTREAL, 20));
        newDeck.add(new DestinationCard(CityName.DULUTH, CityName.EL_PASO, 10));
        newDeck.add(new DestinationCard(CityName.TORONTO, CityName.MIAMI, 10));
        newDeck.add(new DestinationCard(CityName.PORTLAND, CityName.PHOENIX, 11));
        newDeck.add(new DestinationCard(CityName.DALLAS, CityName.NEW_YORK_CITY, 11));
        newDeck.add(new DestinationCard(CityName.CALGARY, CityName.SALT_LAKE_CITY, 7));
        newDeck.add(new DestinationCard(CityName.CALGARY, CityName.PHOENIX, 13));
        newDeck.add(new DestinationCard(CityName.LOS_ANGELES, CityName.MIAMI, 20));
        newDeck.add(new DestinationCard(CityName.WINNIPEG, CityName.LITTLE_ROCK, 11));
        newDeck.add(new DestinationCard(CityName.SAN_FRANCISCO, CityName.ATLANTA, 17));
        newDeck.add(new DestinationCard(CityName.KANSAS_CITY, CityName.HOUSTON, 5));
        newDeck.add(new DestinationCard(CityName.LOS_ANGELES, CityName.CHICAGO, 16));
        newDeck.add(new DestinationCard(CityName.DENVER, CityName.PITTSBURGH, 11));
        newDeck.add(new DestinationCard(CityName.CHICAGO, CityName.SANTA_FE, 9));
        newDeck.add(new DestinationCard(CityName.VANCOUVER, CityName.SANTA_FE, 13));
        newDeck.add(new DestinationCard(CityName.BOSTON, CityName.MIAMI, 12));
        newDeck.add(new DestinationCard(CityName.CHICAGO, CityName.NEW_ORLEANS, 7));
        newDeck.add(new DestinationCard(CityName.MONTREAL, CityName.ATLANTA, 9));
        newDeck.add(new DestinationCard(CityName.SEATTLE, CityName.NEW_YORK_CITY, 22));
        newDeck.add(new DestinationCard(CityName.DENVER, CityName.EL_PASO, 4));
        newDeck.add(new DestinationCard(CityName.HELENA, CityName.LOS_ANGELES, 8));
        newDeck.add(new DestinationCard(CityName.WINNIPEG, CityName.HOUSTON, 12));
        newDeck.add(new DestinationCard(CityName.MONTREAL, CityName.NEW_ORLEANS, 13));
        newDeck.add(new DestinationCard(CityName.SAULT_ST_MARIE, CityName.OKLAHOMA_CITY, 9));
        newDeck.add(new DestinationCard(CityName.SEATTLE, CityName.LOS_ANGELES, 9));
        deck = newDeck;
    }
}
