/*
 * @Parth Shah
 * 
 * @Benjamin Pasternak
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
