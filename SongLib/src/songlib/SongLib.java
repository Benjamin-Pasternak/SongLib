/*
 * @Parth Shah
 * 
 * @Benjamin Pasternak
 * 
 */

package songlib;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SongLib extends Application {

	@Override
	public void start(Stage arg) throws Exception {
		// TODO Auto-generated method stub
		
		//Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("songLibView.fxml"));
		
		Parent root = loader.load();
		//SongLibController c = loader.getController();
		SongLibController instance = loader.getController();
		Scene scene = new Scene(root);
		arg.setTitle("Song Library");
		
		arg.setScene(scene);
		arg.getIcons().add(new Image("/songLibIcon.jpg"));
		arg.show();
		arg.setResizable(false);
		arg.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				
				
				
				
				
				//writing listview contents to file in the user's current directory for persistance//
				try {
					PrintWriter writer = new PrintWriter("./persistance.txt");
					Random random = new Random();
					int code = random.nextInt((999999 - 100000) + 1) + 100000; 
					String nullCode = "nullCode" + code;
					
					writer.println(nullCode);
					
					for(int i = 0; i<instance.songList.getItems().size(); i++) {
						String year = instance.songList.getItems().get(i).year;
						if(year == null) {
							year = nullCode;
						}
						
						String album = instance.songList.getItems().get(i).album;
						if(album == null) {
							album = nullCode;
							
						}
						
						writer.println(instance.songList.getItems().get(i).name);
						writer.println(instance.songList.getItems().get(i).artist);
						writer.println(year);
						writer.println(album);
						writer.println();
						
						
					}
					writer.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0); //making sure program terminates on clicking x button//
			}
			
			
		});
		
		
	}
	public static void main(String[] args) {
		
		launch(args);
		
		
			
		
	}
}
