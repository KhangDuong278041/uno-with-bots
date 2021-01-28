package com.jungko.model;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Card {
    public enum LightColor{
        RED("Red"), BLUE("Blue"), GREEN("Green"), YELLOW("Yellow"), WILD("Wild");

        private String custom;
        private LightColor(String custom) {
            this.custom = custom;
        }
        public String getCustomString() {
            return custom;
        }
    }
    public enum DarkColor{
        GARNET("Garnet"), AMETHYST("Amethyst"), EMERALD("Emerald"),
        AMBER("Amber"), DARK_WILD("Dark Wild"), NONE("How the heck did you get here?");

        private String custom;
        private DarkColor(String custom) {
            this.custom = custom;
        }
        public String getCustomString() {
            return custom;
        }
    }

    public enum LightValue{
        ZERO("0", 0), ONE("1", 1), TWO("2", 2), THREE("3", 3), FOUR("4", 4),
        FIVE("5", 5), SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9),
        SKIP("Skip", 20), REVERSE("Reverse", 20), DRAW_ONE("+1", 10), DRAW_TWO("+2", 20),
        WILD("Card", 50), WILD_DRAW_TWO("+2", 50), WILD_DRAW_FOUR("+4", 50), FLIP("Flip", 20);

        private String custom;
        private int points;
        private LightValue(String custom, int points) {
            this.custom = custom;
            this.points = points;
        }
        public String getCustomString() {
            return custom;
        }
        public int getPoints(){
            return points;
        }
    }
    public enum DarkValue{
        D_ONE("1", 1), D_TWO("2", 2), D_THREE("3", 3), D_FOUR("4", 4), D_FIVE("5", 5),
        D_SIX("6", 6), D_SEVEN("7", 7), D_EIGHT("8", 8), D_NINE("9", 9),
        SKIP_ALL("Skip all", 30), D_REVERSE("Reverse", 20), DRAW_FIVE("+5", 20),
        D_WILD("Card", 40), D_WILD_DRAW("Draw", 60), D_FLIP("Flip", 20), NONE("Why?", 1000);

        private String custom;
        private int points;
        private DarkValue(String custom, int points) {
            this.custom = custom;
            this.points = points;
        }
        public String getCustomString() {
            return custom;
        }
        public int getPoints(){
            return points;
        }
    }

    private LightColor lightColor;
    private LightValue lightValue;
    private DarkColor darkColor;
    private DarkValue darkValue;
    private Rectangle invisibleCard;
    private Group visibleCard;

    public Card(LightColor lightColor, LightValue lightValue, DarkColor darkColor, DarkValue darkValue) {
        this.lightColor = lightColor;
        this.lightValue = lightValue;
        this.darkColor = darkColor;
        this.darkValue = darkValue;
        visibleCard = new Group();
    }

    public LightColor getLightColor() {
        return lightColor;
    }

    public LightValue getLightValue() {
        return lightValue;
    }

    public DarkColor getDarkColor() {
        return darkColor;
    }

    public DarkValue getDarkValue() {
        return darkValue;
    }

    public Rectangle getInvisibleCard() {
        return invisibleCard;
    }

    public Group getVisibleCard() {
        return visibleCard;
    }

    public void setLightColor(LightColor lightColor) {
        this.lightColor = lightColor;
    }

    public void setDarkColor(DarkColor darkColor) {
        this.darkColor = darkColor;
    }

    public void setInvisibleCard(Rectangle invisibleCard) {
        this.invisibleCard = invisibleCard;
    }

    // no scale
}
