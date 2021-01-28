package com.jungko.model;

public class Settings {
    public enum GameMode {
        UNO("Uno"), UNO_FLIP("Uno Flip");

        private String custom;
        private GameMode(String custom) {
            this.custom = custom;
        }
        public String getCustomString() {
            return custom;
        }
    }

    private static GameMode gameMode = GameMode.UNO;
    private static int numberOfPlayers = 4;
    private static int cardsPerPlayer = 7;
    private static boolean multipleCards = false; // stack a card that you've just played, not from the previous player
    private static boolean stacking = false; // stack to prevent draw cards punishments
    private static boolean drawToMatch = false; // if you draw, draw until you get a playable card
    private static boolean forcePlay = false; // you have to play a card to end your turn
    private static boolean sevenSwap = false; // play 7 to swap hands with a player of your choice (except yourself)
    private static boolean zeroRotate = false; // play 0 to rotate hands in current direction
    private static boolean challenge = false; // can choose to challenge a (dark) wild draw
    private static boolean spectateBots = false; // set all players to bots
    private static boolean twoVsTwo = false; // set 2 players to control and 2 bots
    private static boolean winByScore = true; // false: win by first to N
    private static int scoreLimit = 200;
    private static int firstToN = 2;
    private static int handicapSlot1 = 0;
    private static int handicapSlot2 = 0;
    private static int handicapSlot3 = 0;
    private static int handicapSlot4 = 0;
    private static int handicapSlot5 = 0;
    // to be added

    public static GameMode getGameMode() {
        return gameMode;
    }

    public static void setGameMode(GameMode gameMode) {
        Settings.gameMode = gameMode;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        Settings.numberOfPlayers = numberOfPlayers;
    }

    public static int getCardsPerPlayer() {
        return cardsPerPlayer;
    }

    public static void setCardsPerPlayer(int cardsPerPlayer) {
        Settings.cardsPerPlayer = cardsPerPlayer;
    }

    public static boolean isStacking() {
        return stacking;
    }

    public static void setStacking(boolean stacking) {
        Settings.stacking = stacking;
    }

    public static boolean isSevenSwap() {
        return sevenSwap;
    }

    public static void setSevenSwap(boolean sevenSwap) {
        Settings.sevenSwap = sevenSwap;
    }

    public static boolean isZeroRotate() {
        return zeroRotate;
    }

    public static void setZeroRotate(boolean zeroRotate) {
        Settings.zeroRotate = zeroRotate;
    }

    public static boolean isChallenge() {
        return challenge;
    }

    public static void setChallenge(boolean challenge) {
        Settings.challenge = challenge;
    }

    public static boolean isMultipleCards() {
        return multipleCards;
    }

    public static void setMultipleCards(boolean multipleCards) {
        Settings.multipleCards = multipleCards;
    }

    public static boolean isDrawToMatch() {
        return drawToMatch;
    }

    public static void setDrawToMatch(boolean drawToMatch) {
        Settings.drawToMatch = drawToMatch;
    }

    public static boolean isForcePlay() {
        return forcePlay;
    }

    public static void setForcePlay(boolean forcePlay) {
        Settings.forcePlay = forcePlay;
    }

    public static boolean isSpectateBots() {
        return spectateBots;
    }

    public static void setSpectateBots(boolean spectateBots) {
        Settings.spectateBots = spectateBots;
    }

    public static boolean isTwoVsTwo() {
        return twoVsTwo;
    }

    public static void setTwoVsTwo(boolean twoVsTwo) {
        Settings.twoVsTwo = twoVsTwo;
    }

    public static boolean isWinByScore() {
        return winByScore;
    }

    public static void setWinByScore(boolean winByScore) {
        Settings.winByScore = winByScore;
    }

    public static int getScoreLimit() {
        return scoreLimit;
    }

    public static void setScoreLimit(int scoreLimit) {
        Settings.scoreLimit = scoreLimit;
    }

    public static int getFirstToN() {
        return firstToN;
    }

    public static void setFirstToN(int firstToN) {
        Settings.firstToN = firstToN;
    }

    public static int getHandicapSlot1() {
        return handicapSlot1;
    }

    public static void setHandicapSlot1(int handicapSlot1) {
        Settings.handicapSlot1 = handicapSlot1;
    }

    public static int getHandicapSlot2() {
        return handicapSlot2;
    }

    public static void setHandicapSlot2(int handicapSlot2) {
        Settings.handicapSlot2 = handicapSlot2;
    }

    public static int getHandicapSlot3() {
        return handicapSlot3;
    }

    public static void setHandicapSlot3(int handicapSlot3) {
        Settings.handicapSlot3 = handicapSlot3;
    }

    public static int getHandicapSlot4() {
        return handicapSlot4;
    }

    public static void setHandicapSlot4(int handicapSlot4) {
        Settings.handicapSlot4 = handicapSlot4;
    }

    public static int getHandicapSlot5() {
        return handicapSlot5;
    }

    public static void setHandicapSlot5(int handicapSlot5) {
        Settings.handicapSlot5 = handicapSlot5;
    }
}
