package it.polimi.ingsw.model;

public class Professor {
    private Color color;
    private String owner;

    public Professor(){
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color= color;
    }

    //public void changeOwner(player:Player){}

    public String getOwnerNickname(){
        return owner;
    }
}