package com.modwiz.ld31.leveleditor;

import java.io.File;
import com.modwiz.ld31.entities.Player;
import com.modwiz.ld31.world.GameWorld;
import com.modwiz.ld31.utils.assets.LevelLoader;

/**
*	Manages the currently opened file for the level editor.
*/
public class FileManager {
	
	/**
	* The file location of the level that is currently being edited.
	*/
	private File current;
	
	/**
	* Create a new FileManager.
	*/
	public FileManager() {
		current = null;
	}
	
	/**
	* Saves the level to the current file.
	* @param world The game world to save to the current file.
	*/
	public void save(GameWorld world) {
		if (current!=null) {
			LevelLoader.saveLevel(current, world);
		}
	}
	
	/**
	* @return whether or not the FileManager is storing a file location.
	*/
	public boolean hasCurrent() {
		return current != null;
	}
	
	/**
	*	@return the currently stored file location
	*/
	public File getCurrent() {
		return current;
	}
	
	/**
	* Clears the currently saved file location
	*/
	public void clear() {
		current = null;
	}
	
	/**
	* Sets the current file location and saves the level to it.
	* @param file The new file location
	* @param world The level to save at the given file
	*/
	public void saveAs(File file, GameWorld world) {
		current = file.getAbsoluteFile();
		save(world);
	}
	
	/**
	* Loads the level at the given file location and makes it the current file.
	* @param file The file that contains the level that will be loaded
	* @return the loaded GameWorld
	*/
	public GameWorld open(File file) {
		current = file.getAbsoluteFile();
		GameWorld w = LevelLoader.getLevelFromFile(current);
		w.getDimension("MainDimension").addObject(Player.getSingleton());
		return w;
	}
}