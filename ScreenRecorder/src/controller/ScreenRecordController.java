package controller;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import business.HandleRecord;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenRecordController implements ScreenRecordInterface{

	private HandleRecord handleRecord = new HandleRecord();
	private Stage stage;
	
	@FXML
	private AnchorPane recordController;
//	@FXML
//	private AnchorPane mainPane;
	
	@FXML
	private Button recordingButton;
	@FXML
	private Button recordStopButton;

	
	
	@FXML
	private ImageView imageViewRecord;
	@FXML
	private ImageView imageViewStop;

	
	
	
	
	
	private double initX;
	private double initY;
	

	

	@Override
	@FXML
	public void onClickToRecordStart(ActionEvent event) throws IOException {

		try {
			handleRecord.startRecord();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		Stage primaryStage = (Stage) recordingButton.getScene().getWindow();
		primaryStage.close();
		
		handleRecord.loadedRecorderPane();
		
		
		
	
		System.out.println("start button is clicked");
	}

	@Override
	@FXML
	public Button getRecordingButton() {

		return recordingButton;

	}

	@Override
	@FXML
	public ImageView getImageViewRecord() {

		return imageViewRecord;

	}


	@Override
	@FXML
	public void onClickToRecordStop() {

		try {	
			handleRecord.stopRecord();
		
		
		Platform.runLater(()->{
			
			Stage primaryStage = (Stage) recordStopButton.getScene().getWindow();
			primaryStage.close();
			
			
			try {
				Thread.sleep(100);
				Stage mainAnchor = new Stage();
				Image icon = new Image("/resources/assets/images/J.png");
				Parent mainPane;
			
			
				mainPane = FXMLLoader.load(getClass().getResource("/resources/application/RecordScene.fxml"));
				mainAnchor.getIcons().add(icon);
				mainAnchor.setTitle("Screen Recorder");
				mainAnchor.setScene(new Scene(mainPane));
				mainAnchor.show();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
			
		}finally {
			
			String fixedPath = "D:\\Example Screen Videos";
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Video File");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 Files", ".mp4"));
			
			File initialPath = new File(fixedPath);
			fileChooser.setInitialDirectory(initialPath);
			

			File file = fileChooser.showSaveDialog(new Stage());
			
			
			 if (file != null) {
			       
			        handleRecord.saveRecording(file);
			        System.out.println("File saved: " + file.getAbsolutePath());
			    }else {
			        
			        System.out.println("Save action cancelled.");
			    }
		}
		
		
				

		System.out.println("stop button is clicked");
	}

	@Override
	@FXML
	public Button getRecordStopButton() {

		return recordStopButton;
	}

	@Override
	@FXML
	public ImageView getImageViewStop() {

		return imageViewStop;
	}
	
	@Override
	@FXML
	public void onClickOpen(ActionEvent event) throws IOException {
		
		
		
	}
	
	@Override
	@FXML
	public void onClickSave() {
		
			
		
	}

	@Override
	@FXML
	public Button getOpenController(ActionEvent event) {
		
		return null;
	}
	
	
	public void setStage(Stage stage)  {
		
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
