/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;


/**
 *
 * @author yafita870
 */
public class Tile implements Comparable{

    @Override
    public int compareTo(Object tile) {
        if(this.renk.rankValue() > ((Tile)tile).renk.rankValue())
            return 1;
        else return 0;
    }
    
    
    public enum Color {

        BLACK,
        RED,
        BLUE,
        YELLOW;

        public String value() {
            return name();
        }

        public static Color fromValue(String v) {
            return valueOf(v);
        }           
    }
    
    public enum Rank{
        
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        ELEVEN(11),
        TWELVE(12),
        THIRTEEN(13),
        JOKER(14);
        
        private final int value;
        
        private  Rank(int value){
            this.value = value;
        }
        public int rankValue(){
            return this.value;
        }  
    }
    
    public boolean isJoker(){      
        return this.renk == Tile.Rank.JOKER;
    }
    
    public Tile(Color color, Rank renk)
    {
        this.color = color;
        this.renk = renk;
    }
    
    private final Rank renk;
    private final Color color;
    
    public Rank getRank(){
        return this.renk;
    }
    
    public Color getColor(){
        return this.color;
    }
    
}
