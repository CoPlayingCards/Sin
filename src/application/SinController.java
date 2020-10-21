package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SinController implements EventHandler<ActionEvent>{
	private static int clickedValue = 0;
	private static Button clickedButton;

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
		for(int i = 0;i < CARD_VALUE; i++) {
			if(i % 2 == 0) {
				number++;
			}

			Button button = new Button("");
			button.setOnAction(new SinController(number));
			button.setPrefSize(CARD_WEIGHT, CARD_HEIGHT);
			buttonList.add(button);
		}
		Collections.shuffle(buttonList);

		for(int i = 0; i < GRID_ROW_SIZE;  i++) {
			for(int j = 0; j < GRID_COLUMN_SIZE; j++) {
				grid.add(buttonList.get(i * GRID_COLUMN_SIZE + j), j, i);
			}
		}
    	AnchorPane.setBottomAnchor(grid, 20.0);
    	AnchorPane.setRightAnchor(grid, 30.0);
        base_pane.getChildren().add(grid);
    }

    @Override
	public void handle(ActionEvent event) {
    	Button button = (Button)event.getSource();
    	if(!button.getText().isEmpty()) {
    		button.setText("");
    		clickedValue--;
    		return;
    	}

    	if(clickedValue >= 2) {
    		return;
    	}

    	button.setText(String.valueOf(this.number));

    	if(clickedValue == 0) {
    		clickedButton = button;
    		clickedValue++;
    	}else {
    		if(button.getText().equals(clickedButton.getText())) {
    			button.setTextFill(Color.RED);
    			clickedButton.setTextFill(Color.RED);
    			clickedValue = 0;
    		}else {
    			clickedValue++;
    		}
    	}
    }
}
