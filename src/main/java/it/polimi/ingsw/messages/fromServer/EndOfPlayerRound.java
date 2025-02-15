package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;

/**
 * Message sent by the server to communicate the end of the active player's actions for the current round.
 */
public class EndOfPlayerRound implements FromServerMessage {
    private final int roundNumber;
    private final String nickname;

    public EndOfPlayerRound(int roundNumber, String nickname){
        this.roundNumber = roundNumber;
        this.nickname = nickname;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
