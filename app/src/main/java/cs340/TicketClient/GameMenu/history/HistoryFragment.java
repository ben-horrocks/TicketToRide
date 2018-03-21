package cs340.TicketClient.GameMenu.history;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.HistoryItem;
import cs340.TicketClient.Game.GameModel;
import cs340.TicketClient.R;


public class HistoryFragment extends android.support.v4.app.Fragment
{

    private RecyclerView mHistoryRecyclerView;
    private HistoryFragment.HistoryAdapter mHistoryAdapter;
    private RecyclerView.LayoutManager mHistoryLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        //Set background color to white
        v.setBackgroundColor(Color.WHITE);

        //-- RECYCLER --
        //Get list from the model
        ArrayList<HistoryItem> historyList =
                (ArrayList<HistoryItem>) GameModel.getInstance().getPlayHistory();

        //Instantiate View
        mHistoryRecyclerView = (RecyclerView) v.findViewById(R.id.history_recycler_field);

        //Setup layout Manager
        mHistoryLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mHistoryLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mHistoryRecyclerView.setLayoutManager(mHistoryLayoutManager);

        //Populate Recycler
        mHistoryAdapter = new HistoryFragment.HistoryAdapter(historyList);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);

        return v;
    }

    public void updateHistoryList()
    {
        if (getActivity() != null)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    //-- RECYCLER --
                    //Get messages from the Game model
                    ArrayList<HistoryItem> historyList =
                            (ArrayList<HistoryItem>) GameModel.getInstance().getPlayHistory();
                    // The .clear() will make it so the first person to send a chat item won't receive it.
                    //mChatAdapter.clear();
                    mHistoryAdapter.addHistory(historyList);
                    //mChatRecyclerView.invalidate();
                }
            });
        }

    }

    public class HistoryAdapter extends RecyclerView.Adapter<HistoryFragment.HistoryAdapter.Holder>
    {

        private ArrayList<HistoryItem> mHistoryList;

        public class Holder extends RecyclerView.ViewHolder
        {

            private TextView mAction;

            public Holder(View itemView)
            {
                super(itemView);
                mAction = (TextView) itemView.findViewById(R.id.history_text);
            }
        }

        public HistoryAdapter(ArrayList<HistoryItem> historyList)
        {
            this.mHistoryList = historyList;
        }

        @Override
        public HistoryFragment.HistoryAdapter.Holder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType)
        {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_list_item, parent, false);
            HistoryFragment.HistoryAdapter.Holder h = new HistoryFragment.HistoryAdapter.Holder(v);
            return h;
        }

        @Override
        public void onBindViewHolder(HistoryFragment.HistoryAdapter.Holder holder, int position)
        {
            if (mHistoryList != null && !mHistoryList.isEmpty())
            {
                HistoryItem item = mHistoryList.get(position);
                holder.mAction.setText(item.getAction());
            }
        }

        @Override
        public int getItemCount()
        {
            if (mHistoryList != null)
            {
                return mHistoryList.size();
            }
            return 0;
        }

        void addHistory(List<HistoryItem> newList)
        {
            if (mHistoryList != null)
            {
                mHistoryList = new ArrayList<>(newList);
                //chatList.addAll(newList);
                notifyDataSetChanged();
            }
        }

        void clear()
        {
            if (mHistoryList != null)
            {
                mHistoryList.clear();
                notifyDataSetChanged();
            }
        }
    }

}
