package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

public class BJController {
	private static final int BJ_MAX_VALUE = 21;
	private static final String WIN_MASSAGE = "かち";
	private static final String LOSE_MASSAGE = "ハァ…ハァ…敗北者…？";

	private ArrayList<Card> cardList = new ArrayList<>();
	private AtomicInteger card_index = new AtomicInteger(0);
	private AtomicInteger player_grid_index = new AtomicInteger(0);
	private AtomicInteger enemy_grid_index = new AtomicInteger(0);
	private int enemy_point;
	private Card enemy_first_card;

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
			return number > 10 ? 10 : number;
		}

		void turnItOver() {
			if(getText().isEmpty()) {
				setText("" + number);
			}else {
				setText("");
			}
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
    private Button restart_button;

    @FXML
    void onClick_return(ActionEvent event) {
    	Scene s = ((Node)event.getSource()).getScene();
		Window window = s.getWindow();
		window.hide();

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("GameList.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("ゲーム選択");
			stage.show();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    void onClick_restart(ActionEvent event) {
    	Scene s = ((Node)event.getSource()).getScene();
		Window window = s.getWindow();
		window.hide();

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("BlackJack.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("ブラックジャック");
			stage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}

    }

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
        assert restart_button != null : "fx:id=\"restart_button\" was not injected: check your FXML file 'BlackJack.fxml'.";

        add_button.setVisible(true);
    	stand_button.setVisible(true);
    	restart_button.setVisible(false);

        for(int i = 0 ; i < 4 ; i++) {
        	for(int j = 1 ; j < 14 ; j++) {
        		cardList.add(new Card(j));
        	}
        }
        Collections.shuffle(cardList);

        add_button.setOnAction(e -> {
        	if(player_grid_index.get() > 7) {return;}
        	Card card = cardList.get(card_index.get());
        	int point = Integer.parseInt(playerpoint_label.getText()) + card.getNumber();
        	playerpoint_label.setText(point + "");
        	player_grid.add(card, player_grid_index.get(), 0);
        	card_index.incrementAndGet();
        	player_grid_index.incrementAndGet();
        	if(point > BJ_MAX_VALUE) {battle();}
        });
        stand_button.setOnAction(e -> {
        	battle();
        });

        cardDistribute();
    }

    //初期配布
    private void cardDistribute() {
    	int sum = 0;
    	enemy_point = 0;

    	for(int i = 0; i < 2; i++) {
    		Card card = cardList.get(card_index.get());
    		sum += card.getNumber();
    		player_grid.add(card, i, 0);
        	card_index.incrementAndGet();
    	}
    	playerpoint_label.setText("" + sum);

    	//敵
    	Card card = cardList.get(card_index.get());
    	card.turnItOver();
    	enemy_first_card = card;
    	enemy_point += card.getNumber();
    	enemy_grid.add(card, 0, 0);

    	card_index.incrementAndGet();
    	card = cardList.get(card_index.get());
    	enemy_point += card.getNumber();
    	enemy_grid.add(card, 1, 0);
    	card_index.incrementAndGet();

    	player_grid_index.set(2);
    	enemy_grid_index.set(2);
    }

    private void enemyTurn() {
    	Card card;
    	while(enemy_point < 17) {
    		card = cardList.get(card_index.get());
    		card_index.incrementAndGet();
    		enemy_point += card.getNumber();
    		enemy_grid.add(card, enemy_grid_index.get(), 0);
    		enemy_grid_index.incrementAndGet();
    	}
    }

    private void battle() {
    	enemyTurn();

    	enemy_first_card.turnItOver();

    	int player_point = Integer.parseInt(playerpoint_label.getText());
    	String result = "あなた：" + player_point + "\nディーラー：" + enemy_point + "\n";

    	if(player_point > BJ_MAX_VALUE) {
    		result += LOSE_MASSAGE;
    	}else if(enemy_point > BJ_MAX_VALUE) {
    		result += WIN_MASSAGE;
    	}else {
    		result += enemy_point < player_point ?
        			 WIN_MASSAGE : LOSE_MASSAGE ;
    	}

    	add_button.setVisible(false);
    	stand_button.setVisible(false);
    	restart_button.setVisible(true);

    	Alert alrt = new Alert(AlertType.INFORMATION);
		alrt.setTitle("");
		alrt.setHeaderText("");
		alrt.setContentText(result);
		alrt.showAndWait();
    }

}
