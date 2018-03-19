package cs340.TicketClient.Game;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import common.DataModels.GameData.*;
import common.DataModels.*;
import cs340.TicketClient.CardFragments.deck_fragment.DeckFragment;
import cs340.TicketClient.CardFragments.destination_card_fragment.DestinationCardFragment;
import cs340.TicketClient.CardFragments.hand_fragment.HandFragment;
import cs340.TicketClient.GameMenu.chat.ChatFragment;
import cs340.TicketClient.GameMenu.history.HistoryFragment;
import cs340.TicketClient.GameMenu.player_info.PlayerFragment;
import cs340.TicketClient.R;

public class GameActivity extends AppCompatActivity implements View.OnClickListener
{

    private GoogleMap googleMap;
    private User user;
    private GamePresenter presenter;
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
            fm.beginTransaction().add(R.id.fragment_map, destinationViewFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
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
        switch (v.getId())
        {
            case R.id.hand_button:
                Fragment handFragment;
                handFragment = new HandFragment();
                fm.beginTransaction().add(R.id.fragment_map, handFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
                break;
            case R.id.draw_trainCar_button:
                Fragment drawCardFragment;
                drawCardFragment = new DeckFragment();
                fm.beginTransaction().add(R.id.fragment_map, drawCardFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
                break;
            case R.id.draw_destination_button:
                HandDestinationCards cards = presenter.getDestinationCards();
                if (cards == null)
                {
                    return;
                }
                startDestinationFragment(cards);
                break;
            case R.id.claim_route_button:
                System.out.println("To implement... lol");
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
        Fragment destinationCardFragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable("cards", cards);
        destinationCardFragment = new DestinationCardFragment();
        destinationCardFragment.setArguments(bundle);
        fm.beginTransaction().add(R.id.fragment_map, destinationCardFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;

        switch (item.getItemId()) // For all fragments, retain their data after being used.
        {
            case R.id.chat_btn:
                fragment = new ChatFragment();
                fm.beginTransaction().replace(R.id.fragment_map, fragment).addToBackStack(null)
                        .commit();
                break;
            case R.id.hist_btn:
                fragment = new HistoryFragment();
                fm.beginTransaction().replace(R.id.fragment_map, fragment).addToBackStack(null)
                        .commit();
                break;
            case R.id.player_btn:
                fragment = new PlayerFragment();
                fm.beginTransaction().replace(R.id.fragment_map, fragment).addToBackStack(null)
                        .commit();
                break;
            case R.id.test_btn:
                presenter.test();

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
}
