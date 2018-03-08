package cs340.TicketClient.MenuFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import common.DataModels.ChatItem;
import cs340.TicketClient.R;

/**
 * Created by Carter on 3/6/18.
 */

public class ChatFragment extends Fragment {

    private Button mCloseButton;
    private RecyclerView mChatRecyclerView;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        //-- CLOSE BUTTON --
        mCloseButton = (Button) getActivity().findViewById(R.id.history_close_btn);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: close the fragment
            }
        });

        //-- RECYCLER --
        //TODO: GET FROM THE MODEL
        ArrayList<ChatItem> chatList = new ArrayList<>();

        //Instantiate View
        mChatRecyclerView = (RecyclerView) getActivity().findViewById(R.id.chat_recycler_field);

        //Setup layout Manager
        mChatLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mChatLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mChatRecyclerView.setLayoutManager(mChatLayoutManager);

        //Populate Recycler
        mChatAdapter = new ChatAdapter(chatList);
        mChatRecyclerView.setAdapter(mChatAdapter);

        return v;
    }

    public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {

        private ArrayList<ChatItem> chatList;

        public class Holder extends RecyclerView.ViewHolder {

            private RelativeLayout mChatSlot;
            private TextView mDisplayName;
            private TextView mMessage;

            public Holder(View itemView) {
                super(itemView);
                mChatSlot = (RelativeLayout) itemView.findViewById(R.id.chat_slot);
                mDisplayName = (TextView) itemView.findViewById(R.id.chat_player);
                mMessage = (TextView) itemView.findViewById(R.id.chat_message);
            }
        }

        public ChatAdapter(ArrayList<ChatItem> chatList) { this.chatList = chatList; }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
            Holder h = new Holder(v);
            return h;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            if (chatList != null && !chatList.isEmpty()) {
                ChatItem item =  chatList.get(position);
                holder.mDisplayName.setText(item.getPlayerName());
                holder.mMessage.setText(item.getMessage());
            }
        }

        @Override
        public int getItemCount() {
            if (chatList != null) {
                return chatList.size();
            }
            return 0;
        }
    }

}
