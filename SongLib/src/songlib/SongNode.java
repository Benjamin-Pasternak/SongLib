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
 * 
 */

package songlib;

public class SongNode {
	String name;
	String artist;
	String year;
	String album;
	
	
	public SongNode(String name, String artist, String year, String album) {
		this.name = name;
		this.artist = artist;
		this.year = year;
		this.album = album;
		
	}
	
	@Override
	public String toString() {
		return "Name: " + name + " - Artist: " + artist;
	}
}
