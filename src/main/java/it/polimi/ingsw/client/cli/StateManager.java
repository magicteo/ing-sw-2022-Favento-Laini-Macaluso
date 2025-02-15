package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.cli.gameStates.State;

public class StateManager implements Runnable{
    private final CLI cli;
    private final State nextState;

    public StateManager(CLI cli, State nextState) {
        this.cli = cli;
        this.nextState = nextState;
    }

    @Override
    public void run() {
        if (cli.getCurrentThread() != null) {
            try {
                cli.getCurrentThread().join(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        cli.setGameState(nextState);
        Thread newThread = new Thread(nextState);
        cli.setCurrentThread(newThread);
        newThread.start();
    }
}
