package com.jungko.controller;

import com.jungko.App;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.jungko.model.Card;
import com.jungko.model.Card.DarkColor;
import com.jungko.model.Card.LightColor;
import com.jungko.model.Player;
import com.jungko.model.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class GameScreenController {
    private int[] currentScores;
    private int[] botAvatarIndexes;

    @FXML private Pane gamePane;
    @FXML private Pane scorePane;
    private GameLogic gameLogic;
//    private PlayerController playerController;

    @FXML private TextArea gameHistory;
    @FXML private TextArea settingsSummary;
    @FXML private Button quitGameBtn;
    @FXML private Button sortHandBtn;
    @FXML private Button viewOtherSideBtn;
    @FXML private Button endTurnBtn;
    @FXML private Button acceptBtn;

    @FXML private Button redBtn;
    @FXML private Button blueBtn;
    @FXML private Button greenBtn;
    @FXML private Button yellowBtn;
    private Group lightBtnGroup;
    @FXML private Button garnetBtn;
    @FXML private Button amethystBtn;
    @FXML private Button emeraldBtn;
    @FXML private Button amberBtn;
    private Group darkBtnGroup;

    @FXML private CheckBox saveLogCheck;
    @FXML private Button continueBtn;
    @FXML private Button toggleScoreboardBtn;
    @FXML private Button playAgainBtn;
    @FXML private Button backToMenuBtn;

    private Stack<Card> deck;
    private Stack<Card> discardPile;
    private ArrayList<Player> players;

//    private final int cardsPerPlayer = 3;
//    private boolean lightSide; // ???


    void setCurrentScores(int[] currentScores) {
        this.currentScores = currentScores;
    }

    void setBotAvatarIndexes(int[] botAvatarIndexes) {
        this.botAvatarIndexes = botAvatarIndexes;
    }

    void setupPlayers(){
        System.out.println(Arrays.toString(currentScores));
        System.out.println(Arrays.toString(botAvatarIndexes));
        // create correct number of players
        int noOfPlayers = Settings.getNumberOfPlayers();
        if (noOfPlayers >= 2 && noOfPlayers <= 5){
            players = gameLogic.createPlayers(noOfPlayers, currentScores, botAvatarIndexes, true);
        } else {
            players = gameLogic.createPlayers(4, currentScores, botAvatarIndexes, true);
        }
        // draw all created players
        gameLogic.drawAllPlayers(players);
        //
        gameLogic.setPlayers(players);

        if(Settings.isSevenSwap()){
            gameLogic.setupSevenSwapBtn();
        }

        gameLogic.drawAvatarGlow();
        gameLogic.drawDirection();

        for(Player player : players){
            if(player.isYou()){
                gameLogic.setYou(player); // DO STH ABOUT "YOU" LATER
            }
        }

        gameLogic.setupBeforeDealingCards();
    }

    @FXML
    public void initialize(){
        redBtn.setPickOnBounds(false);
        blueBtn.setPickOnBounds(false);
        greenBtn.setPickOnBounds(false);
        yellowBtn.setPickOnBounds(false);
        lightBtnGroup = new Group(redBtn, blueBtn, greenBtn, yellowBtn);
        gamePane.getChildren().remove(lightBtnGroup);

        garnetBtn.setPickOnBounds(false);
        amethystBtn.setPickOnBounds(false);
        emeraldBtn.setPickOnBounds(false);
        amberBtn.setPickOnBounds(false);
        darkBtnGroup = new Group(garnetBtn, amethystBtn, emeraldBtn, amberBtn);
        gamePane.getChildren().remove(darkBtnGroup);

        sortHandBtn.setDisable(true);
        viewOtherSideBtn.setDisable(true);
        endTurnBtn.setDisable(true);

        // initialize deck and discard pile
        deck = new Stack<>();
        discardPile = new Stack<>();

        // initialize game logic and player controller
        gameLogic = new GameLogic(gamePane, scorePane, deck, discardPile, gameHistory);
        gameLogic.setButtons(sortHandBtn, viewOtherSideBtn, endTurnBtn, lightBtnGroup, darkBtnGroup,
                saveLogCheck, continueBtn, toggleScoreboardBtn, playAgainBtn, backToMenuBtn,
                acceptBtn);
//        playerController = new PlayerController(gamePane);

        // write settings
        settingsSummary.appendText("Game mode: " + Settings.getGameMode().getCustomString() + "\n");
        settingsSummary.appendText("Number of players: " + Settings.getNumberOfPlayers() + "\n");
        settingsSummary.appendText("Cards per player: " + Settings.getCardsPerPlayer() + "\n");
        settingsSummary.appendText("Multi-card: " + writeOnOrOff(Settings.isMultipleCards()) + "\n");
        settingsSummary.appendText("Stacking: " + writeOnOrOff(Settings.isStacking()) + "\n");
        settingsSummary.appendText("Draw to match: " + writeOnOrOff(Settings.isDrawToMatch()) + "\n");
        settingsSummary.appendText("Force play: " + writeOnOrOff(Settings.isForcePlay()) + "\n");
        settingsSummary.appendText("7-swap: " + writeOnOrOff(Settings.isSevenSwap()) + "\n");
        settingsSummary.appendText("0-rotate: " + writeOnOrOff(Settings.isZeroRotate()) + "\n");
        // to be added

        // create deck base on game mode
        switch (Settings.getGameMode()){
            case UNO:
                System.out.println("Create uno deck");
                gameLogic.createUnoDeck();
                break;
            case UNO_FLIP:
                System.out.println("Create uno flip deck");
                gameLogic.createUnoFlipDeck();
                break;
            default:
                break;
        }

//        // create correct number of players
//        int noOfPlayers = Settings.getNumberOfPlayers();
//        if (noOfPlayers >= 2 && noOfPlayers <= 5){
//            players = gameLogic.createPlayers(noOfPlayers, currentScores, botAvatarIndexes, true);
//        } else {
//            players = gameLogic.createPlayers(4, currentScores, botAvatarIndexes, true);
//        }
//        // draw all created players
//        gameLogic.drawAllPlayers(players);
//        //
//        gameLogic.setPlayers(players);
//
//        gameLogic.drawAvatarGlow();
//        gameLogic.drawDirection();
//
//        for(Player player : players){
//            if(player.isYou()){
//                gameLogic.setYou(player); // DO STH ABOUT "YOU" LATER
//            }
//        }
//
//        gameLogic.setupBeforeDealingCards();

//        // DRAW THE DECK BEFORE DEALING
//        gameLogic.drawDeck();
//        // DRAW ANIMATIONS AS THE CARDS ARE BEING DEALT TO EACH PLAYER
//        gameLogic.setDealPlayerIndex(gameLogic.getTurn());
//        gameLogic.setDealRound(1);
//        gameLogic.dealACardToAPlayerAction();

        // deal cards to players (may add animations)
//        dealCardsToPlayers();
        // draw cards from all players' hand
//        for(Player player : players){
//            if(player.isYou()){
//                gameLogic.setYou(player); // DO STH ABOUT "YOU" LATER
//                gameLogic.drawMyHand();
//            }
//            else{
//                gameLogic.drawBotsHand(player, false);
//            }
//        }
        // AFTER DEALING STAGE, DRAW ANIMATION WHERE THE DECK'S TOP CARD IS MOVED TO THE DISCARD PILE

        // put first card from deck to discard pile (may add animations)
//        discardPile.push(deck.pop());
        // draw first card in discard pile
//        gameLogic.drawDiscardPile();

        // draw top card in deck pile
//        gameLogic.drawDeck();

        // AFTER THE ABOVE ANIMATION IS FINISHED, DO STUFF INSIDE THIS FUNCTION BUT WITHOUT DELAY
//        waitUntilGameStarts();


//        // apply rules if needed (may add animations)
//        // first player can start
//        if(!players.get(turnEngine.getTurn()).isYou()){
//            BotLogic.playTheirTurn(players.get(turnEngine.getTurn()));
//        }else{
//            // show light buttons if first card is a wild card
//            if(discardPile.peek().getLightColor() == Card.LightColor.WILD){
//                gamePane.getChildren().add(lightBtnGroup);
//            }
//        }

//        gamePane.getChildren().add(lightBtnGroup);
    }

    private String writeOnOrOff(boolean settings){
        if(settings){
            return "On";
        }else{
            return "Off";
        }
    }

    // give each player an equal number of cards
//    private void dealCardsToPlayers(){
//        int cardsPerPlayer = Settings.getCardsPerPlayer();
//        for(int i = 0; i < cardsPerPlayer; i++){
//            for(Player playerTemp : players){
//                playerTemp.getHand().add(deck.pop());
//            }
//        }
//    }

    // WILL ADD DEALING CARDS ANIMATION TO THIS
//    private void waitUntilGameStarts(){
//        KeyFrame startFrame = new KeyFrame(Duration.millis(2000),
//                e -> {
//
////                    gameLogic.setLockingUserAction(false);
//
////                    sortHandBtn.setDisable(false);
////                    viewOtherSideBtn.setDisable(false);
//
//                    Card firstCard = discardPile.peek();
//                    gameHistory.appendText("First card is ");
//                    if(gameLogic.isLightSide()){
//                        gameHistory.appendText(firstCard.getLightColor().getCustomString() +
//                                " " + firstCard.getLightValue().getCustomString());
//                    }else{
//                        gameHistory.appendText(firstCard.getDarkColor().getCustomString() +
//                                " " + firstCard.getDarkValue().getCustomString());
//                    }
//                    gameHistory.appendText("\n");
//
//                    // DO THE CHECKING ...
//                    gameLogic.setSmallEffects(firstCard);
//
//                    gameLogic.setCurrentPlayer(players.get(gameLogic.getTurn()));
//
////                    gameLogic.goToNextTurn();
//                    gameLogic.endTurnAction();
////                    turnEngine.nextTurn();
////                    turnEngine.drawAvatarGlow();
////
////                    // apply rules if needed (may add animations)
////                    // first player can start
////                    if(!players.get(turnEngine.getTurn()).isYou()){
////                        BotLogic.playTheirTurn(players.get(turnEngine.getTurn()));
////                    }else{
////                        // show light buttons if first card is a wild card
////                        if(discardPile.peek().getLightColor() == Card.LightColor.WILD){
////                            gamePane.getChildren().add(lightBtnGroup);
////                        }
////                    }
//                });
//
//        Timeline waitTimeline = new Timeline(startFrame);
//        waitTimeline.play();
//    }

    @FXML
    private void doSortHand(){
        gameLogic.sortMyHand();
    }

    @FXML
    private void doViewOtherSide(){
        // switch viewFront boolean
        gameLogic.switchViewSide();

        // switched to front side
        if(gameLogic.isViewingFront()){
            // click to view back side again
            viewOtherSideBtn.setText("View back");
            // can sort or accept when viewing from the front
            sortHandBtn.setDisable(false);
            acceptBtn.setDisable(false);
        // switched to back side
        }else{
            // click to view front side again
            viewOtherSideBtn.setText("View front");
            // no sorting or accepting when viewing from the back
            sortHandBtn.setDisable(true);
            acceptBtn.setDisable(true);
        }
        // draw my hand with correct view
        gameLogic.drawMyHand();
    }

    @FXML
    private void doEndTurn(){
        endTurnBtn.setDisable(true);
        if(!gameLogic.isYouPlayedACard()){
            gameLogic.addGameHistoryWhenCantPlay();
        }
        gameLogic.goToNextTurn();
    }

    @FXML
    private void doAccept(){
        acceptBtn.setVisible(false);
        gameLogic.setLockingUserAction(true);
        gameLogic.initiateDrawNCards();
    }

    private void nextTurnIfYouPlayed(){
        if(gameLogic.isYouPlayedACard()){
            if(!Settings.isMultipleCards()){
                gameLogic.setLockingUserAction(true);
                gameLogic.endTurnAction();
            }else{
                if(!gameLogic.isGameEnd()){
                    endTurnBtn.setDisable(false);
                }else{
                    gameLogic.endTurnAction();
                }
            }
//            gameEngine.goToNextTurn();
        }
    }

    // update history and change color of top card in discard pile, then remove the correct group of buttons
    @FXML
    private void doChangeToRed(){
        gameLogic.addGameHistoryWhenChangeColor("Red"); // players.get(gameEngine.getTurn()), ???
        gameLogic.changeLightColorOfTopCard(LightColor.RED);
        gamePane.getChildren().remove(lightBtnGroup);
        nextTurnIfYouPlayed();
    }

    @FXML
    private void doChangeToBlue(){
//        gameHistory.appendText("Color has been changed to Blue.\n");
        gameLogic.addGameHistoryWhenChangeColor("Blue");
        gameLogic.changeLightColorOfTopCard(LightColor.BLUE);
        gamePane.getChildren().remove(lightBtnGroup);
        nextTurnIfYouPlayed();
    }

    @FXML
    private void doChangeToGreen(){
        gameLogic.addGameHistoryWhenChangeColor("Green");
        gameLogic.changeLightColorOfTopCard(LightColor.GREEN);
        gamePane.getChildren().remove(lightBtnGroup);
        nextTurnIfYouPlayed();
    }

    @FXML
    private void doChangeToYellow(){
        gameLogic.addGameHistoryWhenChangeColor("Yellow");
        gameLogic.changeLightColorOfTopCard(LightColor.YELLOW);
        gamePane.getChildren().remove(lightBtnGroup);
        nextTurnIfYouPlayed();
    }

    @FXML
    private void doChangeToGarnet(){
        gameLogic.addGameHistoryWhenChangeColor("Garnet");
        gameLogic.changeDarkColorOfTopCard(DarkColor.GARNET);
        gamePane.getChildren().remove(darkBtnGroup);
        nextTurnIfYouPlayed();
    }

    @FXML
    private void doChangeToAmethyst(){
        gameLogic.addGameHistoryWhenChangeColor("Amethyst");
        gameLogic.changeDarkColorOfTopCard(DarkColor.AMETHYST);
        gamePane.getChildren().remove(darkBtnGroup);
        nextTurnIfYouPlayed();
    }

    @FXML
    private void doChangeToEmerald(){
        gameLogic.addGameHistoryWhenChangeColor("Emerald");
        gameLogic.changeDarkColorOfTopCard(DarkColor.EMERALD);
        gamePane.getChildren().remove(darkBtnGroup);
        nextTurnIfYouPlayed();
    }

    @FXML
    private void doChangeToAmber(){
        gameLogic.addGameHistoryWhenChangeColor("Amber");
        gameLogic.changeDarkColorOfTopCard(DarkColor.AMBER);
        gamePane.getChildren().remove(darkBtnGroup);
        nextTurnIfYouPlayed();
    }

    @FXML
    private void doContinue() throws IOException {
        if(saveLogCheck.isSelected()){
            System.out.println("Save match log");
        }
        System.out.println("Continue the match");

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/jungko/fxml/game-screen.fxml"));
        Parent root = fxmlLoader.load();

        GameScreenController controller = fxmlLoader.getController();

        for(int i = 0; i < players.size(); i++){
            currentScores[i] = players.get(i).getScore();
        }
        controller.setCurrentScores(currentScores);

        controller.setBotAvatarIndexes(botAvatarIndexes);
        controller.setupPlayers();

        App.setRootWithParentRoot(root);
    }

    @FXML
    private void doToggleScoreboard(){
        if(toggleScoreboardBtn.getText().equals("Hide\nscoreboard")){
            scorePane.setVisible(false);
            toggleScoreboardBtn.setText("Show\nscoreboard");
        }else{
            scorePane.setVisible(true);
            toggleScoreboardBtn.setText("Hide\nscoreboard");
        }
    }

    @FXML
    private void doPlayAgain() throws IOException {
        if(saveLogCheck.isSelected()){
            System.out.println("Save match log");
        }
        System.out.println("Play again");

        MainMenuController.setupNewGame();
    }

    @FXML
    private void doBackToMenu() throws IOException {
        App.setRoot("/com/jungko/fxml/main-menu");
    }

    @FXML
    private void doQuitGame() throws IOException {
        // Show "Are you sure?" popup
        Stage quitPopup = new Stage();
        quitPopup.initModality(Modality.APPLICATION_MODAL);
        quitPopup.setResizable(false);
        quitPopup.setTitle("Quit game?");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jungko/fxml/quit-game.fxml"));
        quitPopup.setScene(new Scene(loader.load(), 400, 300));

        // set scene so that "Yes" button in QuitGameController can change it
        QuitGameController controller = loader.getController();
        controller.setOwningScene(quitGameBtn.getScene()); // MIGHT CHANGE

        System.out.println("Show popup");
        quitPopup.show();
    }


}
