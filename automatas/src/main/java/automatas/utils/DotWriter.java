package automatas.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DotWriter {
	

	public  static void writeToFile(String path, String dotCode) throws Exception {
		try {
	        File newTextFile = new File(path);
	        FileWriter fw = new FileWriter(newTextFile);
	        fw.write(dotCode);
	        fw.close();
		} catch (IOException io) {
			throw io;
	    }
	}
	
	
}
