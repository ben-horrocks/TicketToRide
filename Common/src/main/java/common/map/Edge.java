package common.map;

import java.io.Serializable;
import java.util.*;

import common.cards.TrainColor;
import common.game_data.Opponent;
import common.player_info.Player;
import common.player_info.PlayerColor;
import common.player_info.Username;

/**
 * A route between two cities.
 */
public class Edge implements Serializable
{
    private City firstCity;
    private City secondCity;
    private TrainColor color;
    private PlayerColor ownerColor;
    private int length;
    private Username owner = null;
    private boolean isDoubleEdge;

    /**
     * @param firstCity    The "starting" city.
     * @param secondCity   The "destination" city.
     * @param color        The color of the edge.
     * @param length       The distance between the two cities (as defined by the game).
     * @param isDoubleEdge Whether or not there is another edge between the same cities.
     * @pre All parameters must be non-null.
     * @post A valid Edge object.
     */
    public Edge(City firstCity, City secondCity, TrainColor color, int length, boolean isDoubleEdge)
    {
        this.firstCity = firstCity;
        this.secondCity = secondCity;
        this.color = color;
        this.length = length;
        this.isDoubleEdge = isDoubleEdge;
        this.ownerColor = null;
    }

    public City getFirstCity()
    {
        return firstCity;
    }

    public City getSecondCity()
    {
        return secondCity;
    }

    public List<City> getCities()
    {
        List<City> cities = new ArrayList<>();
        cities.add(firstCity);
        cities.add(secondCity);
        return cities;
    }

    public TrainColor getColor()
    {
        return color;
    }

    public int getLength()
    {
        return length;
    }

    public Username getOwner()
    {
        return owner;
    }

    public void setOwner(Player owner)
    {
        this.owner = owner.getUser().getUsername();
        this.ownerColor = owner.getColor();
    }

    public void setOwner(Opponent owner)
    {
        this.owner = owner.getUsername();
        this.ownerColor = owner.getColor();
    }

    public PlayerColor getOwnerColor()
    {
        return this.ownerColor;
    }

    public boolean isClaimed()
    {
        return owner != null;
    }

    public boolean isDoubleEdge()
    {
        return isDoubleEdge;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Edge edge = (Edge) o;

        if (length != edge.length)
        {
            return false;
        }
        if (isDoubleEdge != edge.isDoubleEdge)
        {
            return false;
        }
        if (!firstCity.equals(edge.firstCity))
        {
            return false;
        }
        if (!secondCity.equals(edge.secondCity))
        {
            return false;
        }
        if (color != edge.color)
        {
            return false;
        }
        return owner.equals(edge.owner);
    }

    @Override
    public int hashCode()
    {
        int result = firstCity.hashCode();
        result = 31 * result + secondCity.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + length;
        result = 31 * result + (isDoubleEdge ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Owner: " + owner + "\n" + "From " + firstCity.getCityName() + " to " +
               secondCity.getCityName() + "\n" + "Length: " + length + ". Color: " + color;
    }

    public int computeLongestPathOneDirection(Set<Edge> unusedEdges, Set<Edge> usedEdges)
    {
        int longestPath = 0;
        for(Edge checkEdge : unusedEdges) {
            if(checkEdge.getCities().contains(firstCity) || checkEdge.getCities().contains(secondCity)) {
                unusedEdges.remove(checkEdge);
                if(!usedEdges.contains(checkEdge))
                {
                    usedEdges.add(checkEdge);
                }
                Set<Edge> newUnusedEdges = new HashSet<>();
                newUnusedEdges.addAll(unusedEdges);
                int newLongestPath = checkEdge.computeLongestPathOneDirection(newUnusedEdges, usedEdges);
                if(newLongestPath > longestPath)
                {
                    longestPath = newLongestPath;
                }
            }
        }
        longestPath += length;
        return longestPath;
    }

    public int computeLongestPathTwoDirections(Set<Edge> unusedEdges)
    {
        int longestPathDirectionOne = 0;
        int longestPathDirectionTwo = 0;
        for(Edge checkEdge : unusedEdges) {
            if(checkEdge.getCities().contains(firstCity)) {
                unusedEdges.remove(checkEdge);
                int newLongestPath = checkEdge.computeLongestPathTwoDirections(unusedEdges);
                if(newLongestPath > longestPathDirectionOne)
                {
                    longestPathDirectionOne = newLongestPath;
                }
            } else if(checkEdge.getCities().contains(secondCity))
            {
                unusedEdges.remove(checkEdge);
                int newLongestPath = checkEdge.computeLongestPathTwoDirections(unusedEdges);
                if(newLongestPath > longestPathDirectionTwo)
                {
                    longestPathDirectionTwo = newLongestPath;
                }
            }
        }
        int longestPath = length + longestPathDirectionOne + longestPathDirectionTwo;
        return longestPath;
    }

    public static List<Edge> findEdgesWithCity(Set<Edge> edges, City city)
    {
        List<Edge> realedges = new ArrayList<>();
        for(Edge edge : edges)
        {
            if(edge.getFirstCity().equals(city))
            {
                realedges.add(edge);
            } else if(edge.getSecondCity().equals(city))
            {
                realedges.add(edge);
            }
        }
        return realedges;
    }

    public static List<City> findCitiesWithNumEdges(Set<Edge> edges, int edgenum)
    {
        Map<City, Integer> cities = new HashMap<>();
        for(Edge edge : edges)
        {
            City city1 = edge.getFirstCity();
            City city2 = edge.getSecondCity();
            if(cities.containsKey(city1))
            {
                Integer i = cities.get(city1);
                cities.put(city1, ++i);
            } else
            {
                Integer i = 0;
                cities.put(city1, ++i);
            }
            if(cities.containsKey(edge.getSecondCity()))
            {
                Integer i = cities.get(city2);
                cities.put(city2, ++i);
            } else
            {
                Integer i = 0;
                cities.put(city2, ++i);
            }
        }
        List<City> correctCities = new ArrayList<>();
        for(Map.Entry<City, Integer> city : cities.entrySet())
        {
            if(city.getValue().equals(edgenum))
            {
                correctCities.add(city.getKey());
            }
        }
        return correctCities;
    }

}
