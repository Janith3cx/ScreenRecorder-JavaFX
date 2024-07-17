package business;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;

import org.jcodec.api.awt.AWTSequenceEncoder;

import controller.ScreenRecordController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;





public class HandleRecord {

	
	private static volatile Boolean running;
	
	private final BlockingQueue<BufferedImage> imageQueue = new LinkedBlockingQueue<>();
	private static File tempFile;
	
	private static final int TARGET_FPS = 10;
	
	
	private static Thread recordingThread;
	private static Thread processingThread;

	private File desti = new File("D:\\Example Screen Videos\\video.mp4");
	
	public void startRecord() throws AWTException , IOException , InterruptedException {
		
			
			running = true;
		
			new Thread(() -> {
			try {
				screenCapture();
			} catch (AWTException | InterruptedException e) {
				e.printStackTrace();
			}
		}).start();;
		
		
			new Thread(() -> {
			try {
				processImages();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}).start();;
		 
//			recordingThread.start();
//			processingThread.start();
			
			System.out.println( "recording thread in start methoad "+recordingThread+"\n"+"processing thread in start methoad "+processingThread);
			
	}
	
	public void stopRecord() {
		
			running = false;
			
			 
			System.out.println("stop record clicked 1 " + running);
	
			System.out.println( "recording thread in stop methoad "+recordingThread+"\n"+"processing thread in stop methoad "+processingThread);
		
	}

	public void screenCapture() throws AWTException, InterruptedException {

		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		
		long frameTime = 1000/TARGET_FPS;

		System.out.println("cpatured the screen");
//		System.out.println("frame time " + frameTime);


		int count = 0;

		 while (running) {
			
			Robot rbt = new Robot();
			BufferedImage image = rbt.createScreenCapture(screenRect);
			 	
			 	long startTime = System.currentTimeMillis();
			       imageQueue.put(image);
			     
//				System.out.println("in the screen " + running);
	           
	            long endTime = System.currentTimeMillis();
	            long sleepTime = frameTime - (endTime - startTime);
	            
	            

	            if (sleepTime < 0) {
	                
	                    Thread.sleep(sleepTime);
	                
	            }
	            System.out.println("captured count " + count);
//	            System.out.println("start time " + startTime);
//	            System.out.println("end time " + endTime);
//	            System.out.println("sleep time " + sleepTime);

	            count++;
	        }
		 
		


//			System.out.println("cpatured running" + running);
			


	}


	@SuppressWarnings("unused")
	public void  processImages() throws IOException, InterruptedException {

		
		tempFile = File.createTempFile("tempVideo", ".mp4");
		
		AWTSequenceEncoder sequenceEncoder = AWTSequenceEncoder.createSequenceEncoder(tempFile, TARGET_FPS);
			
			try {	
				int count = 0;
				while(running) {
					
						BufferedImage image = imageQueue.take();
                       
						if(image !=null) {
							
								sequenceEncoder.encodeImage(image);
						}
						
//						System.out.println("image " + image);


						
				
					
//					System.out.println(Thread.currentThread().getName() + " is running");
					
					
					System.out.println("image processing " + count);
					
					count++;
				}
				
			
			}finally {
				
				 if (sequenceEncoder != null) {
		                sequenceEncoder.finish();
		                
		                System.out.println("encoder is finished " + sequenceEncoder);
		            }
			
				
			}
		
			
	
		
	}
	
	
	
	public void saveRecording(File destination) {
	
		if (tempFile != null && tempFile.exists()) {
	        if (destination != null) {
	            tempFile.renameTo(destination);
	        }
	    }
		
		
		
	}
	
	
	
	public void loadedRecorderPane() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/application/RecordControls.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		Image icon = new Image("/resources/assets/images/J.png");
		
		ScreenRecordController recordController = loader.getController();
		recordController.setStage(stage);
		
		double[] arr = recordController.intialize();
		
		double x = arr[0];
		double y = arr[1];
		
		System.out.println("x : "+x+" y : "+y);
		
		scene.setFill(null);
		stage.setX(x);
		stage.setY(y);
		stage.getIcons().add(icon);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
		
		
	}

}
