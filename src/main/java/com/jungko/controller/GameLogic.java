package com.jungko.controller;

import com.jungko.App;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import com.jungko.model.Card;
import com.jungko.model.Card.DarkColor;
import com.jungko.model.Card.DarkValue;
import com.jungko.model.Card.LightColor;
import com.jungko.model.Card.LightValue;
import com.jungko.model.Player;
import com.jungko.model.Settings;
import com.jungko.model.Settings.GameMode;

import java.util.*;

public class GameLogic {
    // the pane that elements will be displayed on
    private Pane gamePane;
    // the pane that scoreboard will be displayed on
    private Pane scorePane;
    // if the game is currently in Light Side
    private boolean lightSide;
    // if you are viewing your hand from the front
    private boolean viewingFront;
    // if the current sorting method is by color (false is sorting by value)
    private boolean sortByColor;
    // if the sorting method will be switched when the sort button is clicked
    private boolean switchSortMethod;

    private int[] currentScores;
    private int[] botAvatarIndexes;

    private final int avatarSize = 100;

    private final String avatarsDir = "/com/jungko/avatars/";

    private final String avatarHuman = "avatar_human.png";

    private final String avatarRobotBlack = "avatar_robot_black.png";
    private final String avatarRobotRed = "avatar_robot_red.png";
    private final String avatarRobotBlue = "avatar_robot_blue.png";
    private final String avatarRobotGreen = "avatar_robot_green.png";
    private final String avatarRobotYellow = "avatar_robot_yellow.png";
    private final String avatarRobotRuby = "avatar_robot_garnet.png";
    private final String avatarRobotAmethyst = "avatar_robot_amethyst.png";
    private final String avatarRobotEmerald = "avatar_robot_emerald.png";
    private final String avatarRobotAmber = "avatar_robot_amber.png";
    private final String avatarRobotMoon = "avatar_robot_moon.png";

    private final String avatarBorder = "avatar_border.png";

    // if you're locked from playing/drawing
    private boolean lockingUserAction;

    // if you have played a card
    private boolean youPlayedACard;
    // if you have drawn a card
    private boolean youDrawnACard;

    // card's dimension, number of cards in each row for each hand, and the gap between cards
    private final int cardWidth = 66;
    private final int cardHeight = 100;
    private final int cardsPerRow = 8;
    private final int cardGap = 30;

    // deck and discard pile's positions
    private final int deckX = 594;
    private final int deckY = 310;
    private final int discardX = 714;
    private final int discardY = 310;

    // folder where the cards are in
    private final String cardsDir = "/com/jungko/cards/"; //"file:src/uno/resources/";

    // file name of every card
    private final String colorRed = "color_red.png";
    private final String colorBlue = "color_blue.png";
    private final String colorGreen = "color_green.png";
    private final String colorYellow = "color_yellow.png";
    private final String colorWild = "color_wild.png";

    private final String colorGarnet = "color_garnet.png";
    private final String colorAmethyst = "color_amethyst.png";
    private final String colorEmerald = "color_emerald.png";
    private final String colorAmber = "color_amber.png";
    private final String colorDarkWild = "color_dark_wild.png";
    private final String colorNone = "color_none.png";

    private final String value0 = "value_0.png";
    private final String value1 = "value_1.png";
    private final String value2 = "value_2.png";
    private final String value3 = "value_3.png";
    private final String value4 = "value_4.png";
    private final String value5 = "value_5.png";
    private final String value6 = "value_6.png";
    private final String value7 = "value_7.png";
    private final String value8 = "value_8.png";
    private final String value9 = "value_9.png";
    private final String valueSkip = "value_skip.png";
    private final String valueReverse = "value_reverse.png";
    private final String valueDraw1 = "value_draw_1.png";
    private final String valueDraw2 = "value_draw_2.png";
    private final String valueWild = "value_wild.png";
    private final String valueWildDraw2 = "value_wild_draw_2.png";
    private final String valueWildDraw4 = "value_wild_draw_4.png";
    private final String valueFlip = "value_flip.png";

    private final String valueD1 = "value_d_1.png";
    private final String valueD2 = "value_d_2.png";
    private final String valueD3 = "value_d_3.png";
    private final String valueD4 = "value_d_4.png";
    private final String valueD5 = "value_d_5.png";
    private final String valueD6 = "value_d_6.png";
    private final String valueD7 = "value_d_7.png";
    private final String valueD8 = "value_d_8.png";
    private final String valueD9 = "value_d_9.png";
    private final String valueSkipAll = "value_skip_all.png";
    private final String valueDReverse = "value_d_reverse.png";
    private final String valueDraw5 = "value_draw_5.png";
    private final String valueDWild = "value_d_wild.png";
    private final String valueDWildDraw = "value_d_wild_draw.png";
    private final String valueDFlip = "value_d_flip.png";

    // the deck, discard pile, you and list of players
    private Stack<Card> deck;
    private Stack<Card> discardPile;
    private Player you; // MIGHT FIX THIS FOR 2V2 ????
    private ArrayList<Player> players;

    // elements' references from the game screen controller
    private Button sortHandBtn;
    private Button viewOtherSideBtn;
    private Button endTurnBtn;
    private Button acceptBtn;
    private Button challengeBtn;
    private Group lightBtnGroup;
    private Group darkBtnGroup;

    private Group sevenSwapBtnGroup;
    private final int[][][] sevenSwapBtnLocations = {
            {{644, 420, 135}, {790, 240, -45}},
            {{654, 420, 120}, {654, 240, 240}, {800, 330, 0}},
            {{717, 430, 90}, {634, 330, 180}, {717, 230, 270}, {800, 330, 0}},
            {{717, 430, 90}, {624, 350, 162}, {654, 240, 234}, {780, 240, 306}, {810, 350, 18}}
    };

    // history to keep track of what happens during the game
    private TextArea gameHistory;

    // turn of this player
    private Player currentPlayer;
    // last card a player played ???
    private Card lastPlayedCard;
    // last card a player drawn ???
    private Card lastDrawnCard;

    // if a (dark) wild card appears at the start of a player's turn
    private boolean wildOnTurn;
    // if a bot has played a card
    private boolean botPlayed;
    // if a bot has drawn a card
    private boolean botDrawn;

    // number of players
    private int noOfPlayers;
    // turn number, this is used to indicate the current player in the player list
    private int turn;
    // if the current direction is clockwise
    private boolean clockwise;
    // if the game has ended
    private boolean gameEnd;

    // the ImageView for avatar glow (indicating it's their turn) and direction gif
    private ImageView avatarGlowIV;
    private ImageView winnerGlowIV;
    private ImageView directionIV;
    private Image cwImg;
    private Image ccwImg;

    // folder where the effects are in
    private final String effectDir = "/com/jungko/effects/";

    // file name of every effect
    private final String avatarGlow = "effect_avatar_glow.png";
    private final String directionCWGif = "effect_cw.gif";
    private final String directionCCWGif = "effect_ccw.gif";
    private final String winnerGlow = "effect_winner_glow.png";


    // position of the glow is moved 10px up-left from the avatar's position to fit
    private final int glowOffset = 10;
    // position of the direction gif
    private final int directionGifX = 547;
    private final int directionGifY = 160;

    private boolean goingToApplyPunishment;
    private int noOfPlayersToSkip;
    private int noOfCardsToDraw;
    private int drawToMatchColorRepeatTimes;

    private int dealPlayerIndex;
    private int dealRound;

    private ArrayList<Text> scoresText;
    private CheckBox saveLogCheck;
    private Button continueBtn;
    private Button toggleScoreboardBtn;
    private Button playAgainBtn;
    private Button backToMenuBtn;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialization
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // initiate variables
    GameLogic(Pane gamePane, Pane scorePane, Stack<Card> deck, Stack<Card> discardPile, TextArea gameHistory) {
        this.gamePane = gamePane;
        this.scorePane = scorePane;
        lightSide = true;
        viewingFront = true;
        sortByColor = true;
        switchSortMethod = false;
//        playingACard = false;
//        drawingACard = false;
//        waitingToStart = true;
        lockingUserAction = true;
//        youPlayedACard = false;
//        youDrawnACard = false;

        this.deck = deck;
        this.discardPile = discardPile;
        this.gameHistory = gameHistory;

        clockwise = true;
        gameEnd = false;
        avatarGlowIV = new ImageView(App.getResourceImg(effectDir + avatarGlow));
        winnerGlowIV = new ImageView(App.getResourceImg(effectDir + avatarGlow));

        directionIV = new ImageView();
        directionIV.setTranslateX(directionGifX);
        directionIV.setTranslateY(directionGifY);

        cwImg = new Image(App.getResourceImg(effectDir + directionCWGif));
        ccwImg = new Image(App.getResourceImg(effectDir + directionCCWGif));

        goingToApplyPunishment = false;
        noOfPlayersToSkip = 0;
        noOfCardsToDraw = 0;
        drawToMatchColorRepeatTimes = 0;

        scoresText = new ArrayList<>();
    }

    // pass buttons' reference from GameScreenController
    void setButtons(Button sortHandBtn, Button viewOtherSideBtn, Button endTurnBtn, Group lightBtnGroup, Group darkBtnGroup,
                    CheckBox saveLogCheck, Button continueBtn, Button toggleScoreboardBtn, Button playAgainBtn, Button backToMenuBtn,
                    Button acceptBtn){
        this.sortHandBtn = sortHandBtn;
        this.viewOtherSideBtn = viewOtherSideBtn;
        this.endTurnBtn = endTurnBtn;
        this.lightBtnGroup = lightBtnGroup;
        this.darkBtnGroup = darkBtnGroup;
        this.saveLogCheck = saveLogCheck;
        this.continueBtn = continueBtn;
        this.toggleScoreboardBtn = toggleScoreboardBtn;
        this.playAgainBtn = playAgainBtn;
        this.backToMenuBtn = backToMenuBtn;
        this.acceptBtn = acceptBtn;
    }

    ArrayList<Player> createPlayers(int noOfPlayers, int[] currentScores, int[] botAvatarIndexes, boolean includeYou){
        this.currentScores = currentScores;
        this.botAvatarIndexes = botAvatarIndexes;
        ArrayList<Player> players = new ArrayList<>();

        // robots' names
        ArrayList<String> robotsNames = new ArrayList<>();
        robotsNames.add("A-Bot");
        robotsNames.add("B-Bot");
        robotsNames.add("C-Bot");
        robotsNames.add("D-Bot");
        robotsNames.add("E-Bot");

        // shuffle robots' avatars
        ArrayList<String> robotsList = new ArrayList<>();
        robotsList.add(avatarRobotBlack);
        robotsList.add(avatarRobotRed);
        robotsList.add(avatarRobotBlue);
        robotsList.add(avatarRobotGreen);
        robotsList.add(avatarRobotYellow);
        robotsList.add(avatarRobotRuby);
        robotsList.add(avatarRobotAmethyst);
        robotsList.add(avatarRobotEmerald);
        robotsList.add(avatarRobotAmber);
        robotsList.add(avatarRobotMoon);
//        Collections.shuffle(robotsList); // NOT RANDOMIZING WHEN CONTINUE ?

        if(includeYou){
            // add you as first slot
            String yourName = "Player";
            players.add(new Player(1, yourName, new Image(App.getResourceImg(avatarsDir + avatarHuman),
                    avatarSize, avatarSize, false, true), new ArrayList<>(), currentScores[0], true));
        }else{
            // add robot as first slot
            players.add(new Player(1, robotsNames.get(4), new Image(App.getResourceImg(avatarsDir + robotsList.get(botAvatarIndexes[0]))), new ArrayList<>(), currentScores[0], false));
        }
        players.get(0).setPosition(330, 606, 450, 546);

        // add robots to remaining slots
        for (int i = 0; i < noOfPlayers - 1; i++){
            players.add(new Player(i + 2, robotsNames.get(i), new Image(App.getResourceImg(avatarsDir + robotsList.get(botAvatarIndexes[i + 1]))), new ArrayList<>(), currentScores[i + 1], false));
        }
        switch (noOfPlayers){
            case 2:
                players.get(1).setPosition(980, 49, 684, 14);
                break;
            case 3:
                players.get(1).setPosition(330, 49, 450, 14);
                players.get(2).setPosition(990, 176, 990, 296);
                break;
            case 4:
                players.get(0).setPosition(514, 606, 634, 546);
                players.get(1).setPosition(228, 176, 228, 296);
                players.get(2).setPosition(940, 49, 634, 14);
                players.get(3).setPosition(1166, 176, 990, 296);
                break;
            case 5:
                players.get(0).setPosition(514, 606, 634, 546);
                players.get(1).setPosition(228, 236, 228, 356);
                players.get(2).setPosition(300, 49, 420, 14);
                players.get(3).setPosition(1094, 49, 798, 14);
                players.get(4).setPosition(1166, 236, 990, 356);
                break;
            default:
                break;
        }

        return players;
    }

    void drawAllPlayers(ArrayList<Player> players){
        for (Player playerTemp : players) {
            // avatar's location
            int x = playerTemp.getX();
            int y = playerTemp.getY();

            // set avatar location
            ImageView playerAvatarTemp = new ImageView(playerTemp.getAvatar());
            playerAvatarTemp.setTranslateX(x);
            playerAvatarTemp.setTranslateY(y);

            Tooltip tooltipTemp = new Tooltip("Name: " + playerTemp.getName() + "\nScore: " + playerTemp.getScore());
            tooltipTemp.setShowDelay(Duration.millis(200));
            Tooltip.install(playerAvatarTemp, tooltipTemp);

            // get avatar border
            ImageView border = new ImageView(App.getResourceImg(avatarsDir + avatarBorder));
            border.setTranslateX(x);
            border.setTranslateY(y);

            // create player name tag
            Rectangle nameBg = new Rectangle(x - 5, y - 35, 110, 30);
            nameBg.setFill(Color.WHEAT);
            Text playerNameTemp = new Text(x, y - 22, playerTemp.getName());
            playerNameTemp.setFont(new Font("Verdana", 10));
            playerNameTemp.setWrappingWidth(100);
//            playerNameTemp.setTextAlignment(TextAlignment.JUSTIFY);

            // add avatar and border to pane
            gamePane.getChildren().addAll(playerAvatarTemp, border, nameBg, playerNameTemp);
        }
    }

    // create and shuffle a complete Uno deck, might add condition to reshuffle
    void createUnoDeck(){
        for(LightColor lightColor : LightColor.values()){
            if(lightColor == LightColor.WILD){
                for(int i = 0; i < 4; i++){
                    deck.push(new Card(lightColor, LightValue.WILD, DarkColor.NONE, DarkValue.NONE));
                    deck.push(new Card(lightColor, LightValue.WILD_DRAW_FOUR, DarkColor.NONE, DarkValue.NONE));
                }
            }else{
                for(LightValue lightValue : LightValue.values()){
                    if(lightValue != LightValue.DRAW_ONE && lightValue != LightValue.WILD
                            && lightValue != LightValue.WILD_DRAW_TWO && lightValue != LightValue.WILD_DRAW_FOUR && lightValue != LightValue.FLIP){
                        deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
                        if(lightValue != LightValue.ZERO){
                            deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
                        }
//                        if(lightValue == LightValue.ZERO){
//                            deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
//                            deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
//                            deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
//                            deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
//                            deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
//                            deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
//                            deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
//                            deck.push(new Card(lightColor, lightValue, DarkColor.NONE, DarkValue.NONE));
//                        }
                    }
                }
            }
        }

//        for(LightColor lightColor : LightColor.values()){
//            if(lightColor == LightColor.WILD){
////                for(int i = 0; i < 100; i++){
////                    deck.push(new Card(lightColor, LightValue.WILD_DRAW_FOUR, DarkColor.NONE, DarkValue.NONE));
////                }
//            }else{
//                for(int i = 0; i < 25; i++){
//                    deck.push(new Card(lightColor, LightValue.DRAW_ONE, DarkColor.NONE, DarkValue.NONE));
//                }
//            }
//        }
        Collections.shuffle(deck);
    }

    // create and shuffle a complete Uno Flip deck, might add condition to reshuffle
    void createUnoFlipDeck(){
        ArrayList<AbstractMap.SimpleEntry<LightColor, LightValue>> lightSideList = new ArrayList<>();
        for(LightColor lightColor : LightColor.values()){
            if(lightColor == LightColor.WILD){
                for(int i = 0; i < 4; i++){
                    lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, LightValue.WILD));
                    lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, LightValue.WILD_DRAW_TWO));
                }
            }else{
                for(LightValue lightValue : LightValue.values()){
                    if(lightValue != LightValue.ZERO && lightValue != LightValue.DRAW_TWO && lightValue != LightValue.WILD
                            && lightValue != LightValue.WILD_DRAW_TWO && lightValue != LightValue.WILD_DRAW_FOUR){
                        lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
                        lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
//                        if(lightValue == LightValue.FLIP){
//                            lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
//                            lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
//                            lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
//                            lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
//                            lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
//                            lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
//                            lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
//                            lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue));
//                        }
                    }
                }
            }
        }
        Collections.shuffle(lightSideList);

        ArrayList<AbstractMap.SimpleEntry<DarkColor, DarkValue>> darkSideList = new ArrayList<>();
        for(DarkColor darkColor : DarkColor.values()){
            if(darkColor == DarkColor.DARK_WILD){
                for(int i = 0; i < 4; i++){
                    darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, DarkValue.D_WILD));
                    darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, DarkValue.D_WILD_DRAW));
                }
            }else if(darkColor != DarkColor.NONE){
                for(DarkValue darkValue : DarkValue.values()){
                    if(darkValue != DarkValue.NONE && darkValue != DarkValue.D_WILD && darkValue != DarkValue.D_WILD_DRAW){
                        darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
                        darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
//                        if(darkValue == DarkValue.D_SEVEN){
//                            darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
//                            darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
//                            darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
//                            darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
//                            darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
//                            darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
//                            darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
//                            darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue));
//                        }
                    }
                }
            }
        }
        Collections.shuffle(darkSideList);

        if(lightSideList.size() == darkSideList.size()){
            for(int i = 0; i < lightSideList.size(); i++){
                AbstractMap.SimpleEntry<LightColor, LightValue> lightSideTemp = lightSideList.get(i);
                AbstractMap.SimpleEntry<DarkColor, DarkValue> darkSideTemp = darkSideList.get(i);

                LightColor lightColorTemp = lightSideTemp.getKey();
                LightValue lightValueTemp = lightSideTemp.getValue();
                DarkColor darkColorTemp = darkSideTemp.getKey();
                DarkValue darkValueTemp = darkSideTemp.getValue();

                deck.push(new Card(lightColorTemp, lightValueTemp, darkColorTemp, darkValueTemp));
            }
        }
    }

    // set players in this controller
    void setPlayers(ArrayList<Player> players) {
        this.players = players;
        // set number of players and randomly pick a player to start the game
        this.noOfPlayers = players.size();
        Random random = new Random();
        this.turn = random.nextInt(noOfPlayers);

        setupScoreboard();
    }

    void setupSevenSwapBtn(){
        sevenSwapBtnGroup = new Group();

        for(int i = 0; i < noOfPlayers; i++){
            int playerIndex = i;
            Button sevenSwapBtnTemp = new Button();
            sevenSwapBtnTemp.setPrefSize(60, 60);
            sevenSwapBtnTemp.setTranslateX(sevenSwapBtnLocations[noOfPlayers - 2][i][0]);
            sevenSwapBtnTemp.setTranslateY(sevenSwapBtnLocations[noOfPlayers - 2][i][1]);
            sevenSwapBtnTemp.setRotate(sevenSwapBtnLocations[noOfPlayers - 2][i][2]);
            sevenSwapBtnTemp.setPickOnBounds(false);
            sevenSwapBtnGroup.getChildren().add(sevenSwapBtnTemp);
            sevenSwapBtnTemp.setOnAction(e -> {
                // CHECK IF BUTTON CLICKED IS NOT TO YOURSELF
                if(turn != playerIndex){
                    gamePane.getChildren().remove(sevenSwapBtnGroup);
                    // ASSIGN A NUMBER FOR CHOSEN PLAYER
                    sevenSwapAction(players.get(playerIndex));
                }
            });
        }

//        Button testBtn = new Button();
//        testBtn.setPrefSize(60, 60);
//        testBtn.setTranslateX(717); // DEPENDS
//        testBtn.setTranslateY(240);
//        // ROTATE AS WELL
//        testBtn.setPickOnBounds(false);
//        sevenSwapBtnGroup.getChildren().add(testBtn);
//        testBtn.setOnAction(e -> {
//            // CHECK IF BUTTON CLICKED IS NOT TO YOURSELF
//            gamePane.getChildren().remove(sevenSwapBtnGroup);
//            // ASSIGN A NUMBER FOR CHOSEN PLAYER
////            sevenSwapAction(players.get(index));
//        });
    }

    private void setSevenSwapBtnStyles(){
        for(Node nodeIn : sevenSwapBtnGroup.getChildren()){
            if(nodeIn instanceof Button){
                nodeIn.getStyleClass().clear();
                if(lightSide){
                    switch (discardPile.peek().getLightColor()){
                        case RED:
                            nodeIn.getStyleClass().add("redTriBtn");
                            break;
                        case BLUE:
                            nodeIn.getStyleClass().add("blueTriBtn");
                            break;
                        case GREEN:
                            nodeIn.getStyleClass().add("greenTriBtn");
                            break;
                        case YELLOW:
                            nodeIn.getStyleClass().add("yellowTriBtn");
                            break;
                        default:
                            break;
                    }
                }else{
                    switch (discardPile.peek().getDarkColor()){
                        case GARNET:
                            nodeIn.getStyleClass().add("garnetTriBtn");
                            break;
                        case AMETHYST:
                            nodeIn.getStyleClass().add("amethystTriBtn");
                            break;
                        case EMERALD:
                            nodeIn.getStyleClass().add("emeraldTriBtn");
                            break;
                        case AMBER:
                            nodeIn.getStyleClass().add("amberTriBtn");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void setupScoreboard(){
        String goalString;
        if(Settings.isWinByScore()){
            goalString = "Goal: " + Settings.getScoreLimit() + " point(s)";
        }else{
            goalString = "Goal: " + Settings.getFirstToN() + " win(s)";
        }

        Text goalText = new Text(30, 50, goalString);
        goalText.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        goalText.setStroke(Color.WHITE);
        goalText.setStrokeWidth(2);
        scorePane.getChildren().add(goalText);

        int scoreInfoStartX = 290 - (5 + 115 * noOfPlayers) / 2;
        for (Player playerTemp : players) {
            // avatar's location
            int x = scoreInfoStartX + 10;
            int y = 110;

            // set avatar location
            ImageView playerAvatarTemp = new ImageView(playerTemp.getAvatar());
            playerAvatarTemp.setTranslateX(x);
            playerAvatarTemp.setTranslateY(y);

            // get avatar border
            ImageView border = new ImageView(App.getResourceImg(avatarsDir + avatarBorder));
            border.setTranslateX(x);
            border.setTranslateY(y);

            // create player name tag
            Rectangle nameBg = new Rectangle(x - 5, y - 35, 110, 30);
            nameBg.setFill(Color.WHITE);

            Text playerNameTemp = new Text(x, y - 22, playerTemp.getName());
            playerNameTemp.setFont(new Font("Verdana", 10));
            playerNameTemp.setWrappingWidth(100);
//            playerNameTemp.setTextAlignment(TextAlignment.JUSTIFY);

            // display score, MIGHT DISPLAY MULTIPLE TIMES FOR ANIMATION
            Text score = new Text(x + 7, y + 150, String.valueOf(playerTemp.getScore()));
            score.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
            score.setStroke(Color.WHITE);
            score.setStrokeWidth(2);
            scoresText.add(score);

            // add avatar and border to pane
            scorePane.getChildren().addAll(playerAvatarTemp, border, nameBg, playerNameTemp, score);

            scoreInfoStartX += 115;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Display functions
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // add to history which player started the turn
    private void addGameHistoryWhenStartTurn(){
        gameHistory.appendText(currentPlayer.getName());

        if(currentPlayer.isYou()){
            gameHistory.appendText("(you)");
        }

        gameHistory.appendText(" started the turn\n");
    }

    // add to history which player played which card
    private void addGameHistoryWhenPlayCard(Card cardPlayed){
//        gameHistory.appendText(player.getName());
//        if(player.isYou()){
//            gameHistory.appendText("(you)");
//        }

        if(lightSide){
            gameHistory.appendText("Played " + cardPlayed.getLightColor().getCustomString() +
                    " " + cardPlayed.getLightValue().getCustomString());
        }else{
            gameHistory.appendText("Played " + cardPlayed.getDarkColor().getCustomString() +
                    " " + cardPlayed.getDarkValue().getCustomString());
        }
        gameHistory.appendText(".\n");
    }

    private void addGameHistoryWhenSevenSwap(Player playerToSwap){
        gameHistory.appendText(currentPlayer.getName() + " swapped cards with " + playerToSwap.getName() + ".\n");
    }

    private void addGameHistoryWhenZeroRotate(){
        gameHistory.appendText("All hands were rotated ");
        if(clockwise){
            gameHistory.appendText("Clockwise.\n");
        }else{
            gameHistory.appendText("Counter-Clockwise.\n");
        }
    }

    private void addGameHistoryAfterFlip(Card topCard){
        gameHistory.appendText("Card on the top is now ");
        if(lightSide){
            gameHistory.appendText(topCard.getLightColor().getCustomString() +
                    " " + topCard.getLightValue().getCustomString());
        }else{
            gameHistory.appendText(topCard.getDarkColor().getCustomString() +
                    " " + topCard.getDarkValue().getCustomString());
        }
        gameHistory.appendText(".\n");
    }

    private void addGameHistoryWhenReverse(){
        gameHistory.appendText("Changed direction to ");
        if(clockwise){
            gameHistory.appendText("Clockwise.\n");
        }else{
            gameHistory.appendText("Counter-Clockwise.\n");
        }
    }

    // add to history which player changed to which color
    void addGameHistoryWhenChangeColor(String colorStr){
//        gameHistory.appendText(currentPlayer.getName());
//
//        if(currentPlayer.isYou()){
//            gameHistory.appendText("(you)");
//        }
        gameHistory.appendText("Changed color to ");

        gameHistory.appendText(colorStr + ".\n");
    }

    // add to history which player drew a card
    private void addGameHistoryWhenDrawCard(){
//        gameHistory.appendText(player.getName());
//
//        if(player.isYou()){
//            gameHistory.appendText("(you)");
//        }

        gameHistory.appendText("Drew a card.\n");
    }

    void addGameHistoryWhenCantPlay(){
        gameHistory.appendText("Didn't play a card this turn.\n");
    }

    private void addGameHistoryWhenGetSkipped(){
        gameHistory.appendText("Got skipped this turn.\n");
    }

    // add to history which player ended the turn
    private void addGameHistoryWhenEndTurn(){
        gameHistory.appendText(currentPlayer.getName());

        if(currentPlayer.isYou()){
            gameHistory.appendText("(you)");
        }

        gameHistory.appendText(" ended the turn\n\n");
    }

    // remove previous image of the glow then redraw it on the correct avatar
    void drawAvatarGlow(){
        gamePane.getChildren().remove(avatarGlowIV);

        int x = players.get(turn).getX();
        int y = players.get(turn).getY();
        avatarGlowIV.setTranslateX(x - glowOffset);
        avatarGlowIV.setTranslateY(y - glowOffset);
        gamePane.getChildren().add(avatarGlowIV);
    }

    // set the correct direction gif then add to pane
    void drawDirection(){
        setCorrectGif();
        gamePane.getChildren().add(directionIV);
    }

    // draw bots/opponents hand and decide if it's faced up or not
    private void drawBotsHand(Player bot, boolean showHand){
        // get hand and its starting position
        int handX = bot.getHandX();
        int handY = bot.getHandY();
        ArrayList<Card> botsHand = bot.getHand();
        // clear old and draw new for each card in bot's hand
        for(int i = 0; i < botsHand.size(); i++){
            Card cardTemp = botsHand.get(i);
            removeCardFromGamePane(cardTemp);

            int x = handX + i % cardsPerRow * cardGap;
            int y = handY + cardGap * (i / cardsPerRow);

            drawCard(cardTemp, x, y, showHand);
        }
    }

    // draw your hand base on the side you want to view
    void drawMyHand(){
        // get my hand and its starting position
        ArrayList<Card> myHand = you.getHand();
        int handX = you.getHandX();
        int handY = you.getHandY();
        // clear old and draw new for each card in my hand
        for(int i = 0; i < myHand.size(); i++){
            Card cardTemp = myHand.get(i);
            removeCardFromGamePane(cardTemp);

            int x = handX + i % cardsPerRow * cardGap;
            int y = handY + cardGap * (i / cardsPerRow);

            drawCard(cardTemp, x, y, viewingFront);
            // add moving animation and handle card click
            addUpDownAnimation(cardTemp, x, y);
            addCardPlayEvent(cardTemp, x, y);
        }
    }

    // MIGHT BE USED IN GENERAL
    private void drawAHand(Player player, boolean showHandForBot){
        // get hand and its starting position
        int handX = player.getHandX();
        int handY = player.getHandY();
        ArrayList<Card> playerHand = player.getHand();
        // clear old and draw new for each card in bot's hand
        for(int i = 0; i < playerHand.size(); i++){
            Card cardTemp = playerHand.get(i);
            removeCardFromGamePane(cardTemp);

            int x = handX + i % cardsPerRow * cardGap;
            int y = handY + cardGap * (i / cardsPerRow);

            if(player.isYou()){
                drawCard(cardTemp, x, y, viewingFront);
                // add moving animation and handle card click
                addUpDownAnimation(cardTemp, x, y);
                addCardPlayEvent(cardTemp, x, y);
            }else{
                drawCard(cardTemp, x, y, showHandForBot);
            }
        }
    }

    // redraw hands without setting you as a player like when the scene is initialized
    // called when a (dark) flip card is played
    private void redrawPlayersHand(){
        for(Player player : players){
            if(player.isYou()){
                drawMyHand();
            }else{
                // shuffle hand if it's from a bot
                Collections.shuffle(player.getHand());
                drawBotsHand(player, false);
            }
        }
    }

    // draw the top card of the discard pile and
    // remove previous card's element from pane if possible
    private void drawDiscardPile(){
        int size = discardPile.size();
        // discard pile has cards
        if(size > 0){
            // discard pile has more than 1 card
            if(size > 1){
                // remove previous card
                removeCardFromGamePane(discardPile.get(size - 2));
            }
            // draw top card
            drawCard(discardPile.peek(), discardX, discardY, true);
        }
    }

    // set light color of top card in the discard pile, then redraw discard pile
    void changeLightColorOfTopCard(LightColor lightColor){
        discardPile.peek().setLightColor(lightColor);
        removeCardFromGamePane(discardPile.peek());
        drawDiscardPile();
    }

    // set light color of top card in the discard pile, then redraw discard pile
    void changeDarkColorOfTopCard(DarkColor darkColor){
        discardPile.peek().setDarkColor(darkColor);
        removeCardFromGamePane(discardPile.peek());
        drawDiscardPile();
    }

    // draw the top card of the deck facing "down"
    private void drawDeck(){
        // refill deck if empty
        if(deck.empty()){
            refillDeck();
        }

        if(!deck.empty()){
            // when deck isn't empty, remove and redraw card on top,
            // facing "down"
            Card topCardOfDeck = deck.peek();
            removeCardFromGamePane(topCardOfDeck);
            drawCard(topCardOfDeck, deckX, deckY, false);
            // handle click to draw
            addDeckDrawEvent();
        }
    }

    // refill deck with played cards when the deck is empty
    private void refillDeck(){
        // keep top card to push at the end
        Card topCardOfDiscard = discardPile.pop();

        while(!discardPile.empty()){
            // move every card from discard pile to deck
            deck.push(discardPile.pop());
            // check the moved card and reset color if it's a wild or dark wild on either side
            Card cardTemp = deck.peek();
            if((cardTemp.getLightValue() ==  LightValue.WILD) ||
                    (cardTemp.getLightValue() ==  LightValue.WILD_DRAW_TWO) ||
                    (cardTemp.getLightValue() ==  LightValue.WILD_DRAW_FOUR)){
                cardTemp.setLightColor(LightColor.WILD);
            }
            if((cardTemp.getDarkValue() ==  DarkValue.D_WILD) ||
                    (cardTemp.getDarkValue() ==  DarkValue.D_WILD_DRAW)){
                cardTemp.setDarkColor(DarkColor.DARK_WILD);
            }
        }
        // shuffle the deck after refilling
        Collections.shuffle(deck);
        // push the top card back to discard pile
        discardPile.push(topCardOfDiscard);

        gameHistory.appendText("DECK EMPTY, REFILLED DECK\n");
    }

    // draw the card with correct elements, position and orientation
    private void drawCard(Card card, int x, int y, boolean faceUp){
        // load correct images
        ImageView cardColor;
        ImageView cardValue;
        if((lightSide && faceUp) || (!lightSide && !faceUp)){
            // draw light color + value
            switch (card.getLightColor()){
                case RED:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorRed));
                    break;
                case BLUE:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorBlue));
                    break;
                case GREEN:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorGreen));
                    break;
                case YELLOW:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorYellow));
                    break;
                case WILD:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorWild));
                    break;
                default:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorNone));
                    System.out.println("Light color hello?");
                    break;
            }

            switch (card.getLightValue()){
                case ZERO:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value0));
                    break;
                case ONE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value1));
                    break;
                case TWO:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value2));
                    break;
                case THREE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value3));
                    break;
                case FOUR:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value4));
                    break;
                case FIVE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value5));
                    break;
                case SIX:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value6));
                    break;
                case SEVEN:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value7));
                    break;
                case EIGHT:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value8));
                    break;
                case NINE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + value9));
                    break;
                case SKIP:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueSkip));
                    break;
                case REVERSE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueReverse));
                    break;
                case DRAW_ONE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueDraw1));
                    break;
                case DRAW_TWO:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueDraw2));
                    break;
                case WILD:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueWild));
                    break;
                case WILD_DRAW_TWO:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueWildDraw2));
                    break;
                case WILD_DRAW_FOUR:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueWildDraw4));
                    break;
                case FLIP:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueFlip));
                    break;
                default:
                    cardValue = new ImageView();
                    System.out.println("Light value hello?");
                    break;
            }
        } else {
            // draw dark color + value
            switch (card.getDarkColor()){
                case GARNET:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorGarnet));
                    break;
                case AMETHYST:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorAmethyst));
                    break;
                case EMERALD:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorEmerald));
                    break;
                case AMBER:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorAmber));
                    break;
                case DARK_WILD:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorDarkWild));
                    break;
                case NONE:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorNone));
                    break;
                default:
                    cardColor = new ImageView(App.getResourceImg(cardsDir + colorNone));
                    System.out.println("Dark color hello?");
                    break;
            }

            switch (card.getDarkValue()){
                case D_ONE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueD1));
                    break;
                case D_TWO:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueD2));
                    break;
                case D_THREE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueD3));
                    break;
                case D_FOUR:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueD4));
                    break;
                case D_FIVE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueD5));
                    break;
                case D_SIX:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueD6));
                    break;
                case D_SEVEN:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueD7));
                    break;
                case D_EIGHT:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueD8));
                    break;
                case D_NINE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueD9));
                    break;
                case SKIP_ALL:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueSkipAll));
                    break;
                case D_REVERSE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueDReverse));
                    break;
                case DRAW_FIVE:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueDraw5));
                    break;
                case D_WILD:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueDWild));
                    break;
                case D_WILD_DRAW:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueDWildDraw));
                    break;
                case D_FLIP:
                    cardValue = new ImageView(App.getResourceImg(cardsDir + valueDFlip));
                    break;
                case NONE:
                    cardValue = new ImageView();
                    break;
                default:
                    cardValue = new ImageView();
                    System.out.println("Dark value hello?");
                    break;
            }
        }
        // attach images to card object and move accordingly
        Group visibleCard = card.getVisibleCard();
        visibleCard.getChildren().addAll(cardColor, cardValue);
        visibleCard.setTranslateX(x);
        visibleCard.setTranslateY(y);

        // add visible elements to pane
        gamePane.getChildren().add(visibleCard);
        // add invisible element on top for further usage
        addInvisibleCard(card, x, y);
    }

    // add invisible rectangle, always on top of visible cards
    private void addInvisibleCard(Card card, int x, int y){
        Rectangle invisibleCard = new Rectangle(0, 0, cardWidth, cardHeight);
        invisibleCard.setFill(Color.TRANSPARENT);
        invisibleCard.setTranslateX(x);
        invisibleCard.setTranslateY(y);
        card.setInvisibleCard(invisibleCard);

        gamePane.getChildren().add(card.getInvisibleCard());
    }

    // add move up/down animation of a card
    private void addUpDownAnimation(Card card, int x, int y){
        // define transitions
        PathTransition moveUp = new PathTransition();
        moveUp.setDuration(Duration.millis(200));
        moveUp.setPath(new Line(x + cardWidth/2.0, y + cardHeight/2.0, x + cardWidth/2.0, y - cardHeight/2.0));
        moveUp.setNode(card.getVisibleCard());

        PathTransition moveDown = new PathTransition();
        moveDown.setDuration(Duration.millis(200));
        moveDown.setPath(new Line(x + cardWidth/2.0, y - cardHeight/2.0, x + cardWidth/2.0, y + cardHeight/2.0));
        moveDown.setNode(card.getVisibleCard());

        // handle move up when mouse enters the invisible element
        card.getInvisibleCard().setOnMouseEntered(e -> {
            // increase the element's size for better movement
            growInvisibleCard(card);
            moveUp.play();
        });
        // handle move down when mouse exits the invisible element
        card.getInvisibleCard().setOnMouseExited(e -> {
            // decrease the element's size to normal card's size
            shrinkInvisibleCard(card);
            moveUp.stop();
            moveDown.play();
        });
    }

    // double height of invisible element
    private void growInvisibleCard(Card card){
        Rectangle invisibleCard = card.getInvisibleCard();
        invisibleCard.setY(-cardHeight);
        invisibleCard.setHeight(cardHeight * 2);
    }

    // invisible element's size = normal card's size
    private void shrinkInvisibleCard(Card card){
        Rectangle invisibleCard = card.getInvisibleCard();
        invisibleCard.setY(0);
        invisibleCard.setHeight(cardHeight);
    }

    // clear visible card group and remove all elements of that card from pane
    private void removeCardFromGamePane(Card card){
        card.getVisibleCard().getChildren().clear();
        gamePane.getChildren().remove(card.getVisibleCard());
        gamePane.getChildren().remove(card.getInvisibleCard());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    void setupBeforeDealingCards(){
        // draw the deck first
        drawDeck();
        // start dealing cards form the chosen person, going clockwise
        dealPlayerIndex = turn;
        dealRound = 1;
        dealACardToAPlayerAction();
        gameHistory.appendText("Start dealing cards...\n");
    }

    private void dealACardToAPlayerAction(){
        ArrayList<Card> playerHand = players.get(dealPlayerIndex).getHand();

        KeyFrame dealFrame = new KeyFrame(Duration.millis(150),
                e -> {
                    Card topCardOfDeck = deck.peek();
                    // remove card's elements shown in pane
                    removeCardFromGamePane(topCardOfDeck);
                    // take the card on top of the deck to bot's hand
                    playerHand.add(deck.pop());

                    // redraw the deck
                    drawDeck();

                    dealCardAnimation(topCardOfDeck, dealPlayerIndex, dealRound);

                    dealPlayerIndex++;
                    if(dealPlayerIndex >= noOfPlayers){
                        dealPlayerIndex = 0;
                    }
                    if(dealPlayerIndex == turn){
                        dealRound++;
                    }
                    if(dealRound > Settings.getCardsPerPlayer()){
                        placeFirstCardFromDeckAction();
                    }else{
                        dealACardToAPlayerAction();
                    }
                });

        Timeline dealTimeline = new Timeline(dealFrame);
        dealTimeline.play();
    }

    private void placeFirstCardFromDeckAction(){
        KeyFrame placeFrame = new KeyFrame(Duration.millis(500),
                e -> {
                    Card topCardOfDeck = deck.peek();
                    // remove card's elements shown in pane
                    removeCardFromGamePane(topCardOfDeck);
                    // take the card on top of the deck to bot's hand
                    discardPile.push(deck.pop());

                    // redraw the deck
                    drawDeck();

                    placeFirstCardAnimation(topCardOfDeck);
                });

        Timeline placeTimeline = new Timeline(placeFrame);
        placeTimeline.play();
    }

    private void checkFirstCardThenStartGame(){
        Card firstCard = discardPile.peek();
        gameHistory.appendText("First card is ");
        if(lightSide){
            gameHistory.appendText(firstCard.getLightColor().getCustomString() +
                    " " + firstCard.getLightValue().getCustomString());
        }else{
            gameHistory.appendText(firstCard.getDarkColor().getCustomString() +
                    " " + firstCard.getDarkValue().getCustomString());
        }
        gameHistory.appendText("\n");

        // DO THE CHECKING ...
        setSmallEffects(firstCard);

        currentPlayer = players.get(turn); // MIGHT PUT THIS ABOVE CHECKING FOR 7-0 ???
        endTurnAction();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ANIMATIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void dealCardAnimation(Card card, int currentDealPlayerIndex, int currentDealRound){
        // draw card that was from the top of the deck,
        // mostly from the drawn card so it acts as a temporary animation
        drawCard(card, deckX, deckY, false);
        Group visibleCardTemp = card.getVisibleCard();
        Rectangle invisibleCardTemp = card.getInvisibleCard();

        // calculate destination base on the size of player's hand
        Player playerToDeal = players.get(currentDealPlayerIndex);
        int playerHandSizeBefore = currentDealRound - 1;
        int endX = playerToDeal.getHandX() + playerHandSizeBefore % cardsPerRow * cardGap;
        int endY = playerToDeal.getHandY() + cardGap * (playerHandSizeBefore / cardsPerRow);

        // destination of the card
        KeyValue endValue1 = new KeyValue(visibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
        KeyValue endValue2 = new KeyValue(visibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);
        KeyValue endValue3 = new KeyValue(invisibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
        KeyValue endValue4 = new KeyValue(invisibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);

        // animation lasts 200ms
        KeyFrame endFrame1 = new KeyFrame(Duration.millis(150), endValue1, endValue2, endValue3, endValue4);
        // handle event after animation finished
        KeyFrame endFrame2 = new KeyFrame(Duration.millis(150),
                e -> {
                    // remove that temporary card from pane
                    removeCardFromGamePane(card);

                    if(playerToDeal.isYou()){
                        // show new card in your hand
                        drawCard(card, endX, endY, true);
                        // add moving animation and handle card click for the new card
                        addUpDownAnimation(card, endX, endY);
                        addCardPlayEvent(card, endX, endY);
                    }else{
                        // show new card in bot's hand
                        drawCard(card, endX, endY, false);
                    }
                });
        Timeline dealTimeline = new Timeline(endFrame1, endFrame2);
        // play animation
        dealTimeline.play();
    }

    private void placeFirstCardAnimation(Card card){
        // draw card that was from the top of the deck,
        // mostly from the drawn card so it acts as a temporary animation
        drawCard(card, deckX, deckY, false);
        Group visibleCardTemp = card.getVisibleCard();
        Rectangle invisibleCardTemp = card.getInvisibleCard();

        // destination of the card
        KeyValue endValue1 = new KeyValue(visibleCardTemp.translateXProperty(), discardX, Interpolator.EASE_BOTH);
        KeyValue endValue2 = new KeyValue(visibleCardTemp.translateYProperty(), discardY, Interpolator.EASE_BOTH);
        KeyValue endValue3 = new KeyValue(invisibleCardTemp.translateXProperty(), discardX, Interpolator.EASE_BOTH);
        KeyValue endValue4 = new KeyValue(invisibleCardTemp.translateYProperty(), discardY, Interpolator.EASE_BOTH);

        // animation lasts 200ms
        KeyFrame endFrame1 = new KeyFrame(Duration.millis(200), endValue1, endValue2, endValue3, endValue4);
        // handle event after animation finished
        KeyFrame endFrame2 = new KeyFrame(Duration.millis(200),
                e -> {
                    // remove that temporary card from pane
                    removeCardFromGamePane(card);

                    drawDiscardPile();

                    checkFirstCardThenStartGame();
                });
        Timeline placeTimeline = new Timeline(endFrame1, endFrame2);
        // play animation
        placeTimeline.play();
    }

    // animation where the card comes from the player's hand to the discard pile
    private void playedCardAnimation(Card card, int x, int y){
        // draw card in moved-up position, mostly from the removed card so it acts as a temporary animation
        drawCard(card, x, y, true);
        Group visibleCardTemp = card.getVisibleCard();
        Rectangle invisibleCardTemp = card.getInvisibleCard();

        // destination of the card (discard pile)
        KeyValue endValue1 = new KeyValue(visibleCardTemp.translateXProperty(), discardX, Interpolator.EASE_BOTH);
        KeyValue endValue2 = new KeyValue(visibleCardTemp.translateYProperty(), discardY, Interpolator.EASE_BOTH);
        KeyValue endValue3 = new KeyValue(invisibleCardTemp.translateXProperty(), discardX, Interpolator.EASE_BOTH);
        KeyValue endValue4 = new KeyValue(invisibleCardTemp.translateYProperty(), discardY, Interpolator.EASE_BOTH);

        // animation lasts 250ms
        KeyFrame endFrame1 = new KeyFrame(Duration.millis(250), endValue1, endValue2, endValue3, endValue4);
        // handle event after animation finished
        KeyFrame endFrame2 = new KeyFrame(Duration.millis(250),
                e -> {
                    // remove that temporary card from pane
                    removeCardFromGamePane(card);

                    if(currentPlayer.isYou()){
                        // "false" means the card is no longer being played and you can now play or draw a new card
                        lockingUserAction = false;

                        // MAY ENABLE SOME BUTTONS LATER
                        sortHandBtn.setDisable(false);
                        viewOtherSideBtn.setDisable(false);
//                    endTurnBtn.setDisable(false);
                    }

                    // draw the top card in discard pile,
                    // which is the card used for animation just before
                    drawDiscardPile();

                    // CONNECTING CLASSIC RULE VS MULTI-CARDS RULE FOR HUMAN
                    // OVERALL END TURN ACTION ???

                    Card playedCard = discardPile.peek();
                    // if a flip card is played
                    if((lightSide && playedCard.getLightValue() == LightValue.FLIP) ||
                            (!lightSide && playedCard.getDarkValue() == DarkValue.D_FLIP)){
                        // switch side (light <=> dark)
                        switchSide();

                        // draw players' hand again in new orientation
                        // and shuffle all bots' hands
                        redrawPlayersHand();

                        // remove card image before flipping to avoid duplicate children,
                        // then reverse the pile and redraw it
                        removeCardFromGamePane(playedCard);
                        reverseCardPile(discardPile);
                        drawDiscardPile();

                        removeCardFromGamePane(deck.peek());
                        reverseCardPile(deck);
                        drawDeck();

                        // keep sort method after flipping
                        switchSortMethod = false;

                        Card topCard = discardPile.peek();
                        // TOP CARD IS NOW <...>
                        addGameHistoryAfterFlip(topCard);
                        // DO THE CHECKING ...
                        setSmallEffects(topCard);

                        if(currentPlayer.isYou()){
                            lockingUserAction = true;
                            endTurnAction();
//                            goToNextTurn();
//                            endTurnBtn.setDisable(false);
                        }else{
                            endTurnAction();
                        }
                    }else if(lightSide && playedCard.getLightColor() == LightColor.WILD){
                        // if a (dark) wild card is played, show the correct group of buttons, ???????
                        if(currentPlayer.isYou()){
                            gamePane.getChildren().add(lightBtnGroup);
                            endTurnBtn.setDisable(true);
                        }else{
                            botChangeColorAction();
                        }
                        if(playedCard.getLightValue() == LightValue.WILD_DRAW_TWO){
                            if(!goingToApplyPunishment){
                                setPunishments(true, 1, 2);
                            }else{
                                stackCardsToDraw(2);
                            }
                        }
                        if(playedCard.getLightValue() == LightValue.WILD_DRAW_FOUR){
                            if(!goingToApplyPunishment){
                                setPunishments(true, 1, 4);
                            }else{
                                stackCardsToDraw(4);
                            }
                        }
                    }else if(!lightSide && playedCard.getDarkColor() == DarkColor.DARK_WILD){
                        if(currentPlayer.isYou()){
                            gamePane.getChildren().add(darkBtnGroup);
                            endTurnBtn.setDisable(true);
                        }else{
                            botChangeColorAction();
                        }
                        if(playedCard.getDarkValue() == DarkValue.D_WILD_DRAW){
                            if(!goingToApplyPunishment){
                                setPunishments(true, 1, -1); // -1: draw to match color
                                drawToMatchColorRepeatTimes = 1;
                            }else{
                                drawToMatchColorRepeatTimes++;
                            }
                        }
                    }else if(Settings.isSevenSwap() &&
                            ((lightSide && playedCard.getLightValue() == LightValue.SEVEN) ||
                            (!lightSide && playedCard.getDarkValue() == DarkValue.D_SEVEN))){
                        if(!gameEnd){
                            if(currentPlayer.isYou()){
                                setSevenSwapBtnStyles();
                                gamePane.getChildren().add(sevenSwapBtnGroup);
                                lockingUserAction = true;
                                endTurnBtn.setDisable(true);
                            }else{
                                // MIGHT ADD DELAY TO SHOW WHICH PLAYER THE BOT CHOOSES BEFORE THE ANIMATION
                                botChoosePlayerToSwap();
                            }
                        }else{
                            endTurnAction();
                        }
                    }else if(Settings.isZeroRotate() && lightSide && playedCard.getLightValue() == LightValue.ZERO){
                        if(!gameEnd){
                            lockingUserAction = true;
                            endTurnBtn.setDisable(true);
                            // that action
                            zeroRotateAction();
                        }else{
                            endTurnAction();
                        }
                    }else{
//                        if(lightSide && playedCard.getLightValue() == LightValue.DRAW_ONE){
//                            setPunishments(true, 1, 1);
//                        }else if(lightSide && playedCard.getLightValue() == LightValue.DRAW_TWO){
//                            setPunishments(true, 1, 2);
//                        }else if(!lightSide && playedCard.getDarkValue() == DarkValue.DRAW_FIVE){
//                            setPunishments(true, 1, 5);
//                        }else if(lightSide && playedCard.getLightValue() == LightValue.SKIP){
//                            setPunishments(true, 1, 0);
//                        }else if(!lightSide && playedCard.getDarkValue() == DarkValue.SKIP_ALL){
//                            setPunishments(true, noOfPlayers - 1, 0);
//                        }else if((lightSide && playedCard.getLightValue() == LightValue.REVERSE) ||
//                                    (!lightSide && playedCard.getDarkValue() == DarkValue.D_REVERSE)){
//                            switchDirection();
//                        }
                        setSmallEffects(playedCard);
                        if(!Settings.isMultipleCards()){
                            lockingUserAction = true;
                            endTurnAction();
                        }else{
                            if(!gameEnd){
                                allowEndTurn();
                            }else{
                                endTurnAction();
                            }
                        }

                    }
                });
        Timeline playedCardTimeline = new Timeline(endFrame1, endFrame2);
        // play animation
        playedCardTimeline.play();
    }

    private void setSmallEffects(Card card){
        if(lightSide && card.getLightValue() == LightValue.DRAW_ONE){
            if(!goingToApplyPunishment){
                setPunishments(true, 1, 1);
            }else{
                stackCardsToDraw(1);
            }
        }else if(lightSide && card.getLightValue() == LightValue.DRAW_TWO){
            if(!goingToApplyPunishment){
                setPunishments(true, 1, 2);
            }else{
                stackCardsToDraw(2);
            }
        }else if(!lightSide && card.getDarkValue() == DarkValue.DRAW_FIVE){
            if(!goingToApplyPunishment){
                setPunishments(true, 1, 5);
            }else{
                stackCardsToDraw(5);
            }
        }else if(lightSide && card.getLightValue() == LightValue.SKIP){
            if(!goingToApplyPunishment){
                setPunishments(true, 1, 0);
            }else{
                oneMorePlayerToSkip();
            }
        }else if(!lightSide && card.getDarkValue() == DarkValue.SKIP_ALL){
            setPunishments(true, noOfPlayers - 1, 0);
        }else if((lightSide && card.getLightValue() == LightValue.REVERSE) ||
                (!lightSide && card.getDarkValue() == DarkValue.D_REVERSE)){
            switchDirection();
        }
    }

    // called when a card is played and MULTI-CARD mode is on
    private void allowEndTurn(){
        if(currentPlayer.isYou()){
            endTurnBtn.setDisable(false);
        }else{
            // CHECK IF BOTS CAN PLAY ANOTHER CARD
//            endTurnAction(); // ADD MORE CONDITIONS LATER FOR MULTIPLE CARDS MODE ???
            botKeepsPlayingOrEndTurn();
        }
    }

    // animation where the card comes from the deck to the player's hand
    private void drawFromDeckAnimation(Card card){
        // draw card that was from the top of the deck,
        // mostly from the drawn card so it acts as a temporary animation
        drawCard(card, deckX, deckY, false);
        Group visibleCardTemp = card.getVisibleCard();
        Rectangle invisibleCardTemp = card.getInvisibleCard();

        // calculate destination base on the size of your hand
//        int myHandSizeBefore = you.getHand().size() - 1;
//        int endX = you.getHandX() + myHandSizeBefore % cardsPerRow * cardGap;
//        int endY = you.getHandY() + cardGap * (myHandSizeBefore / cardsPerRow);

        // calculate destination base on the size of player's hand
        int playerHandSizeBefore = currentPlayer.getHand().size() - 1;
        int endX = currentPlayer.getHandX() + playerHandSizeBefore % cardsPerRow * cardGap;
        int endY = currentPlayer.getHandY() + cardGap * (playerHandSizeBefore / cardsPerRow);

        // destination of the card
        KeyValue endValue1 = new KeyValue(visibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
        KeyValue endValue2 = new KeyValue(visibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);
        KeyValue endValue3 = new KeyValue(invisibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
        KeyValue endValue4 = new KeyValue(invisibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);

        // animation lasts 250ms
        KeyFrame endFrame1 = new KeyFrame(Duration.millis(250), endValue1, endValue2, endValue3, endValue4);
        // handle event after animation finished
        KeyFrame endFrame2 = new KeyFrame(Duration.millis(250),
                e -> {
                    // remove that temporary card from pane
                    removeCardFromGamePane(card);

                    if(currentPlayer.isYou()){
                        // show new card in your hand
                        drawCard(card, endX, endY, true);
                        // add moving animation and handle card click for the new card
                        addUpDownAnimation(card, endX, endY);
                        addCardPlayEvent(card, endX, endY);

                        // keep the sort method after drawing
                        switchSortMethod = false;

                        // CHECK IF DRAWN CARD CAN BE PLAYED
                        // YES: ALLOW YOU TO PLAY OR END TURN
                        if(checkIfDrawnCardCanBePlayed(card)){
                            if(!Settings.isForcePlay()){
                                lockingUserAction = false;

                                // MAY ENABLE SOME BUTTONS LATER
                                sortHandBtn.setDisable(false);
                                viewOtherSideBtn.setDisable(false);
                                endTurnBtn.setDisable(false);
                            }else{
                                // AUTOMATICALLY PLAY THAT CARD
                                youForcedPlayDrawnCardAction(card);
                            }
                        }else{ // NO:
                            if(!Settings.isDrawToMatch()){
                                // ONLY ALLOW END TURN
                                // MAY ENABLE SOME BUTTONS LATER
                                sortHandBtn.setDisable(false);
                                viewOtherSideBtn.setDisable(false);
                                endTurnBtn.setDisable(false);
                            }else{
                                // FORCE DRAW ANOTHER CARD
                                // ADD LATER
                                youDrawToMatchAction();
                            }
                        }

                    }else{
                        // redraw bot's hand
//                        drawBotsHand(currentPlayer, false);
                        drawCard(card, endX, endY, false);
                    }


                });
        Timeline drawFromDeckTimeline = new Timeline(endFrame1, endFrame2);
        // play animation
        drawFromDeckTimeline.play();
    }

    private boolean checkIfDrawnCardCanBePlayed(Card card){
        if(lightSide){
            // same light color, value or a wild card (if the card matches the top card)
            return (card.getLightColor() == discardPile.peek().getLightColor()) ||
                    (card.getLightValue() == discardPile.peek().getLightValue()) ||
                    (card.getLightColor() == LightColor.WILD);
        }else{
            // same dark color, value or a dark wild card (if the card matches the top card)
            return (card.getDarkColor() == discardPile.peek().getDarkColor()) ||
                    (card.getDarkValue() == discardPile.peek().getDarkValue()) ||
                    (card.getDarkColor() == DarkColor.DARK_WILD);
        }
    }

    private void forcedDrawFromDeckAnimation(Card card){
        // draw card that was from the top of the deck,
        // mostly from the drawn card so it acts as a temporary animation
        drawCard(card, deckX, deckY, false);
        Group visibleCardTemp = card.getVisibleCard();
        Rectangle invisibleCardTemp = card.getInvisibleCard();

        // calculate destination base on the size of player's hand
        int playerHandSizeBefore = currentPlayer.getHand().size() - 1;
        int endX = currentPlayer.getHandX() + playerHandSizeBefore % cardsPerRow * cardGap;
        int endY = currentPlayer.getHandY() + cardGap * (playerHandSizeBefore / cardsPerRow);

        // destination of the card
        KeyValue endValue1 = new KeyValue(visibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
        KeyValue endValue2 = new KeyValue(visibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);
        KeyValue endValue3 = new KeyValue(invisibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
        KeyValue endValue4 = new KeyValue(invisibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);

        // animation lasts 250ms
        KeyFrame endFrame1 = new KeyFrame(Duration.millis(250), endValue1, endValue2, endValue3, endValue4);
        // handle event after animation finished
        KeyFrame endFrame2 = new KeyFrame(Duration.millis(250),
                e -> {
                    // remove that temporary card from pane
                    removeCardFromGamePane(card);

                    if(currentPlayer.isYou()){
                        // show new card in your hand
                        drawCard(card, endX, endY, true);
                        // add moving animation and handle card click for the new card
                        addUpDownAnimation(card, endX, endY);
                        addCardPlayEvent(card, endX, endY);

                        if(!goingToApplyPunishment){
                            sortHandBtn.setDisable(false);
                            viewOtherSideBtn.setDisable(false);
                        }

                        // keep the sort method after drawing
                        switchSortMethod = false; // MIGHT PUT AT THE END OF FORCED DRAWING STATE
                    }else{
                        // redraw bot's hand
//                        drawBotsHand(currentPlayer, false);
                        drawCard(card, endX, endY, false);
                    }
                });
        Timeline drawFromDeckTimeline = new Timeline(endFrame1, endFrame2);
        // play animation
        drawFromDeckTimeline.play();
    }

    private void sevenSwapAnimation(Card lastCardOfCurrentHand, int currentX, int currentY, Card lastCardOfHandToSwap, int toSwapX, int toSwapY, Player playerToSwap){
        drawCard(lastCardOfCurrentHand, currentX, currentY, false);
        Group visibleCardTempCurrent = lastCardOfCurrentHand.getVisibleCard();
        Rectangle invisibleCardTempCurrent = lastCardOfCurrentHand.getInvisibleCard();

        drawCard(lastCardOfHandToSwap, toSwapX, toSwapY, false);
        Group visibleCardTempToSwap = lastCardOfHandToSwap.getVisibleCard();
        Rectangle invisibleCardTempToSwap = lastCardOfHandToSwap.getInvisibleCard();

        // destination of the card
        KeyValue endValue1 = new KeyValue(visibleCardTempCurrent.translateXProperty(), toSwapX, Interpolator.EASE_BOTH);
        KeyValue endValue2 = new KeyValue(visibleCardTempCurrent.translateYProperty(), toSwapY, Interpolator.EASE_BOTH);
        KeyValue endValue3 = new KeyValue(invisibleCardTempCurrent.translateXProperty(), toSwapX, Interpolator.EASE_BOTH);
        KeyValue endValue4 = new KeyValue(invisibleCardTempCurrent.translateYProperty(), toSwapY, Interpolator.EASE_BOTH);

        KeyValue endValue5 = new KeyValue(visibleCardTempToSwap.translateXProperty(), currentX, Interpolator.EASE_BOTH);
        KeyValue endValue6 = new KeyValue(visibleCardTempToSwap.translateYProperty(), currentY, Interpolator.EASE_BOTH);
        KeyValue endValue7 = new KeyValue(invisibleCardTempToSwap.translateXProperty(), currentX, Interpolator.EASE_BOTH);
        KeyValue endValue8 = new KeyValue(invisibleCardTempToSwap.translateYProperty(), currentY, Interpolator.EASE_BOTH);

        // animation lasts 200ms
        KeyFrame endFrame1 = new KeyFrame(Duration.millis(500), endValue1, endValue2, endValue3, endValue4, endValue5, endValue6, endValue7, endValue8);
        // handle event after animation finished
        KeyFrame endFrame2 = new KeyFrame(Duration.millis(500),
                e -> {
                    // remove that temporary card from pane
                    removeCardFromGamePane(lastCardOfCurrentHand);
                    removeCardFromGamePane(lastCardOfHandToSwap);

                    // draw what now???
                    drawAHand(currentPlayer, false);
                    drawAHand(playerToSwap, false);

//                    if(currentPlayer.isYou() || playerToSwap.isYou()){
//                        sortHandBtn.setDisable(false);
//                        viewOtherSideBtn.setDisable(false);
//                    }
                });
        Timeline swapTimeline = new Timeline(endFrame1, endFrame2);
        // play animation
        swapTimeline.play();
    }

    private void zeroRotateAnimation(ArrayList<Card> lastCardsOfAllHands){
        ArrayList<KeyValue> values = new ArrayList<>();

        for(int i = 0; i < noOfPlayers; i++){
            drawCard(lastCardsOfAllHands.get(i), players.get(i).getHandX(), players.get(i).getHandY(), false);
            Group visibleCardTemp = lastCardsOfAllHands.get(i).getVisibleCard();

            KeyValue keyValueTemp1, keyValueTemp2;
            if(clockwise){
                if(i == noOfPlayers - 1){
                    keyValueTemp1 = new KeyValue(visibleCardTemp.translateXProperty(), players.get(0).getHandX(), Interpolator.EASE_BOTH);
                    keyValueTemp2 = new KeyValue(visibleCardTemp.translateYProperty(), players.get(0).getHandY(), Interpolator.EASE_BOTH);
                }else{
                    keyValueTemp1 = new KeyValue(visibleCardTemp.translateXProperty(), players.get(i + 1).getHandX(), Interpolator.EASE_BOTH);
                    keyValueTemp2 = new KeyValue(visibleCardTemp.translateYProperty(), players.get(i + 1).getHandY(), Interpolator.EASE_BOTH);
                }
            }else{
                if(i == 0){
                    keyValueTemp1 = new KeyValue(visibleCardTemp.translateXProperty(), players.get(noOfPlayers - 1).getHandX(), Interpolator.EASE_BOTH);
                    keyValueTemp2 = new KeyValue(visibleCardTemp.translateYProperty(), players.get(noOfPlayers - 1).getHandY(), Interpolator.EASE_BOTH);
                }else{
                    keyValueTemp1 = new KeyValue(visibleCardTemp.translateXProperty(), players.get(i - 1).getHandX(), Interpolator.EASE_BOTH);
                    keyValueTemp2 = new KeyValue(visibleCardTemp.translateYProperty(), players.get(i - 1).getHandY(), Interpolator.EASE_BOTH);
                }
            }
            values.add(keyValueTemp1);
            values.add(keyValueTemp2);
        }

        // animation lasts 500ms
        KeyFrame endFrame1;
        switch (noOfPlayers){
            case 2:
                endFrame1 = new KeyFrame(Duration.millis(500), values.get(0), values.get(1), values.get(2), values.get(3));
                break;
            case 3:
                endFrame1 = new KeyFrame(Duration.millis(500), values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5));
                break;
            case 4:
                endFrame1 = new KeyFrame(Duration.millis(500), values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7));
                break;
            case 5:
                endFrame1 = new KeyFrame(Duration.millis(500), values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7), values.get(8), values.get(9));
                break;
            default:
                endFrame1 = new KeyFrame(Duration.millis(500));
                break;
        }
//        KeyFrame endFrame1 = new KeyFrame(Duration.millis(500), keyValueArrayList);
//        for(KeyValue keyValueTemp : keyValueArrayList){
//            endFrame1.getValues().add(keyValueTemp);
//        }
        // handle event after animation finished
        KeyFrame endFrame2 = new KeyFrame(Duration.millis(500),
                e -> {
                    // remove that temporary card from pane
                    for(Card card : lastCardsOfAllHands){
                        removeCardFromGamePane(card);
                    }

                    // draw hands
                    for(Player playerTemp : players){
                        drawAHand(playerTemp, false);
                    }
                });
        Timeline rotateTimeline = new Timeline(endFrame1, endFrame2);
        // play animation
        rotateTimeline.play();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void playerForcedDrawCardsAction(int delay){
        ArrayList<Card> playerHand = currentPlayer.getHand();

        KeyFrame drawFrame = new KeyFrame(Duration.millis(delay),
                e -> {
                    Card topCardOfDeck = deck.peek();
                    // remove card's elements shown in pane
                    removeCardFromGamePane(topCardOfDeck);
                    // take the card on top of the deck to bot's hand
                    playerHand.add(deck.pop());

                    // redraw the deck
                    drawDeck();

                    // update history
                    addGameHistoryWhenDrawCard();

                    forcedDrawFromDeckAnimation(topCardOfDeck);

                    if(noOfCardsToDraw == -1){
                        // INITIATE DRAW TO MATCH COLOR
                        if(!lightSide && topCardOfDeck.getDarkColor() == discardPile.peek().getDarkColor()){
                            // ADD AN OPTION TO KEEP DRAWING TO MATCH COLOR
                            gameHistory.appendText("That was a matching card.\n");
                            drawToMatchColorRepeatTimes--;
                            if(drawToMatchColorRepeatTimes == 0){
                                // MAY CHECK IF THE PLAYER DOESN'T GET SKIPPED AND CAN PLAY THE TURN ?????????????????????????/
                                setPunishments(false, 0, 0);
                                addGameHistoryWhenGetSkipped();
                                endTurnAction();
                            }else if(drawToMatchColorRepeatTimes > 0){
                                gameHistory.appendText("Draw to match color again because of stacking Dark Wild Draw.\n");
                                playerForcedDrawCardsAction(500);
                            }
                        }else{
                            playerForcedDrawCardsAction(500);
                        }
                    }else if(noOfCardsToDraw > 0){
                        // INITIATE DRAW N CARDS
                        noOfCardsToDraw--;
                        if(noOfCardsToDraw == 0){
                            // MAY CHECK IF THE PLAYER DOESN'T GET SKIPPED AND CAN PLAY THE TURN (FOR CHALLENGE MODE???)
                            setPunishments(false, 0, 0);
                            addGameHistoryWhenGetSkipped();
                            endTurnAction();
                        }else{
                            playerForcedDrawCardsAction(500);
                        }
                    }
                });

        Timeline drawTimeline = new Timeline(drawFrame);
        drawTimeline.play();
    }

    private void sevenSwapAction(Player playerToSwap){
//        Player playerToSwap = players.get(playerToSwapIndex);
        ArrayList<Card> currentHand = currentPlayer.getHand();
        ArrayList<Card> handToSwap = playerToSwap.getHand();

        KeyFrame swapFrame = new KeyFrame(Duration.millis(1000),
                e -> {
                    if(currentPlayer.isYou() || playerToSwap.isYou()){
                        sortHandBtn.setDisable(true);
                        viewOtherSideBtn.setDisable(true);
                        switchSortMethod = false;
                    }
                    // remove cards on screen
                    for(Card cardTemp : currentHand){
                        removeCardFromGamePane(cardTemp);
                    }
                    for(Card cardTemp : handToSwap){
                        removeCardFromGamePane(cardTemp);
                    }
                    // ?
                    Card lastCardOfCurrentHand = currentHand.get(currentHand.size() - 1);
                    int currentX = currentPlayer.getHandX();
                    int currentY = currentPlayer.getHandY();
                    Card lastCardOfHandToSwap = handToSwap.get(handToSwap.size() - 1);
                    int toSwapX = playerToSwap.getHandX();
                    int toSwapY = playerToSwap.getHandY();
                    // update data
                    ArrayList<Card> handTemp = new ArrayList<>(currentHand);
                    currentHand.clear();
                    currentHand.addAll(handToSwap);
                    handToSwap.clear();
                    handToSwap.addAll(handTemp);
                    // history
                    addGameHistoryWhenSevenSwap(playerToSwap);
                    // animation
                    sevenSwapAnimation(lastCardOfCurrentHand, currentX,currentY, lastCardOfHandToSwap, toSwapX, toSwapY, playerToSwap);

                    endTurnAction();
                });

        Timeline swapTimeline = new Timeline(swapFrame);
        swapTimeline.play();
    }

    private void zeroRotateAction(){
        KeyFrame rotateFrame = new KeyFrame(Duration.millis(1000),
                e -> {
                    sortHandBtn.setDisable(true);
                    viewOtherSideBtn.setDisable(true);
                    switchSortMethod = false;
                    // remove cards on screen
                    ArrayList<Card> lastCardsOfAllHands = new ArrayList<>();
                    for(Player playerTemp : players){
                        ArrayList<Card> handTemp = playerTemp.getHand();
                        lastCardsOfAllHands.add(handTemp.get(handTemp.size() - 1));
                        for(Card cardTemp : handTemp){
                            removeCardFromGamePane(cardTemp);
                        }
                    }

                    // update data
                    if(clockwise){
                        ArrayList<Card> lastHand = players.get(noOfPlayers - 1).getHand();
                        ArrayList<Card> handTemp = new ArrayList<>(lastHand);
                        for(int i = noOfPlayers - 1; i > 0; i--){
                            ArrayList<Card> thisHand = players.get(i).getHand();
                            thisHand.clear();
                            thisHand.addAll(players.get(i - 1).getHand());
                        }
                        ArrayList<Card> firstHand = players.get(0).getHand();
                        firstHand.clear();
                        firstHand.addAll(handTemp);
                    }else{
                        ArrayList<Card> firstHand = players.get(0).getHand();
                        ArrayList<Card> handTemp = new ArrayList<>(firstHand);
                        for(int i = 0; i < noOfPlayers - 1; i++){
                            ArrayList<Card> thisHand = players.get(i).getHand();
                            thisHand.clear();
                            thisHand.addAll(players.get(i + 1).getHand());
                        }
                        ArrayList<Card> lastHand = players.get(noOfPlayers - 1).getHand();
                        lastHand.clear();
                        lastHand.addAll(handTemp);
                    }
                    // history
                    addGameHistoryWhenZeroRotate();
                    // animation
                    zeroRotateAnimation(lastCardsOfAllHands);

                    endTurnAction();
                });

        Timeline rotateTimeline = new Timeline(rotateFrame);
        rotateTimeline.play();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PLAYER-CONTROLLED LOGIC
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // add event when you click a card in your hand
    private void addCardPlayEvent(Card cardTemp, int x, int y){
        ArrayList<Card> myHand = you.getHand();
        // add card click event
        cardTemp.getInvisibleCard().setOnMouseClicked(e -> {
            // check if you can play
            if(youCanPlay(cardTemp)){
                youPlayedACard = true;
                lastPlayedCard = cardTemp;
                // "true" means the card is being played
                lockingUserAction = true;

                // MAY DISABLE SOME BUTTONS LATER
                // sort and view x buttons can't be clicked while the card is being played
                sortHandBtn.setDisable(true);
                viewOtherSideBtn.setDisable(true);
                endTurnBtn.setDisable(true);

                // remove card's elements shown in pane
                removeCardFromGamePane(cardTemp);
                // add that card to the discard pile
                discardPile.push(cardTemp);
                // remove that card from your hand
                myHand.remove(cardTemp);
                if(myHand.isEmpty()){
                    gameEnd = true;
                }

                // redraw my hand with up/down animation,
                // but you can't click because playingACard = true
                // to prevent playing multiple cards before moving animation ends
                drawMyHand();

                // update history
                addGameHistoryWhenPlayCard(cardTemp);

                // add animation where that card moves from your hand to the discard pile
                playedCardAnimation(cardTemp, x, y - cardHeight);
            }
        });
    }

    // check if you can play the card, ADD MORE CONDITIONS LATER
    private boolean youCanPlay(Card card){
        // if it's your turn (MIGHT FIX FOR 2V2) and game hasn't ended
        if(turn == players.indexOf(you) && viewingFront && !lockingUserAction && !gameEnd){
            if(youPlayedACard){
                if(lightSide){
                    if(card.getLightValue() == discardPile.peek().getLightValue()){
                        if(discardPile.peek().getLightColor() != LightColor.WILD){
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }else{
                    if(card.getDarkValue() == discardPile.peek().getDarkValue()){
                        if(discardPile.peek().getDarkColor() != DarkColor.DARK_WILD){
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }
            }
            if(youDrawnACard && card != lastDrawnCard){
                return false;
            }
            if(lightSide){
                if(!goingToApplyPunishment){
                    // same light color, value or a wild card (if the card matches the top card)
                    if((card.getLightColor() == discardPile.peek().getLightColor()) ||
                            (card.getLightValue() == discardPile.peek().getLightValue()) ||
                            (card.getLightColor() == LightColor.WILD)){
                        // not an unchanged wild card
                        if((discardPile.peek().getLightColor() != LightColor.WILD)){
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }else{
                    if(card.getLightValue() == discardPile.peek().getLightValue()){
                        acceptBtn.setVisible(false);
                        return true;
                    }else{
                        return false;
                    }
                }
            }else{
                if(!goingToApplyPunishment){
                    // same dark color, value or a dark wild card (if the card matches the top card)
                    if((card.getDarkColor() == discardPile.peek().getDarkColor()) ||
                            (card.getDarkValue() == discardPile.peek().getDarkValue()) ||
                            (card.getDarkColor() == DarkColor.DARK_WILD)){
                        // not an unchanged dark wild card
                        if((discardPile.peek().getDarkColor() != DarkColor.DARK_WILD)){
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }else{
                    if(card.getDarkValue() == discardPile.peek().getDarkValue()){
                        acceptBtn.setVisible(false);
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }else{
            return false;
        }
    }

    private void youForcedPlayDrawnCardAction(Card drawnCard){
        ArrayList<Card> myHand = you.getHand();
        int x = you.getHandX() + myHand.indexOf(drawnCard) % cardsPerRow * cardGap;
        int y = you.getHandY() + cardGap * (myHand.indexOf(drawnCard) / cardsPerRow);

        KeyFrame forceFrame = new KeyFrame(Duration.millis(1000),
                e -> {
                    youPlayedACard = true;
                    lastPlayedCard = drawnCard;

                    // remove card's elements shown in pane
                    removeCardFromGamePane(drawnCard);
                    // add that card to the discard pile
                    discardPile.push(drawnCard);
                    // remove that card from your hand
                    myHand.remove(drawnCard);

//                    drawMyHand(); // MAY REMOVE

                    // update history
                    addGameHistoryWhenPlayCard(drawnCard);

                    // add animation where that card moves from your hand to the discard pile
                    playedCardAnimation(drawnCard, x, y - cardHeight);
                });

        Timeline forceTimeline = new Timeline(forceFrame);
        forceTimeline.play();
    }

    // add event when you click the deck to draw a card
    private void addDeckDrawEvent(){
        ArrayList<Card> myHand = you.getHand();

        // add card on deck click event
        Card topCardOfDeck = deck.peek();
        topCardOfDeck.getInvisibleCard().setOnMouseClicked(e -> {
            // ADD CONDITIONS LATER
            if(youCanDraw()){
                youDrawnACard = true;
                // "true" means the card is being drawn
                lockingUserAction = true;

                // MAY DISABLE SOME BUTTONS LATER
                // sort and view x buttons can't be clicked while the card is being drawn
                sortHandBtn.setDisable(true);
                viewOtherSideBtn.setDisable(true);
                endTurnBtn.setDisable(true);

                setupForYouDrawing(myHand, topCardOfDeck);

//                lastDrawnCard = topCardOfDeck;
//
//                // remove card's elements shown in pane
//                removeCardFromGamePane(topCardOfDeck);
//                // take the card on top of the deck to your hand
//                myHand.add(deck.pop());
//
//                // redraw the deck,
//                // but you can't click because drawingACard = true
//                // to prevent drawing multiple cards before moving animation ends
//                drawDeck();
//
//                // update history
//                addGameHistoryWhenDrawCard();
//
//                // add animation where that card moves from the deck to your hand
//                drawFromDeckAnimation(topCardOfDeck);
            }
        });
    }

    private void setupForYouDrawing(ArrayList<Card> myHand, Card topCardOfDeck){
        lastDrawnCard = topCardOfDeck;

        // remove card's elements shown in pane
        removeCardFromGamePane(topCardOfDeck);
        // take the card on top of the deck to your hand
        myHand.add(deck.pop());

        // redraw the deck,
        // but you can't click because drawingACard = true
        // to prevent drawing multiple cards before moving animation ends
        drawDeck();

        // update history
        addGameHistoryWhenDrawCard();

        // add animation where that card moves from the deck to your hand
        drawFromDeckAnimation(topCardOfDeck);
    }

    private void youDrawToMatchAction(){
        ArrayList<Card> myHand = you.getHand();
        Card topCardOfDeck = deck.peek();

        KeyFrame drawFrame = new KeyFrame(Duration.millis(250),
                e -> {
                    setupForYouDrawing(myHand, topCardOfDeck);
                });

        Timeline drawTimeline = new Timeline(drawFrame);
        drawTimeline.play();
    }

    // check if you can draw a card, ADD MORE CONDITIONS LATER
    private boolean youCanDraw(){
        if(turn == players.indexOf(you) && viewingFront && !lockingUserAction && !goingToApplyPunishment && !youPlayedACard && !youDrawnACard && !gameEnd){
            if(lightSide){
                // not an unchanged wild card, you're viewing the front side of your hand and no animation is playing
                if((discardPile.peek().getLightColor() != LightColor.WILD)){
                    return true;
                }else{
                    return false;
                }
            }else{
                // not an unchanged dark wild card, you're viewing the front side of your hand and no animation is playing
                if((discardPile.peek().getDarkColor() != DarkColor.DARK_WILD)){
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }

    // sort my hand base on different variables
    void sortMyHand(){
        // switch sort method, by default sort method is by color and it won't be switched
        if(switchSortMethod){
            doSwitchSortMethod();
        }

        ArrayList<Card> myHand = you.getHand();
        // sort the card objects in my hand
        for (int i = 0; i < myHand.size(); i++) {
            for (int j = 1; j < (myHand.size() - i); j++) {
                // main condition
                if (compareCards(myHand, j - 1, j, sortByColor, true) > 0) {
                    Collections.swap(myHand, j - 1, j);
                }else if(compareCards(myHand, j - 1, j, sortByColor, true) == 0){
                    // secondary condition
                    if(compareCards(myHand, j - 1, j, sortByColor, false) > 0){
                        Collections.swap(myHand, j - 1, j);
                    }
                }
            }
        }
        // redraw my hand
        drawMyHand();

        // set up so that consecutive calls switch the sort method
        switchSortMethod = true;
    }

    // compare 2 cards in my hand based on sort methods and condition
    private int compareCards(ArrayList<Card> myHand, int index1, int index2, boolean byColor, boolean mainCondition){
        if(lightSide){
            if((byColor && mainCondition) || (!byColor && !mainCondition)){
                return myHand.get(index1).getLightColor().compareTo(myHand.get(index2).getLightColor());
            }else{
                return myHand.get(index1).getLightValue().compareTo(myHand.get(index2).getLightValue());
            }
        }else{
            if((byColor && mainCondition) || (!byColor && !mainCondition)){
                return myHand.get(index1).getDarkColor().compareTo(myHand.get(index2).getDarkColor());
            }else{
                return myHand.get(index1).getDarkValue().compareTo(myHand.get(index2).getDarkValue());
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BOT LOGIC
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // the first thing a bot does
    private void botPlayTheirTurn() {
        // set initial variables
        botPlayed = false;
        botDrawn = false;

        // CHECK FOR PUNISHMENTS
        if(goingToApplyPunishment){
            if(noOfCardsToDraw > 0 || noOfCardsToDraw == -1){
                if(!Settings.isStacking()){
                    // INITIATE DRAW N CARDS OR DRAW TO MATCH COLOR
                    playerForcedDrawCardsAction(1000);
                }else{
                    // CHECK IF THERE IS AT LEAST ONE STACKABLE CARD AND PLAY IT. IF NOT, ACCEPT PUNISHMENT
                    botStartPlayingOrDrawing();
                }
            }else if(noOfPlayersToSkip > 0){
                // INITIATE GET SKIPPED
                noOfPlayersToSkip--;
                if(noOfPlayersToSkip == 0){
                    setPunishments(false, 0, 0);
                }
                addGameHistoryWhenGetSkipped();
                endTurnAction();
            }
        }else{
            // if a (dark) wild card is seen at the start of a bot's turn
            if((lightSide && discardPile.peek().getLightColor() == LightColor.WILD) ||
                    (!lightSide && discardPile.peek().getDarkColor() == DarkColor.DARK_WILD)){
                wildOnTurn = true;
                // change color first
                botChangeColorAction();
            }else{
                wildOnTurn = false;
                // if not, the bot starts the play
                botStartPlayingOrDrawing();
            }
        }
    }

//    private void botStartStackingOrAccepting(){ // REMOVE?
//        ArrayList<Card> botHand = currentPlayer.getHand();
//        int handX = currentPlayer.getHandX();
//        int handY = currentPlayer.getHandY();
//
//        // check if a card can be stacked
//        for(int i = 0; i < botHand.size(); i++){
//            Card cardTemp = botHand.get(i);
//
//            int x = handX + i % cardsPerRow * cardGap;
//            int y = handY + cardGap * (i / cardsPerRow);
//
//            if(botCanPlay(cardTemp)){
//                // if can play a card, it will play that card
//                botPlayCardAction(cardTemp, x, y);
//                // USED FOR MULTIPLE CARDS MODE ???
//                lastPlayedCard = cardTemp;
//                botPlayed = true;
//                break;
//            }
//        }
//
//        // if bot can't play any card
//        if(!botPlayed){
//            if(!botDrawn){
//                if(!goingToApplyPunishment){
//                    // bot draws a card
//                    botDrawCardAction();
//                    botDrawn = true;
//                }else{
//                    // INITIATE DRAW N CARDS OR DRAW TO MATCH COLOR
//                    playerForcedDrawCardsAction();
//                }
//            }else{
//                // bot ends turn if it can't play and has drawn a card, (CHANGE FOR DRAW-TO-MATCH MODE ???)
//                addGameHistoryWhenCantPlay();
//                endTurnAction();
//            }
//        }
//    }

    // bot decides if it wants to play or draw
    private void botStartPlayingOrDrawing(){
        ArrayList<Card> botHand = currentPlayer.getHand();
        int handX = currentPlayer.getHandX();
        int handY = currentPlayer.getHandY();

        // check if a card can be played/stacked
        for(int i = 0; i < botHand.size(); i++){
            Card cardTemp = botHand.get(i);

            int x = handX + i % cardsPerRow * cardGap;
            int y = handY + cardGap * (i / cardsPerRow);

            if(botCanPlay(cardTemp)){
                // if can play a card, it will play that card
                botPlayCardAction(cardTemp, x, y);
                // USED FOR MULTIPLE CARDS MODE ???
                lastPlayedCard = cardTemp;
                botPlayed = true;
                break;
            }
        }

        // if bot can't play any card
        if(!botPlayed){
            if(!botDrawn){
                if(!goingToApplyPunishment){
                    // bot draws a card
                    botDrawCardAction(1000);
                    botDrawn = true;
                }else{
                    // INITIATE DRAW N CARDS OR DRAW TO MATCH COLOR
                    playerForcedDrawCardsAction(1000);
                }
            }else{
                if(!Settings.isDrawToMatch()){
                    // bot ends turn if it can't play and has drawn a card, (CHANGE FOR DRAW-TO-MATCH MODE ???)
                    addGameHistoryWhenCantPlay();
                    endTurnAction();
                }else{
                    botDrawCardAction(500);
                }
            }
        }
    }

    // check if you can play the card, MAY ADD MORE CONDITIONS LATER
    private boolean botCanPlay(Card card){
        if(botPlayed){
            if(lightSide){
                return card.getLightValue() == discardPile.peek().getLightValue();
            }else{
                return card.getDarkValue() == discardPile.peek().getDarkValue();
            }
        }
        if(lightSide){
            if(!goingToApplyPunishment){
                // same light color, value or a wild card (if the card matches the top card)
                return (card.getLightColor() == discardPile.peek().getLightColor()) ||
                        (card.getLightValue() == discardPile.peek().getLightValue()) ||
                        (card.getLightColor() == LightColor.WILD);
            }else{
                return card.getLightValue() == discardPile.peek().getLightValue();
            }
        }else{
            if(!goingToApplyPunishment){
                // same dark color, value or a dark wild card (if the card matches the top card)
                return (card.getDarkColor() == discardPile.peek().getDarkColor()) ||
                        (card.getDarkValue() == discardPile.peek().getDarkValue()) ||
                        (card.getDarkColor() == DarkColor.DARK_WILD);
            }else{
                return card.getDarkValue() == discardPile.peek().getDarkValue();
            }
        }
    }

    private void botKeepsPlayingOrEndTurn(){
        ArrayList<Card> botHand = currentPlayer.getHand();
        int handX = currentPlayer.getHandX();
        int handY = currentPlayer.getHandY();

        boolean botPlayedAnotherCard = false;
        // check if a card can be played
        for(int i = 0; i < botHand.size(); i++){
            Card cardTemp = botHand.get(i);

            int x = handX + i % cardsPerRow * cardGap;
            int y = handY + cardGap * (i / cardsPerRow);

            if(botCanPlay(cardTemp)){
                // if can play a card, it will play that card
                botPlayCardAction(cardTemp, x, y);
                // USED FOR MULTIPLE CARDS MODE ???
                lastPlayedCard = cardTemp;
                botPlayedAnotherCard = true;
                break;
            }
        }

        // if bot can't play any card
        if(!botPlayedAnotherCard){
            endTurnAction();
        }
    }

    // when the bot chooses to play a card
    private void botPlayCardAction(Card cardToPlay, int x, int y){
        ArrayList<Card> botHand = currentPlayer.getHand();

        KeyFrame playFrame = new KeyFrame(Duration.millis(1000),
                e -> {
                    // remove card's elements shown in pane
                    removeCardFromGamePane(cardToPlay);
                    // add that card to the discard pile
                    discardPile.push(cardToPlay);
                    // remove that card from bot's hand
                    botHand.remove(cardToPlay);
                    if(botHand.isEmpty()){
                        gameEnd = true;
                    }

                    drawBotsHand(currentPlayer, false);

                    addGameHistoryWhenPlayCard(cardToPlay);

                    playedCardAnimation(cardToPlay, x, y);

//                    // CONDITIONS
//                    if((lightSide && discardPile.peek().getLightValue() == LightValue.FLIP) ||
//                            (!lightSide && discardPile.peek().getDarkValue() == DarkValue.D_FLIP)){
//                        endTurnAction(); // MIGHT FIX LATER
//                    }else if((lightSide && discardPile.peek().getLightColor() == LightColor.WILD) ||
//                            (!lightSide && discardPile.peek().getDarkColor() == DarkColor.DARK_WILD)){
//                        botChangeColorAction();
//                    }else{
//                        endTurnAction(); // ADD MORE CONDITIONS LATER FOR MULTIPLE CARDS MODE ???
//                    }
                });

        Timeline playTimeline = new Timeline(playFrame);
        playTimeline.play();
    }

    // when the bot picks a color
    private void botChangeColorAction(){
        KeyFrame changeColorFrame = new KeyFrame(Duration.millis(1000),
                e -> {
                    // random FOR NOW
                    Random rng = new Random();
                    int colorInt = rng.nextInt(4);

                    if(lightSide){
                        switch (colorInt){
                            case 0:
                                addGameHistoryWhenChangeColor("Red");
                                changeLightColorOfTopCard(LightColor.RED);
                                break;
                            case 1:
                                addGameHistoryWhenChangeColor("Blue");
                                changeLightColorOfTopCard(LightColor.BLUE);
                                break;
                            case 2:
                                addGameHistoryWhenChangeColor("Green");
                                changeLightColorOfTopCard(LightColor.GREEN);
                                break;
                            case 3:
                                addGameHistoryWhenChangeColor("Yellow");
                                changeLightColorOfTopCard(LightColor.YELLOW);
                                break;
                        }
                    }else{
                        switch (colorInt){
                            case 0:
                                addGameHistoryWhenChangeColor("Garnet");
                                changeDarkColorOfTopCard(DarkColor.GARNET);
                                break;
                            case 1:
                                addGameHistoryWhenChangeColor("Amethyst");
                                changeDarkColorOfTopCard(DarkColor.AMETHYST);
                                break;
                            case 2:
                                addGameHistoryWhenChangeColor("Emerald");
                                changeDarkColorOfTopCard(DarkColor.EMERALD);
                                break;
                            case 3:
                                addGameHistoryWhenChangeColor("Amber");
                                changeDarkColorOfTopCard(DarkColor.AMBER);
                                break;
                        }
                    }

                    // if that (dark) wild card is the one it saw at the start of the turn
                    if(wildOnTurn){
                        // the bot now starts the play
                        botStartPlayingOrDrawing();
                        wildOnTurn = false;
                    }else{
                        // if not, it means the bot played this (dark) wild card, ending the turn
                         // ADD MORE CONDITIONS TO THIS LATER
                        if(!Settings.isMultipleCards()){
                            endTurnAction();
                        }else{
                            if(!gameEnd){
                                botKeepsPlayingOrEndTurn();
                            }else{
                                endTurnAction();
                            }
                        }
                    }
                });

        Timeline changeColorTimeline = new Timeline(changeColorFrame);
        changeColorTimeline.play();
    }

    // when the bot draws a card
    private void botDrawCardAction(int delay){
        ArrayList<Card> botHand = currentPlayer.getHand();

        KeyFrame drawFrame = new KeyFrame(Duration.millis(delay),
                e -> {
                    Card topCardOfDeck = deck.peek();
                    // remove card's elements shown in pane
                    removeCardFromGamePane(topCardOfDeck);
                    // take the card on top of the deck to bot's hand
                    botHand.add(deck.pop());

                    // redraw the deck
                    drawDeck();

                    // update history
                    addGameHistoryWhenDrawCard();

//                    botDrawFromDeckAnimation(topCardOfDeck);
                    drawFromDeckAnimation(topCardOfDeck);

                    // after drawing a card, the bot then decides if it can be played (OR KEEP DRAWING FOR DRAW-TO-MATCH MODE ???)
                    botStartPlayingOrDrawing();
                });

        Timeline drawTimeline = new Timeline(drawFrame);
        drawTimeline.play();
    }

    private void botChoosePlayerToSwap(){ // MIGHT ADD DELAY
        Player playerToSwap = players.get(0);
        if(playerToSwap == currentPlayer){
            playerToSwap = players.get(1);
        }

        for (Player playerTemp : players) {
            if (playerTemp.getHand().size() < playerToSwap.getHand().size() && playerTemp != currentPlayer) {
                playerToSwap = playerTemp;
            }
        }

        sevenSwapAction(playerToSwap);
    }

    // when a player ends the turn
    void endTurnAction(){
        KeyFrame drawFrame = new KeyFrame(Duration.millis(1000), e -> {
//            sortHandBtn.setDisable(false);
//            viewOtherSideBtn.setDisable(false);
            goToNextTurn();
        });

        Timeline drawTimeline = new Timeline(drawFrame);
        drawTimeline.play();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TURN LOGIC
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // calculate the next turn base on direction and number of players
    private void nextTurn(){
        if(clockwise){
            turn++;
            if(turn >= noOfPlayers){
                turn = 0;
            }
        }else{
            turn--;
            if(turn < 0){
                turn = noOfPlayers - 1;
            }
        }
    }

    // switch between clockwise and counter-clockwise, then set the correct direction gif
    // this keeps the gif moving under the cards
    private void switchDirection(){
        clockwise = !clockwise;
        setCorrectGif();
        addGameHistoryWhenReverse();
    }

    // set the correct direction gif
    private void setCorrectGif(){
        if(clockwise){
            directionIV.setImage(cwImg);
        }else{
            directionIV.setImage(ccwImg);
        }
    }

//    int getTurn() {
//        return turn;
//    }

    void goToNextTurn(){
        addGameHistoryWhenEndTurn();
        lockingUserAction = false;
        if(viewingFront){
            sortHandBtn.setDisable(false);
        }
        viewOtherSideBtn.setDisable(false);

        // keep going if game hasn't end
        if(!gameEnd){
            // calculate next turn and draw new avatar glow
            nextTurn();
            drawAvatarGlow();

            // get the current player
            currentPlayer = players.get(turn);
            addGameHistoryWhenStartTurn();

            // if it's a bot
            if(!currentPlayer.isYou()){
                // the bot starts its turn
                botPlayTheirTurn();
            }else{
                // DO STH MORE WITH DRAW CARDS LATER ??
                youPlayedACard = false;
                youDrawnACard = false;
                // CHECK FOR PUNISHMENTS
                if(goingToApplyPunishment){
                    lockingUserAction = true;
                    if(noOfCardsToDraw > 0 || noOfCardsToDraw == -1){
                        if(!Settings.isStacking()){
                            initiateDrawNCards();
                        }else{
                            lockingUserAction = false;
                            acceptBtn.setVisible(true);
                        }
                    }else if(noOfPlayersToSkip > 0){
                        // INITIATE GET SKIPPED
                        noOfPlayersToSkip--;
                        if(noOfPlayersToSkip == 0){
                            setPunishments(false, 0, 0);
                        }
                        addGameHistoryWhenGetSkipped();
                        endTurnAction();
                    }
                }else{
                    // show light/dark buttons if you see (dark) wild card at the start of your turn
                    if(lightSide && discardPile.peek().getLightColor() == LightColor.WILD){
                        gamePane.getChildren().add(lightBtnGroup);
                    }else if(!lightSide && discardPile.peek().getDarkColor() == DarkColor.DARK_WILD){
                        gamePane.getChildren().add(darkBtnGroup);
                    }
                }
            }
        }else{ // if a player played all of their cards
            viewingFront = true;
            viewOtherSideBtn.setText("View back");
            if(!currentPlayer.isYou()){
                // if a bot wins, redraw my hand facing up
                drawMyHand();
            }

            // disable buttons
            sortHandBtn.setDisable(true);
            viewOtherSideBtn.setDisable(true);

            // CHECK FOR PUNISHMENTS, DISABLE BUTTONS REGARDLESS
            if(goingToApplyPunishment && noOfCardsToDraw != 0){
                // DRAW CARDS, THEN DO THE USUAL
                nextTurn();
                gameHistory.appendText(players.get(turn).getName());
                if(players.get(turn).isYou()){
                    gameHistory.appendText("(you)");
                }
                gameHistory.appendText(" must draw\n");
                playerForcedDrawCardsAtEndAction();
            }else{
                // redraw all bots' hand facing up
                showAllBotsHand();
                addGameHistoryWhenEndGame();
                // SHOW SCOREBOARD ACTION
                showScoreboardAction();
            }
            // WHAT ELSE TO DO WHEN GAME HAS ENDED???
        }
    }

    void initiateDrawNCards(){
        // INITIATE DRAW N CARDS OR DRAW TO MATCH COLOR
        sortHandBtn.setDisable(true);
        viewOtherSideBtn.setDisable(true);
        if(!viewingFront){
            viewOtherSideBtn.setText("View back");

            viewingFront = true;
            drawMyHand();
        }
        playerForcedDrawCardsAction(1000);
    }

    // redraw all bots' hand facing up at the end of the game
    private void showAllBotsHand(){
        for(Player player : players){
            if(!player.isYou()){
                drawBotsHand(player, true);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void forcedDrawAtEndAnimation(Card card){
        // draw card that was from the top of the deck,
        // mostly from the drawn card so it acts as a temporary animation
        drawCard(card, deckX, deckY, false);
        Group visibleCardTemp = card.getVisibleCard();
        Rectangle invisibleCardTemp = card.getInvisibleCard();

        // calculate destination base on the size of player's hand
        Player nextPlayer = players.get(turn);
        int playerHandSizeBefore = nextPlayer.getHand().size() - 1;
        int endX = nextPlayer.getHandX() + playerHandSizeBefore % cardsPerRow * cardGap;
        int endY = nextPlayer.getHandY() + cardGap * (playerHandSizeBefore / cardsPerRow);

        // destination of the card
        KeyValue endValue1 = new KeyValue(visibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
        KeyValue endValue2 = new KeyValue(visibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);
        KeyValue endValue3 = new KeyValue(invisibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
        KeyValue endValue4 = new KeyValue(invisibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);

        // animation lasts 250ms
        KeyFrame endFrame1 = new KeyFrame(Duration.millis(250), endValue1, endValue2, endValue3, endValue4);
        // handle event after animation finished
        KeyFrame endFrame2 = new KeyFrame(Duration.millis(250),
                e -> {
                    // remove that temporary card from pane
                    removeCardFromGamePane(card);

                    if(nextPlayer.isYou()){
                        // show new card in your hand
                        drawCard(card, endX, endY, true);
                        // add moving animation and handle card click for the new card
                        addUpDownAnimation(card, endX, endY);
                        addCardPlayEvent(card, endX, endY);
                    }else{
                        // show new card in bot's hand
                        drawCard(card, endX, endY, false);
                    }
                });
        Timeline drawFromDeckTimeline = new Timeline(endFrame1, endFrame2);
        // play animation
        drawFromDeckTimeline.play();
    }

    private void playerForcedDrawCardsAtEndAction(){
        ArrayList<Card> nextPlayerHand = players.get(turn).getHand();

        KeyFrame drawFrame = new KeyFrame(Duration.millis(500),
                e -> {
                    Card topCardOfDeck = deck.peek();
                    // remove card's elements shown in pane
                    removeCardFromGamePane(topCardOfDeck);
                    // take the card on top of the deck to bot's hand
                    nextPlayerHand.add(deck.pop());

                    // redraw the deck
                    drawDeck();

                    // update history
                    addGameHistoryWhenDrawCard();

                    forcedDrawAtEndAnimation(topCardOfDeck);

                    if(noOfCardsToDraw == -1){
                        // INITIATE DRAW TO MATCH COLOR
                        if(!lightSide && topCardOfDeck.getDarkColor() == discardPile.peek().getDarkColor()){
                            // ADD AN OPTION TO KEEP DRAWING TO MATCH COLOR
                            gameHistory.appendText("That was a matching card.\n");
                            drawToMatchColorRepeatTimes--;
                            if(drawToMatchColorRepeatTimes == 0){
                                endGameAction();
                            }else if(drawToMatchColorRepeatTimes > 0){
                                gameHistory.appendText("Draw to match color again because of stacking Dark Wild Draw.\n");
                                playerForcedDrawCardsAtEndAction();
                            }
                        }else{
                            playerForcedDrawCardsAtEndAction();
                        }
                    }else if(noOfCardsToDraw > 0){
                        // INITIATE DRAW N CARDS
                        noOfCardsToDraw--;
                        if(noOfCardsToDraw == 0){
                            endGameAction();
                        }else{
                            playerForcedDrawCardsAtEndAction();
                        }
                    }
                });

        Timeline drawTimeline = new Timeline(drawFrame);
        drawTimeline.play();
    }

    private void addGameHistoryWhenEndGame(){
        gameHistory.appendText(currentPlayer.getName());
        if(currentPlayer.isYou()){
            gameHistory.appendText("(you)");
        }
        gameHistory.appendText(" won the game!\n");
    }

    private void endGameAction(){
        KeyFrame endGameFrame = new KeyFrame(Duration.millis(1000),
                e -> {
                    showAllBotsHand();
                    gameHistory.appendText("\n");
                    addGameHistoryWhenEndGame();
                    // SHOW SCOREBOARD ACTION
                    showScoreboardAction();
                });

        Timeline endGameTimeline = new Timeline(endGameFrame);
        endGameTimeline.play();
    }

    private int scoreIndex;
    private int endX;
    private int endY;

    private void showScoreboardAction(){
        KeyFrame showFrame = new KeyFrame(Duration.millis(1000),
                e -> {
                    scorePane.setVisible(true);
                    gamePane.getChildren().remove(scorePane);
                    gamePane.getChildren().add(scorePane);
                    addWinnerGlow();

                    scoreIndex = players.indexOf(currentPlayer);
                    endX = 457 + 290 - (5 + 115 * noOfPlayers) / 2 + 115 * players.indexOf(currentPlayer) + 10 + 7;
                    endY = 190 + 260;
                    if(Settings.isWinByScore()){
                        addScoreAction();
                    }else{
                        addWinAction();
                    }
                });

        Timeline showTimeline = new Timeline(showFrame);
        showTimeline.play();
    }

    private void addWinnerGlow(){
        int x = 290 - (5 + 115 * noOfPlayers) / 2 + 115 * players.indexOf(currentPlayer) + 10;
        int y = 110;
        drawWinnerGlow(x, y);
    }

    // remove previous image of the glow then redraw it on the correct avatar
    private void drawWinnerGlow(int x, int y){
        scorePane.getChildren().remove(winnerGlowIV);
        winnerGlowIV.setTranslateX(x - glowOffset);
        winnerGlowIV.setTranslateY(y - glowOffset);
        scorePane.getChildren().add(winnerGlowIV);
    }

    private void addScoreAction(){
        KeyFrame addFrame = new KeyFrame(Duration.millis(500),
                e -> {
                    if(clockwise){
                        scoreIndex++;
                        if(scoreIndex >= noOfPlayers){
                            scoreIndex = 0;
                        }
                    }else{
                        scoreIndex--;
                        if(scoreIndex < 0){
                            scoreIndex = noOfPlayers - 1;
                        }
                    }
                    if(scoreIndex == players.indexOf(currentPlayer)){
                        // FINAL CHECKING ACTION
                        finalCheckingAction();
                    }else{
                        int scoreGain = calculateScoreGain(players.get(scoreIndex).getHand());
                        // ADD SCORE ANIMATION
                        addScoreAnimation(scoreGain);
                        addScoreAction();
                    }
                });

        Timeline addTimeline = new Timeline(addFrame);
        addTimeline.play();
    }

    private void addWinAction(){
        KeyFrame addFrame = new KeyFrame(Duration.millis(500),
                e -> {
                    addScoreAnimation(1);
                });
        KeyFrame addFrame2 = new KeyFrame(Duration.millis(1000),
                e -> {
                    finalCheckingAction();
                });

        Timeline addTimeline = new Timeline(addFrame, addFrame2);
        addTimeline.play();
    }

    private void addScoreAnimation(int scoreGain){
        // draw element
        int startX = players.get(scoreIndex).getHandX() + 10;
        int startY = players.get(scoreIndex).getHandY() + 80;
        Text scoreText = new Text(String.valueOf(scoreGain));
        scoreText.setTranslateX(startX);
        scoreText.setTranslateY(startY);
        scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        scoreText.setStroke(Color.WHITE);
        scoreText.setStrokeWidth(2);
        gamePane.getChildren().add(scoreText);

        // destination of the element
        KeyValue endValue1 = new KeyValue(scoreText.translateXProperty(), endX, Interpolator.EASE_BOTH);
        KeyValue endValue2 = new KeyValue(scoreText.translateYProperty(), endY, Interpolator.EASE_BOTH);

        // animation lasts 250ms
        KeyFrame endFrame1 = new KeyFrame(Duration.millis(500), endValue1, endValue2);
        // handle event after animation finished
        KeyFrame endFrame2 = new KeyFrame(Duration.millis(500),
                e -> {
                    gamePane.getChildren().remove(scoreText);
                    updateScore(scoreGain);
                });
        Timeline drawFromDeckTimeline = new Timeline(endFrame1, endFrame2);
        // play animation
        drawFromDeckTimeline.play();
    }

    private int calculateScoreGain(ArrayList<Card> losingHand){
        int scoreGain = 0;
        for(Card cardTemp : losingHand){
            scoreGain += calculateScoreOfACard(cardTemp);
        }

        return scoreGain;
    }

    private int calculateScoreOfACard(Card card){
        int score;
        if(lightSide){
            score = card.getLightValue().getPoints();
            if(card.getLightValue() == LightValue.WILD && Settings.getGameMode() == GameMode.UNO_FLIP){
                score = 40;
            }
        }else{
            score = card.getDarkValue().getPoints();
        }

        return score;
    }

    private void updateScore(int score){
        int winIndex = players.indexOf(currentPlayer);
        Text scoreToUpdate = scoresText.get(winIndex);
        scorePane.getChildren().remove(scoreToUpdate);

        currentPlayer.addScore(score);
        scoreToUpdate.setText(String.valueOf(currentPlayer.getScore()));

        scorePane.getChildren().add(scoreToUpdate);
    }

    private void finalCheckingAction(){
        KeyFrame finalFrame = new KeyFrame(Duration.millis(500),
                e -> {
                    // MORE STUFF HERE LATER
                    String winnerStr;

//                    saveLogCheck.setDisable(false);

                    if((Settings.isWinByScore() && currentPlayer.getScore() >= Settings.getScoreLimit()) ||
                        (!Settings.isWinByScore() && currentPlayer.getScore() >= Settings.getFirstToN())){
                        playAgainBtn.setVisible(true);
                        backToMenuBtn.setVisible(true);

                        drawFinalWinnerGlow();

                        winnerStr = currentPlayer.getName() + " won the entire game!";
                    }else{
                        continueBtn.setVisible(true);

                        winnerStr = currentPlayer.getName() + " won this round!";
                    }

//                    if(currentPlayer.getScore() >= Settings.getScoreLimit()){
//                        playAgainBtn.setVisible(true);
//                        backToMenuBtn.setVisible(true);
//                    }else{
//                        continueBtn.setVisible(true);
//                    }
                    toggleScoreboardBtn.setVisible(true);

                    Text winnerText = new Text(10, 290, winnerStr);
                    winnerText.setFont(Font.font("Verdana", 20));
//                    winnerText.setStroke(Color.WHITE);
//                    winnerText.setStrokeWidth(1);
                    scorePane.getChildren().add(winnerText);
                });

        Timeline finalTimeline = new Timeline(finalFrame);
        finalTimeline.play();
    }

    private void drawFinalWinnerGlow(){
        int x = 290 - (5 + 115 * noOfPlayers) / 2 + 115 * players.indexOf(currentPlayer) + 10;
        int y = 110;
        ImageView finalWinnerGlowIV = new ImageView(App.getResourceImg(effectDir + winnerGlow));
        finalWinnerGlowIV.setTranslateX(x - glowOffset * 2);
        finalWinnerGlowIV.setTranslateY(y - glowOffset * 2);
        scorePane.getChildren().remove(winnerGlowIV);
        scorePane.getChildren().add(finalWinnerGlowIV);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // UTILITIES
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void setPunishments(boolean goingToApplyPunishment, int noOfPlayersToSkip, int noOfCardsToDraw){
        this.goingToApplyPunishment = goingToApplyPunishment;
        this.noOfPlayersToSkip = noOfPlayersToSkip;
        this.noOfCardsToDraw = noOfCardsToDraw;
    }

    private void oneMorePlayerToSkip(){
        noOfPlayersToSkip++;
    }

    private void stackCardsToDraw(int noOfExtraCardsToDraw){
        noOfCardsToDraw += noOfExtraCardsToDraw;
    }

    // reverse a stack of cards
    private void reverseCardPile(Stack<Card> pile){
        Queue<Card> cardQueueTemp = new LinkedList<>();
        while(!pile.empty()){
            cardQueueTemp.add(pile.pop());
        }
        while(!cardQueueTemp.isEmpty()){
            pile.push(cardQueueTemp.remove());
        }
    }

    // switch between light (true) and dark side (false)
    private void switchSide() {
        lightSide = !lightSide;
        if(lightSide){
            gameHistory.appendText("Turned to Light Side.\n");
        }else{
            gameHistory.appendText("Turned to Dark Side.\n");
        }
    }

    // switch between viewing your hand from the front and back
    void switchViewSide(){
        viewingFront = !viewingFront;
    }

    boolean isViewingFront() {
        return viewingFront;
    }

    // switch between sorting by color (true) and value (false)
    private void doSwitchSortMethod(){
        sortByColor = !sortByColor;
    }

    // set you in this controller for simpler functions
    void setYou(Player you) {
        this.you = you;
    }

    boolean isLightSide() {
        return lightSide;
    }

    public boolean isLockingUserAction() {
        return lockingUserAction;
    }

    void setLockingUserAction(boolean lockingUserAction) {
        this.lockingUserAction = lockingUserAction;
    }

//    void setCurrentPlayer(Player currentPlayer) {
//        this.currentPlayer = currentPlayer;
//    }

    boolean isYouPlayedACard() {
        return youPlayedACard;
    }

    boolean isGameEnd() {
        return gameEnd;
    }

    //    public void setDealPlayerIndex(int dealPlayerIndex) {
//        this.dealPlayerIndex = dealPlayerIndex;
//    }
//
//    public void setDealRound(int dealRound) {
//        this.dealRound = dealRound;
//    }

    // test
//        Card testCard = new Card(Card.LightColor.WILD, Card.LightValue.WILD_DRAW_FOUR, Card.DarkColor.DARK_WILD, Card.DarkValue.D_WILD_DRAW);
//        Card testCard2 = new Card(Card.LightColor.RED, Card.LightValue.REVERSE, Card.DarkColor.AMETHYST, Card.DarkValue.SKIP_ALL);
//        Card testCard3 = new Card(Card.LightColor.GREEN, Card.LightValue.DRAW_ONE, Card.DarkColor.AMBER, Card.DarkValue.DRAW_FIVE);
//        cardController.drawCard(testCard, 600, 300, true);
//        cardController.drawCard(testCard2, 622, 300, true);
//        cardController.drawCard(testCard3, 644, 300, true);
//        cardController.addUpDownAnimation(testCard, 600, 300);
//        cardController.addUpDownAnimation(testCard2, 622, 300);
//        cardController.addUpDownAnimation(testCard3, 644, 300);
//        cardController.addCardClickEvent(testCard);
//        cardController.addCardClickEvent(testCard2);
//        cardController.addCardClickEvent(testCard3);
    // testing, draw all cards from a deck
//    public void drawWholeDeck(){
//        // move cards from stack to array list for easy iteration
//        ArrayList<Card> cardsToTest = new ArrayList<>();
//        while(!deck.empty()){
//            cardsToTest.add(deck.pop());
//        }
//
//        // draw the cards along with animation and click events
//        for(int i = 0; i < cardsToTest.size(); i++) {
//            Card card = cardsToTest.get(i);
//            int x = 300 + i % 7 * 30;
//            int y = 100 + 30 * (i / 7);
//
//            drawCard(card, x, y, true);
//            addUpDownAnimation(card, x, y);
//            addCardClickEvent(card);
//        }
//    }

    // draw cards from all players' hand
//    public void drawCardsFromPlayers(ArrayList<Player> players){
//        for(Player playerTemp : players){
//            int handX = playerTemp.getHandX();
//            int handY = playerTemp.getHandY();
//            boolean you = playerTemp.isYou();
//            ArrayList<Card> handTemp = playerTemp.getHand();
//            for(int i = 0; i < handTemp.size(); i++){
//                Card cardTemp = handTemp.get(i);
//                int x = handX + i % cardsPerRow * cardGap;
//                int y = handY + cardGap * (i / cardsPerRow);
//
//                if(you){
//                    drawCard(cardTemp, x, y, true);
//                    addUpDownAnimation(cardTemp, x, y);
////                    addCardClickEvent(cardTemp);
//                }else{
//                    drawCard(cardTemp, x, y, false);
//                }
//            }
//        }
//    }
//    public void viewBackOfMyHand(){
//        // get my hand and its starting position
//        ArrayList<Card> myHand = you.getHand();
//        int handX = you.getHandX();
//        int handY = you.getHandY();
//        // clear old and draw new for each card in my hand
//        for(int i = 0; i < myHand.size(); i++){
//            Card cardTemp = myHand.get(i);
//            removeCardFromGamePane(cardTemp);
//
//            int x = handX + i % cardsPerRow * cardGap;
//            int y = handY + cardGap * (i / cardsPerRow);
//
//            drawCard(cardTemp, x, y, false);
//            // add moving animation
//            addUpDownAnimation(cardTemp, x, y);
////            addCardPlayEvent(cardTemp, x, y);
//        }
//        removeDeckDrawEvent();
//    }

    // re-add click events after animation finished without the need of drawing cards again
    // this is to avoid moved-up card from resetting its position
//    private void reAddCardPlayEventWholeHand(){
//        // get my hand and its starting position
//        ArrayList<Card> myHand = you.getHand();
//        int handX = you.getHandX();
//        int handY = you.getHandY();
//        // re-add click event for each card in my hand
//        for(int i = 0; i < myHand.size(); i++){
//            Card cardTemp = myHand.get(i);
//
//            int x = handX + i % cardsPerRow * cardGap;
//            int y = handY + cardGap * (i / cardsPerRow);
//
//            // handle card click again
//            addCardPlayEvent(cardTemp, x, y);
//        }
//    }

    // addcardplayevent_old
//                sortHandBtn.setDisable(true);
//                viewOtherSideBtn.setDisable(true);
////            removeCardPlayEventWholeHand(myHand);
//                playingACard = true;
//                // temporarily remove click event for the deck,
//                // to prevent drawing before moving animation ends
////                removeDeckDrawEvent(); ???
//                // remove card's elements shown in pane
//                removeCardFromGamePane(cardTemp);
//                // add that card to the discard pile
//                discardPile.push(cardTemp);
//                // remove that card from your hand
//                myHand.remove(cardTemp);
//
//                // redraw my hand and add up/down animation,
//                // click event is removed right after
//                drawMyHand();
//                // temporarily remove click event for all cards in your hand
//                // to prevent playing multiple cards before moving animation ends
////                removeCardPlayEventWholeHand(myHand); ???
//
//                addGameHistoryWhenPlayCard(you, cardTemp);
//
//                // add animation where that card moves from your hand to the discard pile
//                playedCardAnimation(cardTemp, x, y - cardHeight);

// remove event when you click a card in your hand
//    private void removeCardPlayEventWholeHand(ArrayList<Card> myHand){
//        for(Card cardTemp : myHand){
//            cardTemp.getInvisibleCard().setOnMouseClicked(e -> {});
//        }
//    }

//    private void removeDownAnimation(Card card){
//        card.getInvisibleCard().setOnMouseExited(e -> {});
//    }

    // adddeckdrawevent_old
    //                // temporarily remove click event for all cards in your hand
//                // to prevent playing multiple cards before moving animation ends
////                removeCardPlayEventWholeHand(myHand); ???
//                // remove card's elements shown in pane
//                removeCardFromGamePane(topCardOfDeck);
//                // take the card on top of the deck to your hand
//                myHand.add(deck.pop());
//
//                // redraw the deck,
//                // click event is removed right after
//                drawDeck();
//                // temporarily remove click event for the deck
//                // to prevent drawing multiple cards before moving animation ends
////                removeDeckDrawEvent(); ???
//
//                // add animation where that card moves from the deck to your hand
//                drawFromDeckAnimation(topCardOfDeck);

    // remove event when you click the deck
//    private void removeDeckDrawEvent(){
//        if(!deck.empty()){
//            Card topCardOfDeck = deck.peek();
//            topCardOfDeck.getInvisibleCard().setOnMouseClicked(e -> {});
//        }
//    }

    //    public void addCardClickEvent(Card card){
//        card.getInvisibleCard().setOnMouseClicked(e -> {
//            removeCardFromGamePane(card);
//        });
//    }

    // playedcardanimation_old
    //                    if(!lightSide && discardPile.peek().getDarkColor() == DarkColor.DARK_WILD){
//                        gamePane.getChildren().add(darkBtnGroup);
//                    }else{
//                        turnEngine.nextTurn();
//                        turnEngine.drawAvatarGlow();
//                    }

    //                    // remove that temporary card from pane
//                    removeCardFromGamePane(card);
//                    // re-add click events,
//                    // so you can play a card again after animation finished
////                    reAddCardPlayEventWholeHand(); ???
//                    playingACard = false;
////                    drawMyHand();
//                    // draw top card from discard pile,
//                    // which is the card used for animation just before
//                    drawDiscardPile();
//                    // redraw the deck with click event
////                    drawDeck(); ???


    // drawfromdeckanimatin_old
    //                    // remove that temporary card from pane
//                    removeCardFromGamePane(card);
//                    // re-add click events,
//                    // so you can play a card again after animation finished
////                    reAddCardPlayEventWholeHand(); ???
//                    drawingACard = false;
//                    // draw new card
//                    drawCard(card, endX, endY, true);
//                    // add moving animation and handle card click for the new card
//                    addUpDownAnimation(card, endX, endY);
//                    addCardPlayEvent(card, endX, endY);
////                    drawMyHand();
//                    // redraw the deck with click event
////                    drawDeck(); ???
////                    drawDiscardPile();
//
////                    if(PlayedCards.getPlayedCards().peek().getColor() == Color.BLACK && TurnSystem.getTurn() == 0){
////                        ChangeColorBtns.drawChangeColorBtns(pane);
////                    }
//                    // MAY ENABLE SOME BUTTONS LATER
//                    // you can click the sort button again after animation finished
//                    sortHandBtn.setDisable(false);
//                    viewOtherSideBtn.setDisable(false);
//                    // keep the sort method after drawing
//                    switchSortMethod = false;
    // animation where the card comes from the deck to the player's hand
//    private void botDrawFromDeckAnimation(Card card){
//        // draw card that was from the top of the deck,
//        // mostly from the drawn card so it acts as a temporary animation
//        drawCard(card, deckX, deckY, false);
//        Group visibleCardTemp = card.getVisibleCard();
//        Rectangle invisibleCardTemp = card.getInvisibleCard();
//
//        // calculate destination base on the size of bot's hand
//        int botHandSizeBefore = currentPlayer.getHand().size() - 1;
//        int endX = currentPlayer.getHandX() + botHandSizeBefore % cardsPerRow * cardGap;
//        int endY = currentPlayer.getHandY() + cardGap * (botHandSizeBefore / cardsPerRow);
//
//        // destination of the card
//        KeyValue endValue1 = new KeyValue(visibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
//        KeyValue endValue2 = new KeyValue(visibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);
//        KeyValue endValue3 = new KeyValue(invisibleCardTemp.translateXProperty(), endX, Interpolator.EASE_BOTH);
//        KeyValue endValue4 = new KeyValue(invisibleCardTemp.translateYProperty(), endY, Interpolator.EASE_BOTH);
//
//        // animation lasts 250ms
//        KeyFrame endFrame1 = new KeyFrame(Duration.millis(250), endValue1, endValue2, endValue3, endValue4);
//        // handle event after animation finished
//        KeyFrame endFrame2 = new KeyFrame(Duration.millis(250),
//                e -> {
//                    // remove that temporary card from pane
//                    removeCardFromGamePane(card);
//                    // redraw bot's hand
//                    drawBotsHand(currentPlayer, false);
//                });
//        Timeline drawFromDeckTimeline = new Timeline(endFrame1, endFrame2);
//        // play animation
//        drawFromDeckTimeline.play();
//    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
// animation where the card comes from a bot's hand to the discard pile
//    private void botPlayedCardAnimation(Card card, int x, int y){
//        // draw card chosen in bot's hand, mostly from the removed card so it acts as a temporary animation
//        drawCard(card, x, y, true);
//        Group visibleCardTemp = card.getVisibleCard();
//        Rectangle invisibleCardTemp = card.getInvisibleCard();
//
//        // destination of the card (discard pile)
//        KeyValue endValue1 = new KeyValue(visibleCardTemp.translateXProperty(), discardX, Interpolator.EASE_BOTH);
//        KeyValue endValue2 = new KeyValue(visibleCardTemp.translateYProperty(), discardY, Interpolator.EASE_BOTH);
//        KeyValue endValue3 = new KeyValue(invisibleCardTemp.translateXProperty(), discardX, Interpolator.EASE_BOTH);
//        KeyValue endValue4 = new KeyValue(invisibleCardTemp.translateYProperty(), discardY, Interpolator.EASE_BOTH);
//
//        // animation lasts 250ms
//        KeyFrame endFrame1 = new KeyFrame(Duration.millis(250), endValue1, endValue2, endValue3, endValue4);
//        // handle event after animation finished
//        KeyFrame endFrame2 = new KeyFrame(Duration.millis(250),
//                e -> {
//                    // remove that temporary card from pane
//                    removeCardFromGamePane(card);
//
//                    // draw the top card in discard pile,
//                    // which is the card used for animation just before
//                    drawDiscardPile();
//
//                    // if a flip card is played
//                    if((lightSide && discardPile.peek().getLightValue() == Card.LightValue.FLIP) ||
//                            (!lightSide && discardPile.peek().getDarkValue() == Card.DarkValue.D_FLIP)){
//                        // switch side (light <=> dark)
//                        switchSide();
//
//                        // draw players' hand again in new orientation
//                        // and shuffle all bots' hands
//                        redrawPlayersHand();
//
//                        // remove card image before flipping to avoid duplicate children,
//                        // then reverse the pile and redraw it
//                        removeCardFromGamePane(discardPile.peek());
//                        reverseCardPile(discardPile);
//                        drawDiscardPile();
//
//                        removeCardFromGamePane(deck.peek());
//                        reverseCardPile(deck);
//                        drawDeck();
//                        // keep sort method after flipping
//                        switchSortMethod = false;
//                    }else if((lightSide && discardPile.peek().getLightValue() == LightValue.REVERSE) ||
//                            (!lightSide && discardPile.peek().getDarkValue() == DarkValue.D_REVERSE)){
//                        switchDirection();
//                        addGameHistoryWhenReverse();
//                    }
//                });
//        Timeline botPlayedCardTimeline = new Timeline(endFrame1, endFrame2);
//        // play animation
//        botPlayedCardTimeline.play();
//    }

}
