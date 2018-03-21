package cs340.TicketClient.GameMenu.chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import common.chat.ChatItem;
import cs340.TicketClient.Game.GameModel;
import cs340.TicketClient.R;

public class ChatFragment extends android.support.v4.app.Fragment
{

    private ChatPresenter mChatPresenter;

    private Button mCloseButton;
    private RecyclerView mChatRecyclerView;
    private ChatAdapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private EditText mChatInputText;
    private Button mSendButton;

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
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        //Set background color to white
        v.setBackgroundColor(Color.WHITE);

        //Setup and store ChatPresenter
        mChatPresenter = ChatPresenter.getSINGLETON(this);

        mChatInputText = (EditText) v.findViewById(R.id.chat_text_input);
        mSendButton = (Button) v.findViewById(R.id.chat_send_button);

        // Input Text
        mChatInputText.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                // If the user hits ENTER, the message will be sent.
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    mSendButton.performClick();
                    return true;
                }
                return false;
            }
        });

        // Send Button
        mSendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mChatInputText.getText().length() != 0)
                {
                    //send chat message to server
                    mChatPresenter.sendChatMessage(mChatInputText.getText().toString());
                    //clear message in text box
                    mChatInputText.setText("");
                }
            }
        });

        //-- RECYCLER --
        //Get messages from the Game model
        ArrayList<ChatItem> chatList =
                (ArrayList<ChatItem>) GameModel.getInstance().getChatMessages();
        if (chatList == null)
        {
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

    /**
     * Updates the list of chat items in the ui thread.
     */
    public void updateChatList()
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
                    ArrayList<ChatItem> chatList =
                            (ArrayList<ChatItem>) GameModel.getInstance().getChatMessages();
                    // The .clear() will make it so the first person to send a chat item won't receive it.
                    //mChatAdapter.clear();
                    mChatAdapter.addChats(chatList);
                    //mChatRecyclerView.invalidate();
                }
            });
        }
    }

    /**
     * The adapter for the list of chat items.
     */
    private class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder>
    {
        private ArrayList<ChatItem> chatList = new ArrayList<>();

        /**
         * Custom view holder for each chat item.
         */
        class Holder extends RecyclerView.ViewHolder
        {

            // Name of the user sending the message.
            private TextView mDisplayName;
            // The message being displayed.
            private TextView mMessage;

            Holder(View itemView)
            {
                super(itemView);
                mDisplayName = (TextView) itemView.findViewById(R.id.chat_player);
                mMessage = (TextView) itemView.findViewById(R.id.chat_message);
            }
        }

        private ChatAdapter(ArrayList<ChatItem> chatList)
        {
            this.chatList = chatList;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_list_item, parent, false);
            Holder h = new Holder(v);
            return h;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position)
        {
            try
            {
                ChatItem item = chatList.get(position);
                holder.mDisplayName.setText(item.getPlayerName());
                holder.mMessage.setText(item.getMessage());
            } catch (IndexOutOfBoundsException e)
            {
                System.out.println(e + "-> Trying to access out of bounds position in chat list!");
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount()
        {
            return chatList.size();
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
        void addChats(List<ChatItem> newList)
        {
            if (chatList != null)
            {
                chatList = new ArrayList<>(newList);
                //chatList.addAll(newList);
                notifyDataSetChanged();
            }
        }

        /**
         * Abstract: Function to clear all games from the list.
         *
         * @pre games is not null
         * @post games.size == 0
         */
        void clear()
        {
            if (chatList != null)
            {
                chatList.clear();
                notifyDataSetChanged();
            }
        }
    }


}
