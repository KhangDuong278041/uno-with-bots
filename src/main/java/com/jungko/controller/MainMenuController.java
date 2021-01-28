package com.jungko.controller;

import com.jungko.App;
import com.jungko.model.Settings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainMenuController {

    @FXML private Button toGameBtn;
    @FXML private Button toGameSettingsBtn;
    @FXML private Button toViewCardsBtn;

    @FXML
    private void toGame() throws IOException {
        System.out.println("Go to game");
//        App.setRoot("/com/jungko/fxml/game-screen");

        setupNewGame();
    }

    static void setupNewGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/jungko/fxml/game-screen.fxml"));
        Parent root = fxmlLoader.load();

        GameScreenController controller = fxmlLoader.getController();

        controller.setCurrentScores(new int[Settings.getNumberOfPlayers()]);

        ArrayList<Integer> allBotAvatarIndexes = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            allBotAvatarIndexes.add(i);
        }
        Collections.shuffle(allBotAvatarIndexes);
        int[] botAvatarIndexes = new int[Settings.getNumberOfPlayers()];
        for(int i = 0; i < Settings.getNumberOfPlayers(); i++){
            botAvatarIndexes[i] = allBotAvatarIndexes.get(i);
        }
        controller.setBotAvatarIndexes(botAvatarIndexes);
        controller.setupPlayers();

        App.setRootWithParentRoot(root);
    }

    @FXML
    private void toGameSettings() throws IOException {
        System.out.println("Go to settings");
        App.setRoot("/com/jungko/fxml/game-settings");
    }

    @FXML
    private void toViewCards() throws IOException {
        System.out.println("Go to view cards");
        App.setRoot("/com/jungko/fxml/view-cards");
    }
}
