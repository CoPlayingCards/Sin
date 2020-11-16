package application;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class BJController {
	private static final int BJ_MAX_VALUE = 21;
	private static final String WIN_MASSAGE = "かち";
	private static final String LOSE_MASSAGE = "#敗北";

	private Queue<Card> cardList;
	private Card enemy_first_card;

	private Player player;
	private Player dealer;

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
    	showNewWindow(event, "GameList.fxml", "ゲーム選択");
    }

    @FXML
    void onClick_restart(ActionEvent event) {
    	showNewWindow(event, "BlackJack.fxml", "BlackJack");
    }

    void showNewWindow(Event event, String resource, String title) {
    	Scene s = ((Node)event.getSource()).getScene();
		Window window = s.getWindow();
		window.hide();

		try {
			Parent parent = FXMLLoader.load(getClass().getResource(resource));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(title);
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

        //ボタンの表示非表示
        add_button.setVisible(true);
    	stand_button.setVisible(true);
    	restart_button.setVisible(false);

    	player = new Player(player_grid, playerpoint_label);
    	dealer = new Player(enemy_grid, enemypoint_label);

    	//カードを作成し、シャッフルする。
    	ArrayList<Card> cards = new ArrayList<>();
        for(int i = 0 ; i < 4 ; i++) {
        	for(int j = 1 ; j < 14 ; j++) {
        		cards.add(new Card(j));
        	}
        }
        Collections.shuffle(cards);
        cardList = new ArrayDeque<>(cards);

       //ヒットボタンの挙動
        add_button.setOnAction(e -> {
        	if(player.cardCount() > 7) {return;}
        	player.addCard(cardList.poll());
        	if(player.getPoint() > BJ_MAX_VALUE) {battle();}
        });

        //スタンドボタンの挙動
        stand_button.setOnAction(e -> {
        	battle();
        });

        cardDistribute();
    }

    //初期配布
    private void cardDistribute() {

    	//プレイヤー
    	for(int i = 0; i < 2; i++) {
    		player.addCard(cardList.poll());
    	}

    	//ディーラー
    	enemy_first_card = cardList.poll();
    	enemy_first_card.turnItOver();
    	dealer.addCard(enemy_first_card);
    	dealer.addCard(cardList.poll());

    }

    private void enemyTurn() {
    	while(dealer.getPoint() < 17) {
    		dealer.addCard(cardList.poll());
    	}
    }

    //点数勝負
    private void battle() {
    	enemyTurn();

    	enemy_first_card.turnItOver();

    	int player_point = player.getPoint();
    	String result = "あなた：" + player_point + "\nディーラー：" + dealer.getPoint() + "\n";

    	if(player_point > BJ_MAX_VALUE) {
    		result += LOSE_MASSAGE;
    	}else if(dealer.getPoint() > BJ_MAX_VALUE) {
    		result += WIN_MASSAGE;
    	}else {
    		result += dealer.getPoint() < player_point ?
        			 WIN_MASSAGE : LOSE_MASSAGE ;
    	}

    	//ボタンの表示非表示
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
