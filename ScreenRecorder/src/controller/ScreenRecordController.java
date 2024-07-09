package controller;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import business.HandleRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenRecordController implements ScreenRecordInterface{

	@FXML
	private Button playButton;
	@FXML
	private Button pauseButton;

	
	@FXML 
	private AnchorPane recordController;
	
	private double initX;
	private double initY;
	

	@FXML
	private ImageView imageViewPlay;
	@FXML
	private ImageView imageViewPause;

	private HandleRecord handleRecord = new HandleRecord();
	private Stage stage;

	@Override
	@FXML
	public void onClickPlay(ActionEvent event) {

		playButton.setVisible(false);
		pauseButton.setVisible(true);
		
		
		handleRecord.startRecord();
		
	
		System.out.println("Play Button is clicked");
	}

	@Override
	public Button getPlayButton() {

		return playButton;

	}

	@Override
	public ImageView getImageViewPlay() {

		return imageViewPlay;

	}


	@Override
	@FXML
	public void onClickPause(ActionEvent event) throws AWTException, IOException, InterruptedException {

		pauseButton.setVisible(false);
		playButton.setVisible(true);

		
		handleRecord.stopRecord();
				

		System.out.println("Pause Button is clicked");
	}

	@Override
	public Button getPauseButton() {

		return pauseButton;
	}

	@Override
	public ImageView getImageViewPause() {

		return imageViewPause;
	}
	
	@Override
	@FXML
	public void onClickOpen(ActionEvent event) throws IOException {
		
		handleRecord.loadedRecorderPane();
		
	}
	
	@Override
	@FXML
	public void onClickSave() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Video File");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 Files", ".mp4"));
		File file = fileChooser.showSaveDialog(new Stage());
		
		
		if(file !=null) {
			
			handleRecord.saveRecording(file);
		}
	}

	@Override
	@FXML
	public Button getOpenController(ActionEvent event) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void setStage(Stage stage)  {
		
//		handleRecord.loadedRecorderPane();
		this.stage = stage;
	}

	@FXML
	public double[] intialize() {
		recordController.setOnMousePressed(this::handleMousePressed);
		recordController.setOnMouseDragged(this::handleMouseDragged);
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		
		double paneWidth = recordController.getPrefWidth();
		double paneHieght = recordController.getPrefHeight();
		
		double x = screenBounds.getMaxX() - paneWidth - 10;
		double y = screenBounds.getMaxY() - paneHieght - 10;
		
		double[] arr = {x,y};

		return arr;
	}
	
	
	public void handleMousePressed(MouseEvent event) {
		
		
		
		initX = event.getScreenX() - stage.getX();
		initY = event.getScreenY() - stage.getY();
		
	}

	
	public void handleMouseDragged(MouseEvent event) {
		
		stage.setX(event.getScreenX() - initX);
		stage.setY(event.getScreenY() - initY);
		
	}

	

	
}
