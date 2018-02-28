package common.DataModels;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jhens on 2/28/2018.
 */

public class StartGamePacket implements Serializable{

    List<Object> initialDestinationCards;
    List<Object> initialTrainCards;
    Object clientGameObject;

    public StartGamePacket(List<Object> initialDestinationCards, List<Object> initialTrainCards, Object clientGameObject)
    {
        this.initialDestinationCards = initialDestinationCards;
        this.initialTrainCards = initialTrainCards;
        this.clientGameObject = clientGameObject;
    }

    public List<Object> getInitialDestinationCards() {
        return initialDestinationCards;
    }

    public List<Object> getInitialTrainCards() {
        return initialTrainCards;
    }

    public Object getClientGameObject() {
        return clientGameObject;
    }
}
