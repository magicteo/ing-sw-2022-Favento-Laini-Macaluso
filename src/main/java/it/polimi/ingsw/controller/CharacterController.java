package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterController {
    Controller controller;
    Game game;
    VirtualView virtualview;

    public CharacterController(Controller c, Game g, VirtualView v){
        this.controller=c;
        this.game=g;
        this.virtualview=v;
    }

    public void generateCharacters(Dashboard dashboard) {
        ArrayList<CharacterEnum> allCharacters = new ArrayList<>();
        CharacterFactory factory = new CharacterFactory();
        ArrayList<CharacterCard> gameCharacters = new ArrayList<>();

        Collections.addAll(allCharacters, CharacterEnum.values());
        Collections.shuffle(allCharacters);
        for (int i = 0; i < 3; i++) {
            gameCharacters.add(factory.getCharacter(allCharacters.get(i)));
            setUpCharacter(gameCharacters.get(i),dashboard.getBag());
        }
        dashboard.setCharacters(gameCharacters);

    }

    public void setUpCharacter(CharacterCard c, Bag bag){
        switch  (c.getValue()){
            case Char1:
            case Char7:
            case Char11:
                c.setUp(bag);
                break;
        }
    }

    public void applyEffect(CharacterCard c) throws NotEnoughCoinsException, InvalidInputException, NoEntryTilesLeftException, StudentNotExistingException {
        if (c==null) throw new InvalidInputException("The input is not a Character");
        //Check if player has enough coins
        if (!verifyCoins(c)){
            throw new NotEnoughCoinsException("You don't have enough coins");
        }
        else {
            c.increaseCost();
            c.setActive();
            c.setUsedBy(game.getCurrentPlayer().getNickname());
            if (c instanceof Char1){
                activate((Char1) c);}
            if (c instanceof Char2){
                activate((Char2) c);}
            if (c instanceof Char3){
                activate((Char3) c);}
            if (c instanceof Char4){
                activate((Char4) c);}
            if (c instanceof Char5){
                activate((Char5) c);}
            if (c instanceof Char6){
                activate((Char6) c);}
            if (c instanceof Char7){
                activate((Char7) c);}
            if (c instanceof Char8){
                activate((Char8) c);}
            if (c instanceof Char9){
                activate((Char9) c);}
            if (c instanceof Char10){
                activate((Char10) c);}
            if (c instanceof Char11){
                activate((Char11) c);}
            if (c instanceof Char12){
                activate((Char12) c);}
            c.setInactive();
            c.setUsedBy(null);
        }
    }

    public void activate(Char1 c) {
        //pick a student on this card
        //Color color = virtualview.selectStudentFrom(c.getStudents());
        //choose an island to move student on
        //Island island = virtualview.selectIsland(game.getDashboard().getIslands());
        //put the student on the island
        //island.addStudent(color);
        //c.removeStudent(color);
        //draw a new student from the bag
        //c.addStudent(game.getDashboard().getBag().drawStudent());
        //virtualview.done();
        //set the character to inactive
        //c.setInactive();
        //c.setUsedBy=null;
    }

    public void activate(Char2 c){
        //during this turn, you take control of any number of Professors even if you have
        //the same number of students as the player who currently controls them
        for (int i=0; i<5; i++) {
            if (!(game.getDashboard().getProfessorwithindex(i).getOwner()==game.getCurrentPlayer())){
                Professor p= game.getDashboard().getProfessorwithindex(i);
                int temp;
                Color color= game.getDashboard().getProfessorwithindex(i).getColor();
                temp=game.getDashboard().getProfessorwithindex(i).getOwner().getSchoolBoard().getDiningRoom().getStudentsNumber(color);
                if (temp==game.getCurrentPlayer().getSchoolBoard().getDiningRoom().getStudentsNumber(color)){
                    game.getCurrentPlayer().getSchoolBoard().addProfessor(p);
                    game.getDashboard().getProfessorwithindex(i).getOwner().getSchoolBoard().removeProfessor(p);
                }
            }
            //remember to modify the controller method to assign professors to the player
            //at the end of the player turn, remember to verify if the professor owner is the same as the player who currently has it on the schoolboard
        }

    }

    public void activate(Char3 c) {
        //resolve an island as if Mother Nature has ended her movement here
        //ask the player to select the island
        //Island island = virtualview.selectIslandfrom(game.getDashboard().getIslands());
        //resolve the island
        //controller.resolveIsland(island);
        //controller.notify(new UpdateBoard(game.getDashboard().getPlayedCharacters(), game.getDashboard()));
    }

    public void activate(Char4 c) {
        //you may move mother nature up to 2 additional islands
        game.getDashboard().setAdditionalMNMovements(2);
    }

    public void activate(Char5 c) {
        //place a No Entry tile on an Island of your choice
        //The first time mother nature ends her movement there, put the No Entry tile back onto this card
        //remove No Entry Tile from the character card
        //c.useNoEntryTile();
        //select the Island
        //Island island = virtualview.selectIsland(game.getDashboard().getIslands());
        //place No Entry Tile on selected island
        //island.addNoEntry();
        }

        public void activate (Char6 c){
            //when resolving a conquering on an island, towers do not count towards influence
            game.getDashboard().enableDoNotCountTowers();
        }

        public void activate(Char7 c){
            //You may take up to 3 students from this card and replace them with the same number of students from your entrance
            //TODO
        }

        public void activate(Char8 c){
            //During the influence calculation this turn, you count as having 2 more influence
            for (Island island:game.getDashboard().getIslands()){
                island.setExtraInfluence(game.getCurrentPlayer(),2);
            }
        }

        public void activate(Char9 c){
            //Choose a color of Student: during the influence calculation this turn, that color adds no influence
            ArrayList<Color> colors=new ArrayList<>();
            Collections.addAll(colors, Color.values());
            //Color color= virtualview.chooseColor(colors);
            //game.getDashboard().setDoNotCountColor(color);
        }

        public void activate(Char10 c){
            //You may exchange up to 2 Students between your Entrance and your Dining Room
            //TODO
        }

        public void activate(Char11 c){
            //Take 1 student from this card and place it in your Dining Room
            //Color color= virtualview.selectStudentFrom(c.getStudents());
            //add to dining room
            //game.getCurrentPlayer().getSchoolBoard().getDiningRoom().addStudent(color);
            //remove from this card
            //c.removeStudent(color);
            //draw a new student from the bag and place it on this card
            c.addStudent(game.getDashboard().getBag().drawStudent());
        }

        public void activate(Char12 c){
            //Choose a type of student: every player (including the current player) must return
            // 3 students of that type from their dining room to the bag
            //choose color
            //ArrayList<Color> colors=new ArrayList<>();
            //Collections.addAll(colors, Color.values());
            //Color color= virtualview.chooseColor(colors);
            //every player must return 3 student of this color to the bag
            //for (Player player: game.getOnlinePlayers()) {
            //for (int i=0; i<3; i++) {
            //player.getSchoolBoard().getDiningRoom().extractStudent(color);
            //game.getDashboard().getBag().addStudent(color);
            //}
            //}
        }




    public boolean verifyCoins(CharacterCard c) throws NotEnoughCoinsException {
            if((game.getCurrentPlayer().getSchoolBoard().getCoins())>=(c.getCost())){
                game.getCurrentPlayer().getSchoolBoard().spendCoins(c.getCost());
                return true;
            }
            else{
            return false;}
    }
}
