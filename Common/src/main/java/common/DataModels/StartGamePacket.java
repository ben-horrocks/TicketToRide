package common.DataModels;

import java.io.Serializable;
import java.util.List;

import common.DataModels.GameData.ClientGameData;

public class StartGamePacket implements Serializable{

    private List<Object> initialDestinationCards;
<<<<<<< HEAD
=======
    private List<Object> initialTrainCards;
>>>>>>> 1df9bd21a15e5843ad32c9ebc4de5594298f0bb7
    private ClientGameData clientGameData;

    public StartGamePacket(List<Object> initialDestinationCards, List<Object> initialTrainCards, ClientGameData clientGameData)
    {
        this.initialDestinationCards = initialDestinationCards;
<<<<<<< HEAD
=======
        this.initialTrainCards = initialTrainCards;
>>>>>>> 1df9bd21a15e5843ad32c9ebc4de5594298f0bb7
        this.clientGameData = clientGameData;
    }

    public List<Object> getInitialDestinationCards() { return initialDestinationCards; }

<<<<<<< HEAD
    public ClientGameData getClientGameData() {
        return clientGameData;
    }

    public Username getUser()
    {
        return clientGameData.getPlayer().getUser().getUsername();
    }
=======
    public List<Object> getInitialTrainCards() { return initialTrainCards; }

    public Object getClientGameData() { return clientGameData; }
>>>>>>> 1df9bd21a15e5843ad32c9ebc4de5594298f0bb7
}
