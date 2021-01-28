package com.jungko.controller;

import com.jungko.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import com.jungko.model.Card;
import com.jungko.model.Card.DarkColor;
import com.jungko.model.Card.DarkValue;
import com.jungko.model.Card.LightColor;
import com.jungko.model.Card.LightValue;

import java.io.IOException;
import java.util.*;

public class ViewCardsController {
    @FXML private Pane viewCardsPane;
    @FXML private Button backBtn;
    @FXML private Label info;
    private Stack<Card> deck;
    private ArrayList<Card> cards;
    private Random random = new Random();

    // folder where the cards are in
    private final String cardsDir = "/com/jungko/cards/"; // ERROR WHILE RUNNING BAT ???

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

    @FXML
    public void initialize(){
        viewCardsPane.getChildren().removeAll(backBtn, info);

        deck = new Stack<>();

        // create and shuffle a partial Uno deck
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
                    }
                }
            }
        }

        // create and shuffle a partial Uno Flip deck
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
                        lightSideList.add(new AbstractMap.SimpleEntry<>(lightColor, lightValue)); //
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
                        darkSideList.add(new AbstractMap.SimpleEntry<>(darkColor, darkValue)); //
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

        Collections.shuffle(deck);
        System.out.println(deck.size());

        cards = new ArrayList<>();
        while(!deck.empty()){
            cards.add(deck.pop());
        }

        drawCardsToTest();
        addElementsOnTop();
    }

    private void addElementsOnTop(){
        viewCardsPane.getChildren().addAll(backBtn, info);
    }

    // draw cards randomly
    private void drawCardsToTest(){
        // clear old and draw new for each card in my hand
        for(int i = 0; i < cards.size(); i++){
            Card cardTemp = cards.get(i);
            cardTemp.getVisibleCard().getChildren().clear();
            viewCardsPane.getChildren().remove(cardTemp.getVisibleCard());
            viewCardsPane.getChildren().remove(cardTemp.getInvisibleCard());

            int x = -53 + i % 22 * 66;
            int y = -90 + 100 * (i / 22);

            int num = random.nextInt(3);
            if(num == 0){
                drawCard(cardTemp, x, y, false);
            }else{
                drawCard(cardTemp, x, y, true);
            }
            // handle card click
            addCardClickEvent(cardTemp);
        }
    }

    // draw the card with correct elements, position and orientation
    private void drawCard(Card card, int x, int y, boolean faceUp){
        // load correct images
        ImageView cardColor;
        ImageView cardValue;
        if(faceUp){
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
        viewCardsPane.getChildren().add(visibleCard);
    }

    private void addCardClickEvent(Card card) {
        card.getVisibleCard().setOnMouseClicked(e -> {
            viewCardsPane.getChildren().removeAll(backBtn, info);
            Collections.shuffle(cards);
            drawCardsToTest();
            addElementsOnTop();
        });
    }

    @FXML
    private void backToMain() throws IOException {
        System.out.println("Back to main menu");
//        Parent root = FXMLLoader.load(getClass().getResource("../view/main-menu.fxml"));
//        backBtn.getScene().setRoot(root);
        App.setRoot("/com/jungko/fxml/main-menu");
    }
}
