package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.SchoolBoardRenderer;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenSteps;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.player.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ActionStep2State implements State{
    private final CLI cli;

    public ActionStep2State(CLI cli){
        this.cli = cli;
    }

    @Override
    public void run(){
        int steps;

        cli.getClient().sendMessage(new Ack());

        try{
            synchronized (this){
                wait();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        while (!cli.isSuccess()){
            Scanner in = new Scanner(System.in);

            steps = cli.getView().getMotherNatureSteps();

            SchoolBoardRenderer.renderAllSchoolBoards(cli.getView().getPlayers());
            IslandsRenderer.islandsRenderer(cli.getView().getDashboard());

            System.out.printf("How many steps do you want MotherNature to make? Choose a number between 1 and %d: \n", steps);

            try {
                in.reset();
                steps = in.nextInt();

                cli.getClient().sendMessage(new ChosenSteps(steps));

                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        if (cli.isSuccess()) {
            cli.setSuccess(false);
        }
        System.out.println("These are the islands after Move 2:");
        IslandsRenderer.islandsRenderer(cli.getView().getDashboard());
    }
}