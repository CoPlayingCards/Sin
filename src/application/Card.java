package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Card  extends Label{

	private final int number;
	private static final int CARD_HEIGHT = 90;
	private static final int CARD_WEIGHT = 60;

	public Card(int number) {
		super("" + number);
		this.number = number;
		setPrefSize(CARD_WEIGHT, CARD_HEIGHT);
		setFont(new Font(30));
		setPadding(new Insets(0,0,0,0));
		setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY)));
		setAlignment(Pos.CENTER);
	}

	public int getNumber() {
		return number > 10 ? 10 : number;
	}

	public void turnItOver() {
		if(getText().isEmpty()) {
			setText("" + number);
		}else {
			setText("");
		}
	}
}
