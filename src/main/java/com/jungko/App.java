package com.jungko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/com/jungko/fxml/main-menu"), 1280, 720);

        stage.setResizable(false);
        stage.getIcons().add(new Image(getResourceImg("/com/jungko/icon/icon_uno.png")));
//        stage.getIcons().add(new Image(App.class.getResourceAsStream("/com/jungko/icon/icon_uno.png")));
        stage.setTitle("Uno with bots");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRootWithParentRoot(Parent root){
        scene.setRoot(root);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
//        System.out.println(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static String getResourceImg(String url) {
        return App.class.getResource(url).toExternalForm();
    }

    public static void main(String[] args) {
        launch();
    }

}