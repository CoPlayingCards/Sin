package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SinController implements EventHandler<ActionEvent>{
	private static int clickedValue = 0;
	private static Button clickedButton;
	private static Button wrongButton;
	private static int answerCount = 0;

	private final int number;

	private final int CARD_VALUE = 24;
	private final int GRID_ROW_SIZE = 3;
	private final int GRID_COLUMN_SIZE = 8;
	private final int CARD_HEIGHT = 90;
	private final int CARD_WEIGHT = 60;

	private List<Button> buttonList = new ArrayList<>();
	private GridPane grid;

	private SinController(int number) {
		this.number = number;
	}

	public SinController() {
		this(-1);
	}
	@FXML
    private AnchorPane base_pane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
    	assert base_pane != null : "fx:id=\"base_pane\" was not injected: check your FXML file 'Sin.fxml'.";
    	grid = new GridPane();
		int number = 0;

		//カードの作成
		for(int i = 0;i < CARD_VALUE; i++) {
			if(i % 2 == 0) {
				number++;
			}

			//カードの大きさ、クリックイベントの追加
			Button button = new Button("");
			button.setOnAction(new SinController(number));
			button.setPrefSize(CARD_WEIGHT, CARD_HEIGHT);
			button.setFont(new Font(30));
			button.setPadding(new Insets(0,0,0,0));
			button.setId("card");
			buttonList.add(button);
		}
		Collections.shuffle(buttonList);

		//グリッドにカードを配置
		for(int i = 0; i < GRID_ROW_SIZE;  i++) {
			for(int j = 0; j < GRID_COLUMN_SIZE; j++) {
				grid.add(buttonList.get(i * GRID_COLUMN_SIZE + j), j, i);
			}
		}

		//画面にグリッドを配置
    	AnchorPane.setBottomAnchor(grid, 20.0);
    	AnchorPane.setRightAnchor(grid, 30.0);
        base_pane.getChildren().add(grid);
    }

    //クリックイベント
    @Override
	public void handle(ActionEvent event) {
    	Button button = (Button)event.getSource();

    	//カードの色が赤（正解状態）だった場合
    	if(button.getTextFill().equals(Color.RED)) {
    		return;
    	}

    	//カードが二枚めくられている状態で他のカードがめくられた場合
    	if(clickedValue >= 2) {
    		wrongButton.setText("");
    		clickedButton.setText("");
    		clickedValue = 0;
    	}

    	//カードが表向きだった場合
    	if(!button.getText().isEmpty()) {
    		if(clickedValue == 1) {
    			return;
    		}else {
    			button.setText("");
        		clickedValue--;
        		return;
    		}
    	}

    	//カードが裏向きになっている場合
    	button.setText(String.valueOf(this.number));

    	if(clickedValue == 0) {
    		clickedButton = button;
    		clickedValue++;
    	}else {

    		//正誤判定
    		if(button.getText().equals(clickedButton.getText())) {
    			button.setTextFill(Color.RED);
    			clickedButton.setTextFill(Color.RED);
    			clickedValue = 0;
    			answerCount += 2;
    			if(answerCount == CARD_VALUE) {
    				showPopup();
    			}
    		}else {
    			wrongButton = button;
    			clickedValue++;
    		}
    	}
    }

    private void showPopup() {
    	Alert alrt = new Alert(AlertType.CONFIRMATION);
		alrt.setTitle("クリア！");
		alrt.setHeaderText(null);
		alrt.setContentText("You Win!!");
		Optional<ButtonType> result = alrt.showAndWait();
		if (result.get() == ButtonType.OK) {
		}
    }

}
