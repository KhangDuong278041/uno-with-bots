package com.jungko.controller;

import com.jungko.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class QuitGameController {

    @FXML private Button confirmQuitBtn;
    @FXML private Button cancelQuitBtn;

    private Scene owningScene;

    // get owning scene
    public void setOwningScene(Scene owningScene){
        this.owningScene = owningScene;
    }

    @FXML
    private void doConfirmQuit() throws IOException {
        System.out.println("Quit from game");
//        Parent root = FXMLLoader.load(getClass().getResource("../view/main-menu.fxml"));
//        owningScene.setRoot(root);
        App.setRoot("/com/jungko/fxml/main-menu");

        System.out.println("Close popup");
        Stage stage = (Stage) confirmQuitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void doCancelQuit(){
        System.out.println("Close popup");
        Stage stage = (Stage) cancelQuitBtn.getScene().getWindow();
        stage.close();
    }
}
