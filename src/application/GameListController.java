package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GameListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button sin;

    @FXML
    void onClick_1(Event event) {
    	showNewWindow(event, "Sin.fxml", "神経衰弱");
    }

    @FXML
    void onClick_2(Event event) {
    	showNewWindow(event, "BlackJack.fxml", "BlackJack");
    }

    @FXML
    void onClick_3(Event event) {

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
        assert sin != null : "fx:id=\"sin\" was not injected: check your FXML file 'GameList.fxml'.";

    }
}
