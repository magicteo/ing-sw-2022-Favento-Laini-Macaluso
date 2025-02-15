package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.model.GameInfo;

import java.util.ArrayList;

/**
 * Message sent by server containing information of all the available games a player can join.
 */
public class AvailableGames implements FromServerMessage {
    private final ArrayList<GameInfo> availableGames;

    public AvailableGames(ArrayList<GameInfo> availableGames) {
        this.availableGames = availableGames;
    }

    public ArrayList<GameInfo> getAvailableGames() {
        return availableGames;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
