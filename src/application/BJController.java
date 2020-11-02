package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BJController {

	ArrayList<Card> cardList = new ArrayList<>();

	private static class Card extends Label{

		private final int number;
		private static final int CARD_HEIGHT = 90;
		private static final int CARD_WEIGHT = 60;

		private Card(int number) {
			super("" + number);
			this.number = number;
			setPrefSize(CARD_WEIGHT, CARD_HEIGHT);
			setFont(new Font(30));
			setPadding(new Insets(0,0,0,0));
			setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY)));
			setAlignment(Pos.CENTER);
		}
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane base_pane;

    @FXML
    void initialize() {
        assert base_pane != null : "fx:id=\"base_pane\" was not injected: check your FXML file 'BlackJack.fxml'.";
        for(int i = 0 ; i < 4 ; i++) {
        	for(int j = 1 ; j < 14 ; j++) {
        		cardList.add(new Card(j));
        	}
        }

       //とりあえず一枚出力してみた
        AnchorPane.setBottomAnchor(cardList.get(0), 40.0);
    	AnchorPane.setRightAnchor(cardList.get(0), 70.0);
        base_pane.getChildren().add(cardList.get(0));
    }
}
