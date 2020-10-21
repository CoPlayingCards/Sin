package application;

import java.util.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CreateCardsList {
	private final int CARD_VALUE = 24;
	private final int GRID_ROW_SIZE = 3;
	private final int GRID_COLUMN_SIZE = 8;
	private final int CARD_HEIGHT = 90;
	private final int CARD_WEIGHT = 60;

	private List<Button> buttonList = new ArrayList<>();
	private GridPane grid;

	public CreateCardsList() {
		grid = new GridPane();
		int number = 0;
		for(int i = 0;i < CARD_VALUE; i++) {
			if(i % 2 == 0) {
				number++;
			}
			final int tmp = number;
			Button button = new Button("");
			button.setOnAction(event -> {
				Button bt = (Button)event.getSource();
				if(bt.getText().equals("")) {
					bt.setText(String.valueOf(tmp));
				}else {
					bt.setText("");
				}
			});
			button.setPrefSize(CARD_WEIGHT, CARD_HEIGHT);
			buttonList.add(button);
		}
		Collections.shuffle(buttonList);

		for(int i = 0; i < GRID_ROW_SIZE;  i++) {
			for(int j = 0; j < GRID_COLUMN_SIZE; j++) {
				grid.add(buttonList.get(i * GRID_COLUMN_SIZE + j), j, i);
			}
		}
	}

	public GridPane getGrid() {
		return this.grid;
	}
}
