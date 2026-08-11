package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * ATSOSIO is used for File Interactions within the ATSOS System.
 * @author Nolan Wright
 *
 */
public class ATSOSIO {
	
	/**
	 * Used to parse out the optionsFile for the program.
	 * @param dataFile The Given Options DataFile Absolute Path
	 * @return Returns a Hashmap with the Options contained inside
	 */
	public static HashMap<String, String> processOptionsFile(String dataFile) {
		HashMap<String, String> optionsOutput = new HashMap<String, String>();
		List<String> optionsFile = null;
		
		try {
			optionsFile = Files.readAllLines(Paths.get(dataFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String optionsLine : optionsFile) {
			String[] splitOptions = optionsLine.split("=");
			switch (splitOptions[0]) {
				case ("DiscordAPIToken") :
					optionsOutput.put(splitOptions[0], splitOptions[1]);
			};
		}
		
		return optionsOutput;
	}
}
