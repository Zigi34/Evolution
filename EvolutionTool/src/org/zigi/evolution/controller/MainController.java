package org.zigi.evolution.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import org.zigi.evolution.Main;

public class MainController implements Initializable {

	private Main window;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public Main getMainApp() {
		return window;
	}

	public void setMainApp(Main mainApp) {
		this.window = mainApp;
	}

	@FXML
	public void handleClose(ActionEvent event) {
		Platform.exit();
	}
}
