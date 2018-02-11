package cs340.TicketClient.Lobby;

import android.app.*;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import common.DataModels.Player;
import cs340.TicketClient.ASyncTask.AddGameTask;
import cs340.TicketClient.R;

public class CreateGameDialog extends DialogFragment
{
  public interface CreateGameDialogListener {
    public void onAddGame(DialogFragment frag, String newGameName);
  }
  EditText mNewGameName;
  CreateGameDialogListener mlistener;

  @Override
  public Dialog onCreateDialog(final Bundle savedInstanceState)
  {
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.new_game_fragment, null);
    mNewGameName = (EditText) view.findViewById(R.id.newGameText);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setView(view)
            .setPositiveButton(R.string.new_game_confirm, new DialogInterface.OnClickListener() {

              @Override
              public void onClick(DialogInterface dialogInterface, int i)
              {
                mlistener.onAddGame(CreateGameDialog.this, mNewGameName.getText().toString());
              }
            })
            .setNegativeButton(R.string.new_game_cancel, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i)
              {
                 CreateGameDialog.this.getDialog().cancel();
              }
            });
    return builder.create();
  }
  @Override
  public void onAttach(Activity activity)
  {
    super.onAttach(activity);

    try {
      mlistener = (CreateGameDialogListener) activity;
    } catch (ClassCastException e) {
      Toast.makeText(activity, "This activity must implement CreateGameDialogListener", Toast.LENGTH_SHORT).show();
    }
  }
}
