package com.jungko.controller;

import com.jungko.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import com.jungko.model.Settings;
import com.jungko.model.Settings.GameMode;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.function.UnaryOperator;

public class GameSettingsController {

    @FXML private Label warning;

    @FXML private Button confirmBtn;
    @FXML private Button backBtn;

    @FXML private ComboBox<String> gameMode;

    @FXML private ToggleGroup numberOfPlayers;
    @FXML private RadioButton twoPlayers;
    @FXML private RadioButton threePlayers;
    @FXML private RadioButton fourPlayers;
    @FXML private RadioButton fivePlayers;

    @FXML private TextField cardsPerPlayer;

    @FXML private ToggleGroup multiCardMode;
    @FXML private RadioButton multiCardOn;
    @FXML private RadioButton multiCardOff;

    @FXML private ToggleGroup stackingMode;
    @FXML private RadioButton stackingOn;
    @FXML private RadioButton stackingOff;

    @FXML private ToggleGroup drawToMatchMode;
    @FXML private RadioButton drawToMatchOn;
    @FXML private RadioButton drawToMatchOff;

    @FXML private ToggleGroup forcePlayMode;
    @FXML private RadioButton forcePlayOn;
    @FXML private RadioButton forcePlayOff;

    @FXML private ToggleGroup sevenSwapMode;
    @FXML private RadioButton sevenSwapOn;
    @FXML private RadioButton sevenSwapOff;

    @FXML private ToggleGroup zeroRotateMode;
    @FXML private RadioButton zeroRotateOn;
    @FXML private RadioButton zeroRotateOff;

    @FXML private ToggleGroup challengeMode;
    @FXML private RadioButton challengeOn;
    @FXML private RadioButton challengeOff;

    @FXML private ToggleGroup spectateMode;
    @FXML private RadioButton spectateOn;
    @FXML private RadioButton spectateOff;

    @FXML private ToggleGroup twoVsTwoMode;
    @FXML private RadioButton twoVsTwoOn;
    @FXML private RadioButton twoVsTwoOff;

    @FXML private ToggleGroup winCondition;
    @FXML private RadioButton winByScore;
    @FXML private RadioButton winByFirstToN;

    @FXML
    public void initialize(){
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        cardsPerPlayer.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));

        for(GameMode mode : GameMode.values()){
            gameMode.getItems().add(mode.getCustomString());
        }
        // initialize settings from class Settings
        loadSettings();
    }

    private void loadSettings(){
        System.out.println(Settings.getGameMode().getCustomString());
        System.out.println(Settings.getNumberOfPlayers());
        // load values stored in class Settings to elements in scene
        // load game mode
        gameMode.getSelectionModel().select(Settings.getGameMode().getCustomString());
        // load number of players
        switch (Settings.getNumberOfPlayers()){
            case 2:
                twoPlayers.setSelected(true);
                break;
            case 3:
                threePlayers.setSelected(true);
                break;
            case 5:
                fivePlayers.setSelected(true);
                break;
            default:
                fourPlayers.setSelected(true);
                break;
        }
        // load cards per player
        cardsPerPlayer.setText(String.valueOf(Settings.getCardsPerPlayer()));
        // load multi-card mode
        if(Settings.isMultipleCards()){
            multiCardOn.setSelected(true);
        }else{
            multiCardOff.setSelected(true);
        }
        // load stacking mode
        if(Settings.isStacking()){
            stackingOn.setSelected(true);
        }else{
            stackingOff.setSelected(true);
        }
        // load draw to match mode
        if(Settings.isDrawToMatch()){
            drawToMatchOn.setSelected(true);
        }else{
            drawToMatchOff.setSelected(true);
        }
        // load force play mode
        if(Settings.isForcePlay()){
            forcePlayOn.setSelected(true);
        }else{
            forcePlayOff.setSelected(true);
        }
        // load seven-swap mode
        if(Settings.isSevenSwap()){
            sevenSwapOn.setSelected(true);
        }else{
            sevenSwapOff.setSelected(true);
        }
        // load zero-rotate mode
        if(Settings.isZeroRotate()){
            zeroRotateOn.setSelected(true);
        }else{
            zeroRotateOff.setSelected(true);
        }
        // load challenge mode
        if(Settings.isChallenge()){
            challengeOn.setSelected(true);
        }else{
            challengeOff.setSelected(true);
        }
        // load spectate bots mode
        if(Settings.isSpectateBots()){
            spectateOn.setSelected(true);
        }else{
            spectateOff.setSelected(true);
        }
        // load 2v2 mode
        if(Settings.isTwoVsTwo()){
            twoVsTwoOn.setSelected(true);
        }else{
            twoVsTwoOff.setSelected(true);
        }
        // load win condition
        if(Settings.isWinByScore()){
            winByScore.setSelected(true);
        }else{
            winByFirstToN.setSelected(true);
        }

        // to be added
    }

    private void saveSettings(){
        // save values to class Settings from elements in scene
        // save game mode
        for (GameMode mode : GameMode.values()) {
            if (mode.getCustomString().equals(gameMode.getValue())) {
                Settings.setGameMode(mode);
                break;
            }
        }
        // save number of players
        RadioButton selectedNumberOfPlayers = (RadioButton) numberOfPlayers.getSelectedToggle();
        Settings.setNumberOfPlayers(Integer.parseInt(selectedNumberOfPlayers.getText()));
        // save cards per player
        Settings.setCardsPerPlayer(Integer.parseInt(cardsPerPlayer.getText()));
        // save multi-card mode
        if(multiCardOn.isSelected()){
            Settings.setMultipleCards(true);
        }else{
            Settings.setMultipleCards(false);
        }
        // save stacking mode
        if(stackingOn.isSelected()){
            Settings.setStacking(true);
        }else{
            Settings.setStacking(false);
        }
        // save draw to match mode
        if(drawToMatchOn.isSelected()){
            Settings.setDrawToMatch(true);
        }else{
            Settings.setDrawToMatch(false);
        }
        // save force play mode
        if(forcePlayOn.isSelected()){
            Settings.setForcePlay(true);
        }else{
            Settings.setForcePlay(false);
        }
        // save seven-swap mode
        if(sevenSwapOn.isSelected()){
            Settings.setSevenSwap(true);
        }else{
            Settings.setSevenSwap(false);
        }
        // save zero-rotate mode
        if(zeroRotateOn.isSelected()){
            Settings.setZeroRotate(true);
        }else{
            Settings.setZeroRotate(false);
        }
        // save challenge mode
        if(challengeOn.isSelected()){
            Settings.setChallenge(true);
        }else{
            Settings.setChallenge(false);
        }
        // save spectate bots mode
        if(spectateOn.isSelected()){
            Settings.setSpectateBots(true);
        }else{
            Settings.setSpectateBots(false);
        }
        // save 2v2 mode
        if(twoVsTwoOn.isSelected()){
            Settings.setTwoVsTwo(true);
        }else{
            Settings.setTwoVsTwo(false);
        }
        // save win condition
        if(winByScore.isSelected()){
            Settings.setWinByScore(true);
        }else{
            Settings.setWinByScore(false);
        }

        // to be added
    }

    @FXML
    private void confirmSettings() throws IOException {
        // CHECK FIELDS WHEN THERE ARE MORE OPTIONS
        // CHECK CONDITIONS FIRST
        boolean valid = true;

        if(cardsPerPlayer.getText().equals("")){
            warning.setText("Cards per player field is empty.");
            valid = false;
        }else{
            int cardsPerPlayerInt = Integer.parseInt(cardsPerPlayer.getText());
            if(cardsPerPlayerInt < 1 || cardsPerPlayerInt > 10){
                warning.setText("The number of cards per player must be between 1 and 10.");
                valid = false;
            }
        }

        // INVALID: DISPLAY WARNING MESSAGE, DON'T SAVE SETTINGS
        if(!valid){
            System.out.println("Invalid input");
            warning.setVisible(true);
            return;
        }

        // VALID: SAVE SETTINGS AND GO BACK TO MAIN MENU
        System.out.println("Confirm settings");
        saveSettings();
        App.setRoot("/com/jungko/fxml/main-menu");
    }

    @FXML
    private void defaultSettings(){
        System.out.println("Reset to default settings");
        warning.setVisible(false);
        // set elements in scene to default values
        gameMode.getSelectionModel().select(GameMode.UNO.getCustomString());
        fourPlayers.setSelected(true);
        cardsPerPlayer.setText("7");
        multiCardOff.setSelected(true);
        stackingOff.setSelected(true);
        drawToMatchOff.setSelected(true);
        forcePlayOff.setSelected(true);
        sevenSwapOff.setSelected(true);
        zeroRotateOff.setSelected(true);
        challengeOff.setSelected(true);
        spectateOff.setSelected(true);
        twoVsTwoOff.setSelected(true);
        winByScore.setSelected(true);
    }

    @FXML
    private void backToMain() throws IOException {
        System.out.println("Back to main menu");
//        Parent root = FXMLLoader.load(getClass().getResource("../view/main-menu.fxml"));
//        backBtn.getScene().setRoot(root);
        App.setRoot("/com/jungko/fxml/main-menu");
    }
}
