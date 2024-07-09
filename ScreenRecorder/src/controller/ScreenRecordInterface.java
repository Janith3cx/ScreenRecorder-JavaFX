package controller;



import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public interface ScreenRecordInterface {
	
	void onClickPlay(ActionEvent event);
	void onClickPause(ActionEvent event) throws AWTException, IOException, InterruptedException;
	void onClickOpen(ActionEvent event) throws IOException;
	void onClickSave();
	void handleMousePressed(MouseEvent event);
	void handleMouseDragged(MouseEvent event);
	void setStage(Stage stage);
	
	
	Button getPlayButton();
	Button getPauseButton();
	Button getOpenController(ActionEvent event);
	
	ImageView getImageViewPlay();
	ImageView getImageViewPause();
	
	
	
	
}
