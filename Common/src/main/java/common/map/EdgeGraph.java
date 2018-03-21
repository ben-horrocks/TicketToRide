package common.map;

import java.io.Serializable;
import java.util.*;

/**
 * The representation of the board game.
 */
public class EdgeGraph implements Serializable
{
    private Map<City, List<Edge>> graph;

    public EdgeGraph()
    {
        graph = new HashMap<>();
    }

    public Map<City, List<Edge>> getGraph()
    {
        return graph;
    }

    public Set<Edge> getAllEdges()
    {
        Set<Edge> edges = new HashSet<>();
        for (Map.Entry<City, List<Edge>> entry : graph.entrySet())
        {
            edges.addAll(entry.getValue());
        }
        return edges;
    }

    public void addEdge(City city, Edge edge)
    {
        if (hasCity(city))
        {
            graph.get(city).add(edge);
        } else
        {
            ArrayList<Edge> temp = new ArrayList<>();
            temp.add(edge);
            graph.put(city, temp);
        }
    }

    public void addEdge(Edge edge)
    {
        City city = edge.getFirstCity();
        addEdge(city, edge);
    }

    public boolean hasEdge(Edge edge)
    {
        City city = edge.getFirstCity();
        if (graph.containsKey(city))
        {
            List<Edge> edges = graph.get(city);
            for (Edge e : edges)
            {
                if (e.equals(edge))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasCity(City city)
    {
        return graph.containsKey(city);
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

        EdgeGraph edgeGraph = (EdgeGraph) o;

        return graph != null ? graph.equals(edgeGraph.graph) : edgeGraph.graph == null;
    }

    @Override
    public int hashCode()
    {
        return graph != null ? graph.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Set<City> cities = graph.keySet();
        for (City city : cities)
        {
            String beginning = "Edges starting at: " + city.getCityName();
            sb.append(beginning);
            List<Edge> edges = graph.get(city);
            for (Edge edge : edges)
            {
                // This could get very long. May want to cut down eventually.
                sb.append(edge.toString());
            }
        }
        return sb.toString();
    }
}
