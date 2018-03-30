package cs340.TicketClient.lobby;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import common.game_data.GameID;
import common.game_data.GameInfo;
import cs340.TicketClient.R;

/**
 * Created by Kavika F.
 */

public class RunningGameListAdapter extends RecyclerView.Adapter<RunningGameListAdapter.viewHolder>
{
	private List<GameInfo> games = new ArrayList<>();

	RunningGameListAdapter() {}

	@Override
	public viewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.running_game_list_item, parent, false);
		viewHolder vh = new viewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(viewHolder holder, int position)
	{
		GameInfo game = games.get(position);
		holder.gameName.setText(game.getName());
		holder.playerCount.setText(game.getPlayerCount());
	}

	@Override
	public int getItemCount()
	{
		return games.size();
	}

	public void setGames(List<GameInfo> gameInfo)
	{
		games.clear();
		games.addAll(gameInfo);
		notifyDataSetChanged();
	}

	public class viewHolder extends RecyclerView.ViewHolder
	{
		TextView gameName;
		TextView playerCount;

		public viewHolder(View itemView)
		{
			super(itemView);
			gameName = itemView.findViewById(R.id.running_game_name);
			playerCount = itemView.findViewById(R.id.running_game_num_players);
		}
	}
}
