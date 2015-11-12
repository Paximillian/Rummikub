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
public class Tile {
    
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
    private int value;
    private Color color; 
}
