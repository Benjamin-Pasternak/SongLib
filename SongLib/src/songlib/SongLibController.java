/*
 * @Parth Shah
 * @netID: prs117
 * @RUID: 172005370
 * @CS213 Section 03
 * @Group 87
 * 
 * @Benjamin Pasternak
 * @netID: bp443
 * @Group 87
 * V3
 */

package songlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;


public class SongLibController implements Initializable {
	@FXML Button c;
	@FXML ListView<SongNode> songList;
	@FXML TextArea songInfo;
	@FXML TextField addNameField;
	@FXML TextField addArtistField;
	@FXML TextField addYearField;
	@FXML TextField addAlbumField;
	@FXML StackPane addStackPane;
	@FXML Button enableAdd;
	@FXML Button cancelAdd;
	@FXML Button removeButton;
	@FXML Button confirmRemove;
	@FXML StackPane removeStackPane;
	@FXML Button cancelRemove;
	@FXML Label addDuplicate;
	@FXML StackPane st;
	@FXML Button enableEdit;
	@FXML StackPane editStackPane;
	@FXML TextField editNameField;
	@FXML TextField editArtistField;
	@FXML TextField editYearField;
	@FXML TextField editAlbumField; 
	@FXML Button editSaveButton;
	@FXML Label listLabel;
	@FXML Button editCancelButton;
	@FXML Label editDuplicate;
	@FXML Label missingInfoAddLabel;
	@FXML Label missingInfoEditLabel;
	@FXML Label emptyRemoveLabel;
	@FXML Label emptyEditLabel;
	@FXML Label yearAddError;
	@FXML Label yearEditError;
	
	
	@FXML public void addSong(ActionEvent e){
		
		
		if(addNameField.getText().isBlank() == false && addArtistField.getText().isBlank() == false) {
			//Check if song name and artist name are already present in ListView//
			if(containsNode(addNameField.getText(), addArtistField.getText()) == true) {
				missingInfoAddLabel.setVisible(false);
				yearAddError.setVisible(false);
				addDuplicate.setVisible(true);
				return;
			}
			if(addYearField.getText().matches("[0-9]+") == false) {
				missingInfoAddLabel.setVisible(false);
				addDuplicate.setVisible(false);
				yearAddError.setVisible(true);
				return;
			}
			
			String year = null;
			String album = null;
			//notempty
			if(addYearField.getText().isBlank() == false) {
				year = addYearField.getText();
				year = year.strip();
				
			}
			//notempty
			if(addAlbumField.getText().isBlank() == false) {
				album = addAlbumField.getText();
				album = album.strip();
				
			}
			
			SongNode tempSong = new SongNode(addNameField.getText(), addArtistField.getText(), year, album);
			tempSong.name = tempSong.name.strip();
			tempSong.artist = tempSong.artist.strip();
			
			//finding the right place to put new songNode and adding it there//
			int alphaIndex = findCorrectIndex(addNameField.getText(), addArtistField.getText());
			songList.getItems().add(alphaIndex, tempSong);
			
			
			//autoselecting and displaying newly added song's info in song display//
			String yearf = tempSong.year;
			String albumf = tempSong.album;
			if(yearf == null) {
				yearf = "unknown";
				
			}
			if(albumf == null) {
				albumf = "unknown";
			}
			
			songList.getSelectionModel().select(tempSong);
			songInfo.setText("Song Name: " + tempSong.name + "\n" + "Song Artist: " + tempSong.artist + "\n" + "Song Year: " + yearf + "\n" + "Song Album: " + albumf + "\n"  );
			
		
			//reseting the textfields after user has clicked add//
			addNameField.clear();
			addArtistField.clear();
			addYearField.clear();
			addAlbumField.clear();
			
			yearAddError.setVisible(false); //making sure year error goes away on succesfull add//
			addDuplicate.setVisible(false); //making sure error for duplicate add goes away on successful add//
			missingInfoAddLabel.setVisible(false); //making sure error for missing info goes away on successful add//
			
			addStackPane.setVisible(false);
		}
		
		else {
			addDuplicate.setVisible(false);
			yearAddError.setVisible(false);
			missingInfoAddLabel.setVisible(true);
		}
		
	}
	
	public void initSongList() {
		//use this for persistance - pulling items from textfile - populating Listview//
		
		
		File pers = new File("./persistance.txt");
		if(pers.isFile() == false) {
			//first time running program, or user deleted persistance.txt in which case the program will run as if it is being run for first time//
			return;
		}
		try {
			Scanner scanner = new Scanner(pers);
			String nullCode = null;
			if(scanner.hasNextLine()) {
				nullCode = scanner.nextLine();
			}
			
			while(scanner.hasNextLine()) {
				String name = scanner.nextLine();
				String artist = scanner.nextLine();
				String year = scanner.nextLine();
				String album = scanner.nextLine();
				if(year.compareTo(nullCode) == 0) {
					year = null;
				}
				
				if(album.compareTo(nullCode) == 0) {
					album = null;
				}
				
				SongNode temp = new SongNode(name, artist, year, album);
				songList.getItems().add(temp);
				scanner.nextLine(); //empty line//
				
			}
			
			if(songList.getItems().isEmpty() == false) {
				//after loading, on the condition that the list is not empty, select first item//
				songList.getSelectionModel().select(0);
				SongNode tempSongNode = songList.getItems().get(0);
				String yearf = tempSongNode.year;
				String albumf = tempSongNode.album;
				
				if(yearf == null) {
					yearf = "unknown";
				}
				
				if(albumf == null) {
					albumf = "unknown";
				}
				
				songInfo.setText("Song Name: " + tempSongNode.name + "\n" + "Song Artist: " + tempSongNode.artist + "\n" + "Song Year: " + yearf + "\n" + "Song Album: " + albumf + "\n"  );
				
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	@FXML public void updateTextArea() {
		emptyRemoveLabel.setVisible(false); //bookkeeping measure to make sure empty msg is erased after next action//
		emptyEditLabel.setVisible(false); //bookkeeping - hiding empty edit label on clickaway//
		SongNode songSelected = songList.getSelectionModel().getSelectedItem();
		if(songList.getSelectionModel().isEmpty()) {
			//if the user clicks on empty listview, return immediately from this method and do not continue forward to avoid exception//
			return;
		}
		
		
		
		String year = songSelected.year;
		String album = songSelected.album;
		if(year == null) {
			year = "unknown";
			
		}
		
		if(album == null) {
			album = "unknown";
		}
		songInfo.setText("Song Name: " + songSelected.name + "\n" + "Song Artist: " + songSelected.artist + "\n" + "Song Year: " + year + "\n" + "Song Album: " + album + "\n"  );
		
		
	}
	
	@FXML public void addRequested() {
		emptyRemoveLabel.setVisible(false); //bookkeeping measure to make sure empty msg is erased after next action//
		emptyEditLabel.setVisible(false); //bookkeeping - hiding empty edit label on clickaway//
		removeStackPane.setVisible(false); //same as remove cancel (removeno)//
		editCancel();
		
		addStackPane.setVisible(true);
		
	}
	
	@FXML public void cancelAdd() {
		addNameField.clear();
		addArtistField.clear();
		addYearField.clear();
		addAlbumField.clear();
		addDuplicate.setVisible(false); //making sure duplicate add error goes away on cancel add button click//
		missingInfoAddLabel.setVisible(false); //making sure missing info add error goes away on cacel add button click//
		yearAddError.setVisible(false); //hiding year error if add is canceled.//
		
		addStackPane.setVisible(false);
	}
	
	@FXML public void removeRequested() {
		emptyEditLabel.setVisible(false); //bookkeeping - hiding empty edit label on clickaway//
		
		if(songList.getItems().isEmpty()) {
			emptyRemoveLabel.setVisible(true);
			
			cancelAdd();
			editCancel();
			return;
		}
		cancelAdd();
		editCancel();
		removeStackPane.setVisible(true);
	}
	
	@FXML public void removeYes() {
		
		if(songList.getItems().isEmpty()) {
			return;
		}
		
		
		SongNode selected = songList.getSelectionModel().getSelectedItem();
		int index = songList.getItems().indexOf(selected);
		//if the song selected to be removed is the only one in listview//
		if(index == 0 && songList.getItems().size() ==1) {
			songInfo.setText("");
			songList.getItems().removeAll(selected);
			
			removeStackPane.setVisible(false); //hiding confirmation after removed//
			return;
		}
		
		//if the song selected to be removed is first one in listview//
		if(index == 0) {
			songList.getSelectionModel().select(1);
			SongNode replacement = songList.getItems().get(1);
			String year = replacement.year;
			String album = replacement.album;
			if(year == null) {year = "unknown";}
			if(album == null) {album = "unknown";}
			songInfo.setText("Song Name: " + replacement.name + "\n" + "Song Artist: " + replacement.artist + "\n" + "Song Year: " + year + "\n" + "Song Album: " + album + "\n" );
			songList.getItems().removeAll(selected);
			
			removeStackPane.setVisible(false);
			return;
		}
		
		//if the song selected to be removed is between two other ones in listview//
		if(index != 0 && index != songList.getItems().size() - 1) {
			songList.getSelectionModel().select(index+1);
			SongNode replacement = songList.getItems().get(index + 1);
			String year = replacement.year;
			String album = replacement.album;
			if(year == null) {year = "unknown";}
			if(album == null) {album = "unknown";}
			songInfo.setText("Song Name: " + replacement.name + "\n" + "Song Artist: " + replacement.artist + "\n" + "Song Year: " + year + "\n" + "Song Album: " + album + "\n" );
			songList.getItems().removeAll(selected);
			
			removeStackPane.setVisible(false);
			return;
		}
		
		//if the song selected to be removed is the last one in the listview//
		if(index != 0 && index == songList.getItems().size() -1) {
			songList.getSelectionModel().select(index-1);
			SongNode replacement = songList.getItems().get(index - 1);
			String year = replacement.year;
			String album = replacement.album;
			if(year == null) {year = "unknown";}
			if(album == null) {album = "unknown";}
			songInfo.setText("Song Name: " + replacement.name + "\n" + "Song Artist: " + replacement.artist + "\n" + "Song Year: " + year + "\n" + "Song Album: " + album + "\n" );
			songList.getItems().removeAll(selected);
			
			removeStackPane.setVisible(false);
			return;
		}
		
		
	}
	
	@FXML public void removeNo() {
		
		removeStackPane.setVisible(false);
		
	}
	
	@FXML public void editRequested() {
		emptyRemoveLabel.setVisible(false); //bookkeeping measure to make sure empty msg is erased after next action//
		if(songList.getItems().isEmpty()) { //if songlists empty//
			emptyEditLabel.setVisible(true);
			removeNo();
			cancelAdd();
			return;
		}
		
		removeNo(); //making sure add and remove stackpanes are not visible//
		cancelAdd();
		SongNode selected = songList.getSelectionModel().getSelectedItem();
		songList.setVisible(false); //So that user cannot select another song in listview after clicking edit but before clicking the save button - prevents displaying incorrect information and editing unintended song//
		//filling in info of selected song that user wants to edit//
		editNameField.setText(selected.name);
		editArtistField.setText(selected.artist);
		
		String year = selected.year;
		if(year == null) {
			year = "";
		}
		String album = selected.album;
		if(album == null) {
			album = "";
		}
		editYearField.setText(year);
		editAlbumField.setText(album);
		listLabel.setText(" Edit Song");
		editStackPane.setVisible(true);
	}
	
	@FXML public void editSong() {
		
		
		if(editNameField.getText().isBlank() == false && editArtistField.getText().isBlank() == false) {
			//Check if song name and artist name are already present in ListView//
			int position = songList.getSelectionModel().getSelectedIndex();
			int check = containsNodeEdit(editNameField.getText(), editArtistField.getText() );
			if(check != -1 && check != position ) { //if the song and artist combination was found, AND the song is not the current song selected (to ensure users can edit year and album without changing name artist)//
				missingInfoEditLabel.setVisible(false);
				yearEditError.setVisible(false);
				editDuplicate.setVisible(true);
				
				return;
			}
			
			if(editYearField.getText().matches("[0-9]+") == false) {
				missingInfoEditLabel.setVisible(false);
				editDuplicate.setVisible(false);
				yearEditError.setVisible(true);
				return;
			}
			
			String newYear = null;
			String newAlbum = null;
			//notempty
			if(editYearField.getText().isBlank() == false) {
				
				newYear = editYearField.getText();
				newYear = newYear.strip();
				
			}
			//notempty
			if(editAlbumField.getText().isBlank() == false) {
				newAlbum = editAlbumField.getText();
				newAlbum = newAlbum.strip();
				
			}
		
			String newName = editNameField.getText();
			newName = newName.strip();
			String newArtist = editArtistField.getText();
			newArtist = newArtist.strip();
			
			SongNode editedSong = new SongNode(newName, newArtist, newYear, newAlbum);
			
			//finding the right place to put edited songNode and adding it there//
			int alphaIndex = findCorrectIndex(newName, newArtist);
			songList.getItems().add(alphaIndex, editedSong);
			
			//removing unedited song from listview//
			SongNode selected = songList.getSelectionModel().getSelectedItem();
			songList.getItems().remove(selected);
			
			//autoselecting and displaying newly added song's info in song display//
			String yearf = editedSong.year;
			String albumf = editedSong.album;
			if(yearf == null) {
				yearf = "unknown";
				
			}
			if(albumf == null) {
				albumf = "unknown";
			}
			
			songList.getSelectionModel().select(editedSong);
			songInfo.setText("Song Name: " + editedSong.name + "\n" + "Song Artist: " + editedSong.artist + "\n" + "Song Year: " + yearf + "\n" + "Song Album: " + albumf + "\n"  );
			
			//clearing out all edit fields for next use//
			editNameField.clear();
			editArtistField.clear();
			editYearField.clear();
			editAlbumField.clear();
			
			editDuplicate.setVisible(false);//hiding error messages if they were invoked on successful edit//
			missingInfoEditLabel.setVisible(false); 
			yearEditError.setVisible(false);
			editStackPane.setVisible(false);
			listLabel.setText(" Song List");
			songList.setVisible(true);
			
		}
		
		else {
			//did not provide both name and artist fields
			editDuplicate.setVisible(false);
			yearEditError.setVisible(false);
			missingInfoEditLabel.setVisible(true);
		}
		
		
	}
	
	@FXML public void editCancel() {
		editNameField.clear();
		editArtistField.clear();
		editYearField.clear();
		editAlbumField.clear();
		editDuplicate.setVisible(false);
		missingInfoEditLabel.setVisible(false); //hiding error message if they were invoked//
		yearEditError.setVisible(false);
		editStackPane.setVisible(false);
		listLabel.setText(" Song List");
		songList.setVisible(true);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initSongList();
		songInfo.setEditable(false);
		
	}
	
	//Searches ListView SongNode objects to look for node in ListView songList that contains the given song name and artist//
	//returns true if found one, false if not.
	private boolean containsNode(String name, String artist) {
		String loweredName = name.toLowerCase();
		loweredName = loweredName.replaceAll("\\s", "");
		
		String loweredArtist = artist.toLowerCase();
		loweredArtist = loweredArtist.replaceAll("\\s", "");
		
		for(int i = 0; i< songList.getItems().size(); i++) {
			String tempName = songList.getItems().get(i).name;
			tempName = tempName.toLowerCase();
			tempName = tempName.replaceAll("\\s", "");
			String tempArtist = songList.getItems().get(i).artist;
			tempArtist = tempArtist.toLowerCase();
			tempArtist = tempArtist.replaceAll("\\s", "");
			if(tempName.compareTo(loweredName) == 0 && tempArtist.compareTo(loweredArtist) == 0) {
				//found a node with the same name and artist as parameter node//
				return true;
			}
			
		}
		return false;
	}
	
	private int containsNodeEdit(String name, String artist) {
		String loweredName = name.toLowerCase();
		loweredName = loweredName.replaceAll("\\s", "");
		
		String loweredArtist = artist.toLowerCase();
		loweredArtist = loweredArtist.replaceAll("\\s", "");
		
		for(int i = 0; i< songList.getItems().size(); i++) {
			String tempName = songList.getItems().get(i).name;
			tempName = tempName.toLowerCase();
			tempName = tempName.replaceAll("\\s", "");
			String tempArtist = songList.getItems().get(i).artist;
			tempArtist = tempArtist.toLowerCase();
			tempArtist = tempArtist.replaceAll("\\s", "");
			if(tempName.compareTo(loweredName) == 0 && tempArtist.compareTo(loweredArtist) == 0) {
				//found a node with the same name and artist as parameter node//
				
				return i; //will always be 0 or greater//
			}
			
		}
		
		return -1; //did not find//
		
	}
	
	
	private int findCorrectIndex(String name, String artist) {
		int index = 0;
		String combined = name;
		combined = combined.concat(artist);
		
		for(int i = 0; i<songList.getItems().size(); i++) {
			String tempCombined = songList.getItems().get(i).name;
			tempCombined = tempCombined.concat(songList.getItems().get(i).artist);
			
			if(combined.compareToIgnoreCase(tempCombined) < 0) {
				return i;
				
			}
			index++;
		}

		return index;
	}
	
	
}
