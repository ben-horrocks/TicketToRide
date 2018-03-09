package common.DataModels.GameData;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

import common.DataModels.Username;

/**
 * Created by Kavika F.
 */

public class TurnQueue implements Serializable
{
	private ArrayBlockingQueue<Username> queue;

	// Constructors
	public TurnQueue(ArrayBlockingQueue<Username> queue) { this.queue = queue; }

	public TurnQueue(Collection<Username> collection) { queue.addAll(collection); }

	public TurnQueue(Username[] usernames) { this(Arrays.asList(usernames)); }

	// Public Methods
	public Username peek() { return queue.peek(); }

	public Username[] toArray() { return queue.toArray(new Username[queue.size()]); }

	public void nextTurn()
	{
		Username username = queue.poll();
		if (username != null)
		{
			queue.add(username);
		}
		else
		{
			System.out.println("Error in turn queue: queue.poll returned null.");
		}
	}
}
