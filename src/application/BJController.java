package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.layout.GridPane;
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
		private int getNumber() {
			return number;
		}
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane base_pane;

    @FXML
    private GridPane player_grid;

    @FXML
    private GridPane enemy_grid;

    @FXML
    private Button add_button;

    @FXML
    private Button stand_button;

    @FXML
    private Label playerpoint_label;

    @FXML
    private Label enemypoint_label;

    @FXML
    private Button return_button;

    @FXML
    void initialize() {
        assert base_pane != null : "fx:id=\"base_pane\" was not injected: check your FXML file 'BlackJack.fxml'.";
        assert player_grid != null : "fx:id=\"player_grid\" was not injected: check your FXML file 'BlackJack.fxml'.";
        assert enemy_grid != null : "fx:id=\"enemy_grid\" was not injected: check your FXML file 'BlackJack.fxml'.";
        assert add_button != null : "fx:id=\"add_button\" was not injected: check your FXML file 'BlackJack.fxml'.";
        assert stand_button != null : "fx:id=\"stand_button\" was not injected: check your FXML file 'BlackJack.fxml'.";
        assert playerpoint_label != null : "fx:id=\"playerpoint_label\" was not injected: check your FXML file 'BlackJack.fxml'.";
        assert enemypoint_label != null : "fx:id=\"enemypoint_label\" was not injected: check your FXML file 'BlackJack.fxml'.";
        assert return_button != null : "fx:id=\"return_button\" was not injected: check your FXML file 'BlackJack.fxml'.";

        for(int i = 0 ; i < 4 ; i++) {
        	for(int j = 1 ; j < 14 ; j++) {
        		cardList.add(new Card(j));
        	}
        }

        Collections.shuffle(cardList);
        add_button.setOnAction(e -> {
        	player_grid.add(add_button, 0, 0);
        });
    }


}
