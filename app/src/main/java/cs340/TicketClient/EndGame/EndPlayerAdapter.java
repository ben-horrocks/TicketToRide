package cs340.TicketClient.EndGame;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import common.player_info.Player;
import cs340.TicketClient.R;

/**
 * EndPlayerAdapter
 * Abstract: Adapter to dynamically create Player List for End Game Activity
 *
 * @domain games  List<Player> The list of players being displayed.
 */
public class EndPlayerAdapter extends RecyclerView.Adapter<EndPlayerAdapter.viewHolder>
{
    private List<Player> players = new ArrayList<>();

    public EndPlayerAdapter()
    {

    }

    @Override
    public EndPlayerAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.end_game_player_item, parent, false);
        viewHolder vh = new viewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position)
    {
        Player player = players.get(position);
        holder.playerName.setText(player.getName());
        holder.playerPoints.setText(player.getScore());
    }

    @Override
    public int getItemCount()
    {
        return players.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addPlayers(List<Player> newplayers) {
        players.addAll(newplayers);
        notifyDataSetChanged();
    }

    /**
     * Abstract: Function to clear all players from the list.
     *
     * @pre players is not null
     * @post players.size == 0
     */
    public void clear()
    {
        players.clear();
        notifyDataSetChanged();
    }

    /**
     * ViewHolder
     * Abstract: view to dynamically display player names and points
     *
     * @domain playerName   TextView      view to display player name
     * playerPoints         TextView      View to display player points
     */
    public class viewHolder extends RecyclerView.ViewHolder
    {
        public TextView playerName;
        public TextView playerPoints;

        public viewHolder(View itemView)
        {
            //VIEW BINDING
            super(itemView);
            playerName = (TextView) itemView.findViewById(R.id.end_player_name);
            playerPoints = (TextView) itemView.findViewById(R.id.end_player_points);
            //END VIEW BINDING

        }
    }

}
