package common.DataModels.GameData.Decks;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.City;
import common.DataModels.DestinationCard;
import common.DataModels.GameData.CityName;

public class DestinationCardDeck implements IDestinationCardDeck
{
  private List<DestinationCard> deck;
  public DestinationCardDeck() {
    this.deck = addDestinationCards();
    shuffle();
  }

  @Override
  public List<DestinationCard> draw()
  {
  	//TODO: implement draw
    return null;
  }

  @Override
  public void shuffle()
  {
    List<DestinationCard> newdeck = new ArrayList<>();
    while(deck.size() > 0)
    {
      int randomcard = (int) (Math.random()) % deck.size();
      newdeck.add(deck.get(randomcard));
      deck.remove(randomcard);
    }
    this.deck = newdeck;
  }

  private List<DestinationCard> addDestinationCards()
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
    return newDeck;
  }
}
