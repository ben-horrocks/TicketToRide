/**
 * DestinationCard.java
 * Author: Ben Horrocks
 * Last Commit: 4 March, 2018
 * Notes: Object to store Destination Card
 */
package common.DataModels;

import java.io.Serializable;

public class DestinationCard implements Serializable
{
  private String city1, city2;
  private int pointValue;

  public DestinationCard(String city1, String city2, int points) {
    this.city1 = city1;
    this.city2 = city2;
    this.pointValue = points;
  }

  public String getCity1() { return city1; }

  public String getCity2() { return city2; }

  public int getPointValue() { return pointValue; }

  @Override
  public String toString() { return city1 + " to " + city2; }
}