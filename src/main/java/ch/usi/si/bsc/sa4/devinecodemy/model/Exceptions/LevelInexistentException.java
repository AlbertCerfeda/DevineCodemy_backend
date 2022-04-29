package ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions;

public class LevelInexistentException extends RuntimeException {
	public LevelInexistentException() {
		super("Level does not exist !");
	}
	
	public LevelInexistentException(int levelNumber){
		super("Level #"+levelNumber+" does not exist !");
	}
}
