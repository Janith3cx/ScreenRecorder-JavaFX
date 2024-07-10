package business;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jcodec.api.awt.AWTSequenceEncoder;

import controller.ScreenRecordController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;





public class HandleRecord {

	private volatile Boolean running;
	private final BlockingQueue<BufferedImage> imageQueue = new LinkedBlockingQueue<>();
	private File tempFile;
	
	private static final int TARGET_FPS = 30;

	public Boolean startRecord() {
		
		 running = true;
		 new Thread(() -> {
			try {
				screenCapture();
			} catch (AWTException | IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		 new Thread(() -> {
			
				
				try {
					processImages();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}).start();
		 return running;
	}
	
	public Boolean stopRecord() throws IOException {
		
		
		return running = false;
		
		
	}

	public void screenCapture() throws AWTException, IOException, InterruptedException {

		Rectangle screenRect = new Rectangle( Toolkit.getDefaultToolkit().getScreenSize());
		Robot rbt = new Robot();
		long frameTime = 1000/TARGET_FPS;

		System.out.println("cpatured the screen");



		int count = 0;

			while(running) {
				long startTime = System.currentTimeMillis();
				BufferedImage image = rbt.createScreenCapture(screenRect);
				imageQueue.put(image);
				long endTime = System.currentTimeMillis();
				long sleepTime = frameTime - (endTime-startTime);
				
				if(sleepTime>0) {
					Thread.sleep(sleepTime);
				}
				System.out.println("cpatured count "+count);


				count++;
			}

			System.out.println("cpatured running" + running);
			


	}


	public void  processImages() throws IOException{

		tempFile = File.createTempFile("tempVideo", ".mp4");
		
		AWTSequenceEncoder sequenceEncoder = AWTSequenceEncoder.createSequenceEncoder(tempFile, TARGET_FPS);
			
			while(running || !imageQueue.isEmpty()) {
				
				BufferedImage image = imageQueue.poll();
				
				if(image !=null) {
					
					sequenceEncoder.encodeImage(image);
				}
			
			
			sequenceEncoder.finish();
			
		}
		
	}
	
	public void saveRecording(File destination) {
	
		if(tempFile !=null && tempFile.exists()) {
			
			if(destination !=null) {
				
				tempFile.renameTo(destination);
			}
		}
	}
	
	
	
	public void loadedRecorderPane() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/application/RecordControls.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		
		ScreenRecordController recordController = loader.getController();
		recordController.setStage(stage);
		
		double[] arr = recordController.intialize();
		
		double x = arr[0];
		double y = arr[1];
		
		System.out.println("x : "+x+" y : "+y);
		
		scene.setFill(null);
		stage.setX(x);
		stage.setY(y);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
		
		
	}

}
