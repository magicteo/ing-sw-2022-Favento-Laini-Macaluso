package it.polimi.ingsw.model.characters;

//Characters that don't need extra attributes

public enum Type3 implements CharacterCard {

    CARD2(2), //You take control of the Professor in case of draw

    CARD4(1), //Move Mother Nature up to 2 additional islands

    CARD10(1), //Exchange up to 2 students between Entrance and Dining Room

    CARD12(3); //Choose a color. Every player must return 3 student of that color from Dining Room to the Bag

    private final int cost;
    Type3(int c){
        this.cost = c;
    }

    @Override
    public void increaseCost() {

    }

    @Override
    public void setInitialCost(int c) {

    }

    @Override
    public void effect() {

    }

    public int getCost(){
        return cost;
    }

    @Override
    public void setActive() {

    }

    @Override
    public void setInactive() {

    }

    @Override
    public void setUsedBy(String player) {

    }
}
