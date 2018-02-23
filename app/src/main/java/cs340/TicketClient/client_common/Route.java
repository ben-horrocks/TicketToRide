package cs340.TicketClient.client_common;

import com.google.android.gms.maps.model.Polyline;

/**
 * Created by Kavika F.
 */

public class Route
{
	private Edge edge;
	private Polyline polyline;

	public Route(Edge edge, Polyline polyline)
	{
		this.edge = edge;
		this.polyline = polyline;
	}

	public Edge getEdge() { return edge; }

	public Polyline getPolyline() { return polyline; }
}
