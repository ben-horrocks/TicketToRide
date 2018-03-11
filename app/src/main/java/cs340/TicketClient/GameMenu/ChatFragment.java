package cs340.TicketClient.GameMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.GameInfo;
import cs340.TicketClient.Game.GameModel;
import cs340.TicketClient.R;

public class ChatFragment extends android.support.v4.app.Fragment {

    private ChatPresenter mChatPresenter;

    private Button mCloseButton;
    private RecyclerView mChatRecyclerView;
    private ChatFragment.ChatAdapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private EditText mChatInputText;
    private Button mSendButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        //Set background color to white
        v.setBackgroundColor(Color.WHITE);

        //Setup and store ChatPresenter
        mChatPresenter = ChatPresenter.getSINGLETON(this);

        //-- SEND BUTTON --
        mChatInputText = (EditText) v.findViewById(R.id.chat_text_input);
        mSendButton = (Button) v.findViewById(R.id.chat_send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mChatInputText.getText().length() != 0) {
                    //send chat message to server
                    mChatPresenter.sendChatMessage(mChatInputText.getText().toString());
                    //clear message in text box
                    mChatInputText.setText("");
                }
            }
        });

        //-- RECYCLER --
        //Get messages from the Game model
        ArrayList<ChatItem> chatList = (ArrayList<ChatItem>) GameModel.getInstance().getChatMessages();
        if (chatList == null) {
            chatList = new ArrayList<>();
        }

        //Instantiate View
        mChatRecyclerView = (RecyclerView) v.findViewById(R.id.chat_recycler_field);

        //Setup layout Manager
        mChatLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mChatLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mChatRecyclerView.setLayoutManager(mChatLayoutManager);

        //Populate Recycler
        mChatAdapter = new ChatAdapter(chatList);
        mChatRecyclerView.setAdapter(mChatAdapter);

        return v;
    }

    public void updateChatList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //-- RECYCLER --
                //Get messages from the Game model
                ArrayList<ChatItem> chatList = (ArrayList<ChatItem>) GameModel.getInstance().getChatMessages();
                mChatAdapter.clear();
                mChatAdapter.addChats(chatList);
                //mChatRecyclerView.invalidate();
            }
        });
    }

    public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {

        private ArrayList<ChatItem> chatList = new ArrayList<>();

        public class Holder extends RecyclerView.ViewHolder {

            private LinearLayout mChatSlot;
            private TextView mDisplayName;
            private TextView mMessage;

            public Holder(View itemView) {
                super(itemView);
                mChatSlot = (LinearLayout) itemView.findViewById(R.id.chat_slot);
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

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView)
        {
            super.onAttachedToRecyclerView(recyclerView);
        }

        /**
         * Abstract: Function to add a collection of games to the list.
         *
         * @pre games is not null, newGames.size >0
         * @post games will have the new list of games added to it, games.size += newgames
         */
        public void addChats(List<ChatItem> newList)
        {
            if (chatList != null) {
            	//clear();
                chatList.addAll(newList);
                notifyDataSetChanged();
            }
        }

        /**
         * Abstract: Function to clear all games from the list.
         *
         * @pre games is not null
         * @post games.size == 0
         */
        public void clear()
        {
            if (chatList != null) {
                chatList.clear();
                notifyDataSetChanged();
            }
        }
    }

}
