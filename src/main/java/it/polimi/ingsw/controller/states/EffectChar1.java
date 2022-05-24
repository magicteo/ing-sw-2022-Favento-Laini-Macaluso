package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.messages.fromServer.MovableStudents;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.messages.fromServer.WhereToMove;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.characters.Char1;


public class EffectChar1 implements ResumableState {

    Game game;
    Controller controller;
    ResumableState previousState;
    Char1 char1;
    boolean requestedAck = false;
    boolean requestedStudent = false;
    boolean requestedIsland = false;
    Color color;


    public EffectChar1(Game game, Controller controller, ResumableState previousState, Char1 char1) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char1 = char1;
    }

    @Override
    public void resume() {

    }

    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        requestedStudent=true;
        //pick a student on this card
        controller.notify(new MovableStudents(char1.getStudents()));
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenStudent && requestedStudent) {
            receiveStudent((ChosenStudent) message);}
        if (message instanceof ChosenDestination && requestedIsland){
            receiveIsland((ChosenDestination) message);}
        if (message instanceof Ack && requestedAck){
            nextState();
        }

    }

    private void receiveStudent(ChosenStudent message) {
        Color student = message.getStudent();
        if (!(char1.getStudents().contains(student))) {
            controller.notify(ErrorMessage.STUDENT_NOT_AVAILABLE);
        }
        else {
            this.color=student;
            requestedStudent=false;
            requestedIsland=true;
            //choose an island to move student on
            controller.notify(new WhereToMove(null, game.getDashboard().getIslands()));
        }
    }

    private void receiveIsland(ChosenDestination message){
        if (!(message.getDestination() instanceof Island)){
            controller.notify(ErrorMessage.INVALID_INPUT);
        }
        else {
            requestedIsland=false;
            //put the student on the island
            message.getDestination().addStudent(color);
            //remove student from Char1
            char1.removeStudent(color);
            //draw a new student from the bag and place it on Char1
            char1.addStudent(game.getDashboard().getBag().drawStudent());
            //Send the updated board
            requestedAck=true;
            controller.notify(new UpdateBoard(game.getDashboard().getPlayedCharacters(), game.getDashboard(), game.getOnlinePlayers()));
        }
    }

}