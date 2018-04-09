package cs340.TicketClient.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.game_data.*;
import common.player_info.*;
import cs340.TicketClient.card_fragments.deck_fragment.DeckFragment;
import cs340.TicketClient.card_fragments.destination_card_fragment.DestinationCardFragment;
import cs340.TicketClient.card_fragments.hand_fragment.HandFragment;
import cs340.TicketClient.end_game.EndGameActivity;
import cs340.TicketClient.game_menu.chat.ChatFragment;
import cs340.TicketClient.game_menu.history.HistoryFragment;
import cs340.TicketClient.game_menu.player_info.PlayerFragment;
import cs340.TicketClient.R;

public class GameActivity extends AppCompatActivity implements View.OnClickListener
{

    private GoogleMap googleMap;
    private User user;
    private GamePresenter presenter;
    private DestinationCardFragment frag = null;
    final FragmentManager fm = this.getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        presenter = new GamePresenter(this);
        Bundle extras = getIntent().getExtras();

        // Initialize the model through the presenter
        if (extras != null)
        {
            if (extras.get("packet") instanceof StartGamePacket)
            {
                StartGamePacket packet = (StartGamePacket) extras.get("packet");
                presenter.fillModel(packet);
            }
        }

        // Start up the google map
        Fragment mapViewFragment = fm.findFragmentById(R.id.fragment_map);
        if (mapViewFragment == null)
        {
            mapViewFragment = new MapFragment();
            mapViewFragment.setArguments(extras);
            fm.beginTransaction().add(R.id.fragment_map, mapViewFragment).commit();
        }

        // Start the game off by having the player pick their destination cards
        Fragment destinationViewFragment = fm.findFragmentById(R.id.fragment_destination_card);
        if (destinationViewFragment == null)
        {
            Bundle toDestinationVF = new Bundle();
            toDestinationVF.putSerializable("cards", presenter.getDestinationCards());
            destinationViewFragment = new DestinationCardFragment();
            destinationViewFragment.setArguments(toDestinationVF);
            // Set the destination fragment and set the transition
            fm.beginTransaction().add(R.id.fragment_map, destinationViewFragment, MapFragment.class.getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(MapFragment.class.getSimpleName())
                    .commit();
        }

        LinearLayout handLayout = this.findViewById(R.id.hand_button_holder);
        LinearLayout movesLayout = this.findViewById(R.id.move_bar);

        // The buttons
        Button handButton = handLayout.findViewById(R.id.hand_button);
        Button destinationCardButton = movesLayout.findViewById(R.id.draw_destination_button);
        Button drawTrainCardButton = movesLayout.findViewById(R.id.draw_trainCar_button);
        Button claimRouteButton = movesLayout.findViewById(R.id.claim_route_button);

        // Hand Button On Click Listener
        handButton.setOnClickListener(this);

        // Destination Card Button On Click Listener
        destinationCardButton.setOnClickListener(this);

        // Draw Train Card On Click Listener
        drawTrainCardButton.setOnClickListener(this);

        // Claim Route On Click Listener
        claimRouteButton.setOnClickListener(this);
    }

    // On Click Listener for XML files
    // Should change to regular onClickListeners eventually...
    @Override
    public void onClick(View v)
    {
    	String tag;
        switch (v.getId())
        {
            case R.id.hand_button:
            	tag = HandFragment.class.getSimpleName();
                Fragment handFragment = fm.findFragmentByTag(tag);
                if (handFragment == null)
				{
					handFragment = new HandFragment();
					fm.beginTransaction().add(R.id.fragment_map, handFragment, tag)
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
							.addToBackStack(tag).commit();
				}
                break;
            case R.id.draw_trainCar_button:
            	tag = DeckFragment.class.getSimpleName();
				Fragment drawCardFragment = fm.findFragmentByTag(tag);
				if (drawCardFragment == null)
				{
					drawCardFragment = new DeckFragment();
					fm.beginTransaction().add(R.id.fragment_map, drawCardFragment, tag)
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
							.addToBackStack(tag).commit();
				}
                break;
            case R.id.draw_destination_button:
				if (!presenter.playerHasRestrictedAction())
				{
					// Gets initial dest cards OR gets 3 new ones.
					presenter.getDestinationCards();
				}
                break;
            case R.id.claim_route_button:
            	if (!presenter.playerHasRestrictedAction())
				{
					presenter.startClaimRouteOption();
				}
                break;
            default:
                System.out.println("Broken at onClick(View v) in Game Activity");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    public void startDestinationFragment(HandDestinationCards cards)
    {
        Fragment destinationCardFragment = fm.findFragmentByTag(DestinationCardFragment.class.getSimpleName());
        if (destinationCardFragment == null)
		{
			Bundle bundle = new Bundle();
			bundle.putSerializable("cards", cards);
			destinationCardFragment = new DestinationCardFragment();
			destinationCardFragment.setArguments(bundle);
			fm.beginTransaction().add(R.id.fragment_map, destinationCardFragment,
					DestinationCardFragment.class.getSimpleName())
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.addToBackStack(DestinationCardFragment.class.getSimpleName())
					.commit();
		}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Fragment fragment;

        switch (item.getItemId()) // For all fragments, retain their data after being used.
        {
            case R.id.chat_btn:
            	fragment = fm.findFragmentByTag(ChatFragment.class.getSimpleName());
            	if (fragment == null)
				{
					fragment = new ChatFragment();
					fm.beginTransaction().replace(R.id.fragment_map, fragment, ChatFragment.class.getSimpleName())
							.addToBackStack(ChatFragment.class.getSimpleName())
							.commit();
				}
                break;
            case R.id.hist_btn:
            	fragment = fm.findFragmentByTag(HistoryFragment.class.getSimpleName());
            	if (fragment == null)
				{
					fragment = new HistoryFragment();
					fm.beginTransaction().replace(R.id.fragment_map, fragment, HistoryFragment.class.getSimpleName())
							.addToBackStack(HistoryFragment.class.getSimpleName())
							.commit();
				}
                break;
            case R.id.player_btn:
            	fragment = fm.findFragmentByTag(PlayerFragment.class.getSimpleName());
            	if (fragment == null)
				{
					fragment = new PlayerFragment();
					fm.beginTransaction().replace(R.id.fragment_map, fragment, PlayerFragment.class.getSimpleName())
							.addToBackStack(PlayerFragment.class.getSimpleName())
							.commit();
				}
                break;
                /* Test button functionality suspended
            case R.id.test_btn:
                presenter.test();
                break;
                */
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Create a larger toast for a longer period of time. Mainly used for testing.
     *
     * @param message The message for the toast to display.
     */
    void makeLargerToast(String message)
    {
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG);
        // Increase toast text size without creating custom toast
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(25);
        toast.show();
    }

    /**
     * Display the colors of the players and their userNames. Mainly for testing.
     *
     * @param userNames The list of userNames to display.
     * @param colors    The list of colors associated with each userName.
     */
    void displayColors(List<Username> userNames, List<PlayerColor> colors)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < userNames.size(); i++)
        {
            String line = userNames.get(i).getName() + " has color " + colors.get(i).name() + "\n";
            sb.append(line);
        }
        makeLargerToast(sb.toString());
    }

    public void setFrag(DestinationCardFragment frag) {
        this.frag = frag;
    }

    /**
     * Display the order in which turns will be taken. Mainly for testing.
     *
     * @param usernames The userNames in a collection sorted by their queue order.
     */
    void displayPlayerOrder(Username[] usernames)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Player Turn Order\n");
        for (int i = 0; i < usernames.length; i++)
        {
            int turn = i + 1;
            String line = turn + ": " + usernames[i].toString() + "\n";
            sb.append(line);
        }
        makeLargerToast(sb.toString());
    }

    /**
     * Display the current player's hand of train cards. Mainly for testing.
     *
     * @param playerHand The list of train cards to display.
     */
    void displayPlayerTrainCards(HandTrainCards playerHand)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Player Hand\n");
        for (int i = 0; i < playerHand.size(); i++)
        {
            String line = playerHand.get(i).getType().name();
            if (i + 1 < playerHand.size())
            {
                line += ", ";
            }
            sb.append(line);
        }
        makeLargerToast(sb.toString());
    }

    /**
     * Display the number of cards each opponent has. Mainly for testing.
     *
     * @param opponents The list of opponents. Will access their hand sizes.
     */
    void displayOpponentHandSize(List<Opponent> opponents)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Opponent Hand Sizes\n");
        for (Opponent opponent : opponents)
        {
            String line =
                    opponent.getUsername().getName() + ": " + opponent.getNumberHandCards() + "\n";
            sb.append(line);
        }
        makeLargerToast(sb.toString());
    }

    public void endGame(EndGame players)
    {
        Intent intent = new Intent(this, EndGameActivity.class);
        intent.putExtra("players", players);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (frag != null)
        {
            Toast.makeText(this, "Cannot go back after drawing Destination cards", Toast.LENGTH_SHORT).show();
        }
        else
            super.onBackPressed();
    }
}
