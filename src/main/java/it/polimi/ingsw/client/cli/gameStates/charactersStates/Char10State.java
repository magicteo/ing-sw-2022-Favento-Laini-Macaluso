package it.polimi.ingsw.client.cli.gameStates.charactersStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.SchoolBoardRenderer;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

/**
 * character 10 state
 */
public class Char10State {

    /**
     * method used to print to command line the effects of character 10
     * @param cli the game's command line interface
     */
    public void run(CLI cli) {
        Scanner in = new Scanner(System.in);
        int selection;

        while (!cli.isSuccess()) {
            if (cli.getView().getMovableStudentsChar() == null) {
                try {
                    synchronized (cli.getGameState()) {
                        cli.getGameState().wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }

            ArrayList<Color> movableStudentsChar = cli.getView().getMovableStudentsChar();

            SchoolBoardRenderer.renderSchoolBoard(Objects.requireNonNull(cli.getView().getPlayers()
                            .stream()
                            .filter(p -> Objects.equals(p.getNickname(), cli.getClient().getNickname()))
                            .findAny()
                            .orElse(null)),
                    cli.getView().isExpertMode());

            System.out.println("Select a student to move from your entrance to your dining room, or enter 0 to stop: ");
            for (Color color : Color.values()) {
                System.out.printf("%d --> %s: " + movableStudentsChar.stream().filter(a -> a == color).count() + "%n",
                        color.ordinal() + 1,
                        color.toString().substring(0, 1).toUpperCase() + color.toString().substring(1));
            }

            try {
                in.reset();
                selection = in.nextInt();
                Color chosenStudent = null;
                for (Color color : Color.values()) {
                    if (color.ordinal() + 1 == selection) {
                        chosenStudent = color;
                    }
                }
                if (selection == 0) {
                    cli.getClient().sendMessage(new ChosenStudent(null));
                    while (!cli.isSuccess()) {
                        try {
                            synchronized (cli.getGameState()) {
                                cli.getGameState().wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    break;
                } else if (cli.getView().getMovableStudentsChar().contains(chosenStudent)) {
                    cli.getView().setMovableStudentsChar(null);
                    cli.getClient().sendMessage(new ChosenStudent(chosenStudent));
                } else {
                    System.out.println("Please enter a valid student.");
                    continue;
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.println("Please enter an integer according to your choice.");
                continue;
            }

            if (cli.getView().getMovableStudentsChar() == null) {
                try {
                    synchronized (cli.getGameState()) {
                        cli.getGameState().wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            SchoolBoardRenderer.renderSchoolBoard(Objects.requireNonNull(cli.getView().getPlayers()
                            .stream()
                            .filter(p -> Objects.equals(p.getNickname(), cli.getClient().getNickname()))
                            .findAny()
                            .orElse(null)),
                    cli.getView().isExpertMode());

            movableStudentsChar = cli.getView().getMovableStudentsChar();

            System.out.println("Select a student to move from your dining room to your entrance: ");
            for (Color color : Color.values()) {
                System.out.printf("%d --> %s: " + movableStudentsChar.stream().filter(a -> a == color).count() + "%n",
                        color.ordinal() + 1,
                        color.toString().substring(0, 1).toUpperCase() + color.toString().substring(1));
            }

            try {
                in.reset();
                selection = in.nextInt();
                Color chosenStudent = null;
                for (Color color : Color.values()) {
                    if (color.ordinal() + 1 == selection) {
                        chosenStudent = color;
                    }
                }
                if (cli.getView().getMovableStudentsChar().contains(chosenStudent)) {
                    cli.getClient().sendMessage(new ChosenStudent(chosenStudent));
                } else {
                    cli.getClient().sendMessage(new ChosenStudent(null));
                }
            } catch (InputMismatchException e) {
                in.next();
                cli.getClient().sendMessage(new ChosenStudent(null));
            }
            cli.getView().setMovableStudentsChar(null);
            if (cli.getView().getMovableStudentsChar() == null && !cli.isSuccess()) {
                try {
                    synchronized (cli.getGameState()) {
                        cli.getGameState().wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (cli.isSuccess()) {
            cli.setSuccess(false);
            cli.getView().setMovableStudentsChar(null);
        }
    }
}
