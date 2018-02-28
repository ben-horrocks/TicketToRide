package common.DataModels;

public class DestinationCard
{
  private City city1, city2;
  private int pointValue;

  public DestinationCard(City city1, City city2, int points) {
    this.city1 = city1;
    this.city2 = city2;
    this.pointValue = points;
  }

  public City getCity1()
  {
    return city1;
  }

  public City getCity2()
  {
    return city2;
  }

  public int getPointValue()
  {
    return pointValue;
  }
}
