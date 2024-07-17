package controller;



import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public interface ScreenRecordInterface {
	
	void onClickToRecordStart(ActionEvent event) throws IOException;
	void onClickToRecordStop() throws IOException;
	void onClickOpen(ActionEvent event) throws IOException;
	void onClickSave();
	void handleMousePressed(MouseEvent event);
	void handleMouseDragged(MouseEvent event);
	void setStage(Stage stage);
	
	
	Button getRecordingButton();
	Button getRecordStopButton();
	Button getOpenController(ActionEvent event);
	
	
	ImageView getImageViewRecord();
	ImageView getImageViewStop();
	
	

	
	
	
	
}
