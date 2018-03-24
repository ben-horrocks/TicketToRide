package common.game_data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

import common.player_info.Username;

public class TurnQueue implements Serializable
{
    private ArrayBlockingQueue<Username> queue;

    // Constructors
    public TurnQueue(ArrayBlockingQueue<Username> queue) { this.queue = queue; }

    public TurnQueue(Collection<Username> collection)
    {
        queue = new ArrayBlockingQueue<>(collection.size());
        queue.addAll(collection);
    }

    public TurnQueue(Username[] userNames) { this(Arrays.asList(userNames)); }

    // Public Methods

	/**
	 * Tells you whose turn it is right now.
	 * @return Returns the username of the player whose turn it is.
	 */
    public Username peek() { return queue.peek(); }

    public boolean isMyTurn(Username username) { return queue.peek().equals(username); }

	/**
	 * Gives you an array representation of the turn queue.
	 * @return Returns an array of Username.
	 */
	public Username[] toArray() { return queue.toArray(new Username[queue.size()]); }

	/**
	 * Increment whose turn it is.
	 */
    public void nextTurn()
    {
        Username username = queue.poll();
        if (username != null)
        {
            queue.add(username);
        } else
        {
            System.out.println("Error in turn queue: queue.poll returned null.");
        }
    }

	/**
	 * The size of the turn queue.
	 * @return Returns the size of the turn queue.
	 */
	public int size() { return queue.size(); }
}
