package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.CloudsRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.PlayersOrderRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.SchoolBoardRenderer;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.PlayAssistant;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PlanningState implements State{
    private final CLI cli;

    public PlanningState(CLI cli){
        this.cli = cli;
    }

    @Override
    public void run(){
        Scanner in = new Scanner(System.in);

        cli.getClient().sendMessage(new Ack());
        try{
            synchronized (this){
                wait();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        printCompleteBoard();

        while(!cli.isSuccess()){

            int selection;
            Assistant selectedAssistant;

            ArrayList<Assistant> availableAssistants = cli.getView().getAvailableAssistants();
            System.out.println("These are the available assistants");
            System.out.println("|---NAME------VALUE----MOVEMENTS---");
            for(int i=0; i<availableAssistants.size(); i++){
                System.out.println(i+1 + ":   " + availableAssistants.get(i).toString() +
                        "           " + availableAssistants.get(i).getValue() +
                        "           " + availableAssistants.get(i).getMovements());
            }
            System.out.println("|----------------------------------");
            for (String player : cli.getView().getPlayedAssistants().keySet()) {
                System.out.printf("%s played %s (%d value, %d movement)%n",
                        player,
                        cli.getView().getPlayedAssistants().get(player),
                        cli.getView().getPlayedAssistants().get(player).getValue(),
                        cli.getView().getPlayedAssistants().get(player).getMovements());
            }
            System.out.println("Insert the number of the assistant you want to play: ");
            in.reset();

            try {
                selection = in.nextInt() - 1;
                selectedAssistant = availableAssistants.get(selection);

                cli.getClient().sendMessage(new PlayAssistant(selectedAssistant));
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("This choice is not available.");
            } catch (InputMismatchException e) {
                System.out.println("You have to enter a number according to your choice.");
                in.next();
            }



        }
        if(cli.isSuccess()){
            cli.setSuccess(false);
            cli.getView().setRoundNumber(1);
        }
    }

    private void printCompleteBoard() {
        CloudsRenderer.cloudRenderer(cli.getView().getDashboard());
        IslandsRenderer.islandsRenderer(cli.getView().getDashboard());
        SchoolBoardRenderer.renderAllSchoolBoards(cli.getView().getPlayers());
        System.out.println("The order for the first round was extracted randomly:");
        if (cli.isFirstPlayer()) {
            PlayersOrderRenderer.playersOrderRenderer(cli.getView().getPlayers());
        }
        System.out.println();
    }
}