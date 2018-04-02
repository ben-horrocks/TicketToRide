package cs340.TicketClient.end_game;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import common.game_data.EndGame;
import cs340.TicketClient.R;

/**
 * EndPlayerAdapter
 * Abstract: Adapter to dynamically create Player List for End Game Activity
 *
 * @domain games  List<Player> The list of players being displayed.
 */
public class EndPlayerAdapter extends RecyclerView.Adapter<EndPlayerAdapter.viewHolder>
{
    private List<EndGame.EndGamePlayer> players = new ArrayList<>();


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
        EndGame.EndGamePlayer player = players.get(position);
        holder.playerName.setText(player.getUsername());
        holder.playerRouteClaimedPoints.setText(Integer.toString(player.getRoutesClaimedPoints()));
        holder.playerLongestRoutePoints.setText(Integer.toString(player.getLongestPathPoints()));
        holder.playerCompletedDestinationPoints.setText(Integer.toString(player.getCompleteDestinationCardPoints()));
        holder.playerIncompleteDestinationPoints.setText(Integer.toString(player.getIncompleteDestinationCardPoints()));
        holder.playerTotalPoints.setText(Integer.toString(player.getTotalPoints()));

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

    public void addPlayers(EndGame newplayers) {
        players.addAll(newplayers.getPlayers());
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
     * @domain playerName           TextView      view to display player name
     * playerRouteClaimedPoints     TextView      View to display player points
     */
    public class viewHolder extends RecyclerView.ViewHolder
    {
        public TextView playerName;
        public TextView playerRouteClaimedPoints;
        public TextView playerLongestRoutePoints;
        public TextView playerCompletedDestinationPoints;
        public TextView playerIncompleteDestinationPoints;
        public TextView playerTotalPoints;

        public viewHolder(View itemView)
        {
            //VIEW BINDING
            super(itemView);
            playerName = (TextView) itemView.findViewById(R.id.end_player_name);
            playerRouteClaimedPoints = (TextView) itemView.findViewById(R.id.end_player_claimed_routes_points);
            playerLongestRoutePoints = (TextView) itemView.findViewById(R.id.end_player_longest_route_points);
            playerCompletedDestinationPoints = (TextView) itemView.findViewById(R.id.end_player_completed_destination_points);
            playerIncompleteDestinationPoints = (TextView) itemView.findViewById(R.id.end_player_incompleted_destination_points);
            playerTotalPoints = (TextView) itemView.findViewById(R.id.end_player_points);
            //END VIEW BINDING

        }
    }

}
