package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.StateManager;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * In the EndOfGame phase, every player receives a message from the server containing the result of the
 * game (win or lose), the nickname of the winner player and the reason of the win.
 */
public class EndOfGameState implements State {
    private final CLI cli;

    public EndOfGameState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        System.out.print("This is the end of the game! ");
        if (cli.getView().getWinnerTeam() == null) {
            System.out.println("It's a tie, congratulations to everyone that played!");
            switch (cli.getView().getEndOfGameReason()) {
                case THREE_ISLANDS_REMAINING_TIE -> System.out.print("There were only three islands remaining.");
                case LAST_ROUND_TIE -> System.out.print("It was the last round.");
            }
        } else {
            System.out.print(cli.getView().getWinnerTeam() + " team won because ");
            switch (cli.getView().getEndOfGameReason()) {
                case LAST_TOWER_BUILD -> System.out.println("they built their last tower.");
                case THREE_ISLANDS_REMAINING_TOWER -> System.out.println("there are only three islands left and they " +
                        "built more towers than the others.");
                case THREE_ISLANDS_REMAINING_PROFESSOR -> System.out.println("there are only three islands left and they " +
                        "have more professors than the others.");
                case LAST_ROUND_TOWER -> System.out.println("it was the last round and they " +
                        "built more towers than the others.");
                case LAST_ROUND_PROFESSOR -> System.out.println("it was  the last round and they " +
                        "have more professors than the others.");
            }
            System.out.print("Congratulations to ");
            for (String winner : cli.getView().getWinners()) {
                System.out.printf("%s%s",
                        winner,
                        cli.getView().getWinners().indexOf(winner) == cli.getView().getWinners().size() - 1 ? "!" : ", ");
            }
        }
        System.out.println();


        int choice;
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Press 0 to go back to the menu, or 1 to close the game.");
            in.reset();
            try {
                choice = in.nextInt();

                if (choice != 0 && choice != 1) {
                    System.out.println("This option is not valid.");
                }
            } catch (InputMismatchException e) {
                System.out.println("You have to enter an integer.");
                choice = -1;
                in.next();
            }
        } while (choice != 0 && choice != 1);

        if (choice == 0) {
            new Thread(new StateManager(cli, new LobbyState(cli))).start();
        } else {
            cli.getClient().closeConnection();
        }
    }
}
