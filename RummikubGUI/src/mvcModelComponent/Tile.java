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
    
    private final Rank rank;
    private final Color color;
        
    public Tile(Color color, Rank rank)
    {
        this.color = color;
        this.rank = rank;
    }
    
    public Tile(String stringRepresentation)
    {
        color = Color.fromValue(stringRepresentation.substring(0, 3));
        String val = stringRepresentation.substring(3);
        
        if(val.toLowerCase().equals("j")){
            rank = Rank.JOKER;
        }
        else{
            rank = Rank.fromValue(Integer.parseInt(val));
        }
    }
    
    @Override
    public int compareTo(Object tile) {
        if(this.rank.rankValue() > ((Tile)tile).rank.rankValue())
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
            switch(v){
                case "red":
                    v = "RED";
                    break;
                case "bla":
                    v = "BLACK";
                    break;
                case "blu":
                    v = "BLUE";
                    break;
                case "yel":
                    v = "YELLOW";
                    break;
            }
            
            return valueOf(v);
        }  
        
        @Override
        public String toString(){
            return name().substring(0, 3).toLowerCase();
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
        JOKER(0);
        
        private final int value;
        
        private Rank(int value){
            this.value = value;
        }
        
        public static Rank fromValue(int val){
            Rank result = null;
            
            switch(val){
                case 1:
                    result = Rank.ONE;
                    break;
                case 2:
                    result = Rank.TWO;
                    break;
                case 3:
                    result = Rank.THREE;
                    break;
                case 4:
                    result = Rank.FOUR;
                    break;
                case 5:
                    result = Rank.FIVE;
                    break;
                case 6:
                    result = Rank.SIX;
                    break;
                case 7:
                    result = Rank.SEVEN;
                    break;
                case 8:
                    result = Rank.EIGHT;
                    break;
                case 9:
                    result = Rank.NINE;
                    break;
                case 10:
                    result = Rank.TEN;
                    break;
                case 11:
                    result = Rank.ELEVEN;
                    break;
                case 12:
                    result = Rank.TWELVE;
                    break;
                case 13:
                    result = Rank.THIRTEEN;
                    break;
                case 0:
                    result = Rank.JOKER;
                    break;
            }
            
            return result;
        }
        
        public int rankValue(){
            return this.value;
        }  
        
        @Override
        public String toString(){
            return value == 0 ? "J" : Integer.toString(value);
        }
    }
    
    public boolean isJoker(){      
        return this.rank.equals(Tile.Rank.JOKER);
    }
    
    public Rank getRank(){
        return this.rank;
    }
    
    public int getValue(){
        return this.rank.value;
    }
    
    public Color getColor(){
        return this.color;
    }
    
    @Override
    public String toString(){
        return color.toString() + rank.toString();
    }
}
