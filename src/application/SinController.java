package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SinController {
	@FXML
    private AnchorPane base_pane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
    	assert base_pane != null : "fx:id=\"base_pane\" was not injected: check your FXML file 'Sin.fxml'.";
    	CreateCardsList ccl = new CreateCardsList(24);
    	GridPane grid = ccl.getGrid();
    	AnchorPane.setBottomAnchor(grid, 20.0);
        base_pane.getChildren().add(grid);
    }
}
