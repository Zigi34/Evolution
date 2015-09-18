package org.zigi.evolution;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.log4j.Logger;
import org.zigi.evolution.controller.MainController;

public class Main extends Application {

	private static final Logger log = Logger.getLogger(Main.class);

	private BorderPane mainPane;
	private MainController mainController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			URL location = getClass().getResource("gui/main.fxml");
			log.info("Location: " + location.toString());
			FXMLLoader loader = new FXMLLoader(location);
			mainPane = (BorderPane) loader.load();

			mainController = loader.getController();
			mainController.setMainApp(this);

			Scene scene = new Scene(mainPane);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static void main(String[] args) {
		Application.launch(args);

	}
}
