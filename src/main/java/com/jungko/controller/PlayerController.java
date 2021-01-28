//package com.jungko.controller;
//
//import com.jungko.App;
//import javafx.scene.Group;
//import javafx.scene.control.Tooltip;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Line;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.util.Duration;
//import com.jungko.model.Player;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//public class PlayerController {
//    private Pane gamePane;
//
//    private final int avatarSize = 100;
//
//    private final String avatarsDir = "/com/jungko/avatars/";
//
//    private final String avatarHuman = "avatar_human.png";
//
//    private final String avatarRobotBlack = "avatar_robot_black.png";
//    private final String avatarRobotRed = "avatar_robot_red.png";
//    private final String avatarRobotBlue = "avatar_robot_blue.png";
//    private final String avatarRobotGreen = "avatar_robot_green.png";
//    private final String avatarRobotYellow = "avatar_robot_yellow.png";
//    private final String avatarRobotRuby = "avatar_robot_garnet.png";
//    private final String avatarRobotAmethyst = "avatar_robot_amethyst.png";
//    private final String avatarRobotEmerald = "avatar_robot_emerald.png";
//    private final String avatarRobotAmber = "avatar_robot_amber.png";
//    private final String avatarRobotMoon = "avatar_robot_moon.png";
//
//    private final String avatarBorder = "avatar_border.png";
//
//    public PlayerController(Pane gamePane){
//        this.gamePane = gamePane;
//    }
//
//    public ArrayList<Player> createPlayers(int noOfPlayers, boolean includeYou){
//        ArrayList<Player> players = new ArrayList<>();
//
//        // robots' names
//        ArrayList<String> robotsNames = new ArrayList<>();
//        robotsNames.add("A-Bot");
//        robotsNames.add("B-Bot");
//        robotsNames.add("C-Bot");
//        robotsNames.add("D-Bot");
//        robotsNames.add("E-Bot");
//
//        // shuffle robots' avatars
//        ArrayList<String> robotsList = new ArrayList<>();
//        robotsList.add(avatarRobotBlack);
//        robotsList.add(avatarRobotRed);
//        robotsList.add(avatarRobotBlue);
//        robotsList.add(avatarRobotGreen);
//        robotsList.add(avatarRobotYellow);
//        robotsList.add(avatarRobotRuby);
//        robotsList.add(avatarRobotAmethyst);
//        robotsList.add(avatarRobotEmerald);
//        robotsList.add(avatarRobotAmber);
//        robotsList.add(avatarRobotMoon);
//        Collections.shuffle(robotsList);
//
//        if(includeYou){
//            // add you as first slot
//            String yourName = "Player";
//            players.add(new Player(1, yourName, new ImageView(new Image(App.getResourceImg(avatarsDir + avatarHuman),
//                    avatarSize, avatarSize, false, true)), new ArrayList<>(), 0, true));
//        }else{
//            // add robot as first slot
//            players.add(new Player(1, robotsNames.get(4), new ImageView(App.getResourceImg(avatarsDir + robotsList.get(4))), new ArrayList<>(), 0, false));
//        }
//        players.get(0).setPosition(330, 606, 450, 546);
//
//        // add robots to remaining slots
//        for (int i = 0; i < noOfPlayers - 1; i++){
//            players.add(new Player(i + 2, robotsNames.get(i), new ImageView(App.getResourceImg(avatarsDir + robotsList.get(i))), new ArrayList<>(), 0, false));
//        }
//        switch (noOfPlayers){
//            case 2:
//                players.get(1).setPosition(980, 49, 684, 14);
//                break;
//            case 3:
//                players.get(1).setPosition(330, 49, 450, 14);
//                players.get(2).setPosition(990, 176, 990, 296);
//                break;
//            case 4:
//                players.get(0).setPosition(514, 606, 634, 546);
//                players.get(1).setPosition(228, 176, 228, 296);
//                players.get(2).setPosition(940, 49, 634, 14);
//                players.get(3).setPosition(1166, 176, 990, 296);
//                break;
//            case 5:
//                players.get(0).setPosition(514, 606, 634, 546);
//                players.get(1).setPosition(228, 236, 228, 356);
//                players.get(2).setPosition(300, 49, 420, 14);
//                players.get(3).setPosition(1094, 49, 798, 14);
//                players.get(4).setPosition(1166, 236, 990, 356);
//                break;
//            default:
//                break;
//        }
//
//        return players;
//    }
//
//    public void drawAllPlayers(ArrayList<Player> players){
//        for (Player playerTemp : players) {
//            // avatar's location
//            int x = playerTemp.getX();
//            int y = playerTemp.getY();
//
//            // set avatar location
//            ImageView playerAvatarTemp = playerTemp.getAvatar();
//            playerAvatarTemp.setTranslateX(x);
//            playerAvatarTemp.setTranslateY(y);
//
//            Tooltip tooltipTemp = new Tooltip("Name: " + playerTemp.getName() + "\nScore: " + playerTemp.getScore());
//            tooltipTemp.setShowDelay(Duration.millis(200));
//            Tooltip.install(playerAvatarTemp, tooltipTemp);
//
//            // create avatar border
////            Line line1 = new Line(x, y, x + avatarSize, y);
////            Line line2 = new Line(x + avatarSize, y, x + avatarSize, y + avatarSize);
////            Line line3 = new Line(x + avatarSize, y + avatarSize, x, y + avatarSize);
////            Line line4 = new Line(x, y + avatarSize, x, y);
////            Group border = new Group(line1, line2, line3, line4);
//            ImageView border = new ImageView(App.getResourceImg(avatarsDir + avatarBorder));
//            border.setTranslateX(x);
//            border.setTranslateY(y);
//
//            // create player name tag
//            Rectangle nameBg = new Rectangle(x - 5, y - 35, 110, 30);
//            nameBg.setFill(Color.WHEAT);
//            Text playerNameTemp = new Text(x, y - 22, playerTemp.getName());
//            playerNameTemp.setFont(new Font("Verdana", 10));
//            playerNameTemp.setWrappingWidth(100);
////            playerNameTemp.setTextAlignment(TextAlignment.JUSTIFY);
//
//            // add avatar and border to pane
//            gamePane.getChildren().addAll(playerAvatarTemp, border, nameBg, playerNameTemp);
//        }
//    }
//}
