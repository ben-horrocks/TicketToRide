package common.DataModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kavika F.
 */

public class EdgeGraph implements Serializable
{
	private Map<City, List<Edge>> graph = new HashMap<>();

	public EdgeGraph(Map<City, List<Edge>> graph)
	{
		this.graph = graph;
	}

	public Map<City, List<Edge>> getGraph() { return graph; }

	public void addEdge(City city, Edge edge)
	{
		if (hasCity(city))
		{
			graph.get(city).add(edge);
		}
		else
		{
			ArrayList<Edge> temp = new ArrayList<>();
			temp.add(edge);
			graph.put(city, temp);
		}
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
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EdgeGraph edgeGraph = (EdgeGraph) o;

		return graph != null ? graph.equals(edgeGraph.graph) : edgeGraph.graph == null;
	}

	@Override
	public int hashCode()
	{
		return graph != null ? graph.hashCode() : 0;
	}
}
