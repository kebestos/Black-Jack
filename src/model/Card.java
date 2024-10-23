package model;

import java.util.Objects;

public class Card {

    /**
     * Diamond, Pic, Square, Clover
     */
    private final String type;

    /**
     * 2 to 9 and AJQK
     */
    private final String value;

    public Card(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getValue() {
        if("AJQK".contains(value)){
            if(value.equals("A")){
                return 11;
            }
            return 10;
        }
        return Integer.parseInt(value);
    }

//    public String getType() {
//        return type;
//    }

    public boolean isAce() {
        return Objects.equals(value, "A");
    }

    public String toString() {
        return value + "-" + type;
    }

    public String getImagePath(){
        return "../cards/"+ this + ".png";
    }
}
