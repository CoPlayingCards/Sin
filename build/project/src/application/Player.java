package application;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Player {

	private int point;
	private GridPane grid;
	private int gridIndex;
	private Label pointLabel;

	public Player(GridPane grid, Label label) {
		this.grid = grid;
		this.pointLabel = label;
		this.gridIndex = 0;
	}

	public void addPoint(int point) {
		this.point += point;
	}

	public void addCard(Card card) {
		this.grid.add(card, gridIndex, 0);
		gridIndex++;
		this.point += card.getNumber();
		this.pointLabel.setText(String.valueOf(this.point));
	}

	public int cardCount() {
		return this.gridIndex;
	}

	public int getPoint(){
		return this.point;
	}

	public GridPane getGrid() {
		return this.grid;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public void setGrid(GridPane grid) {
		this.grid = grid;
	}
}
