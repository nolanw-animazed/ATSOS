package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvException;

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
			if(splitOptions.length > 1) {
				optionsOutput.put(splitOptions[0], splitOptions[1]);
			} else {
				optionsOutput.put(splitOptions[0], "");
			}
			
		}
		
		return optionsOutput;
	}
	
	/**
	 * Used to read in simple CSV Data from Files within the Program
	 * Taken from the RRT Project
	 * @param fileLocation Filelocation of the file
	 * @return Returns an ArrayList of the File Data
	 */
	public static List<String[]> readFileCSVData(String fileLocation) {
		List<String[]> myEntries = null;
		try {
			CSVReader reader = new CSVReaderBuilder(new FileReader(fileLocation)).build();
			myEntries = reader.readAll();
		    reader.close();
		} catch (FileNotFoundException e) {
			//Make the List not Null
			myEntries = new ArrayList<String[]>();
			//File Not Made Yet
			return myEntries;
		} catch (IOException | CsvException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return myEntries;
	}
	
	/**
	 * Used to write data to CSV Files from within the Program
	 * Taken from the RRT Project
	 * @param myEntries List of String[] Data from the program
	 * @param fileLocation FileLocation used for saving.
	 */
	public static void writeFileCSVData(List<String[]> myEntries, String fileLocation) {
		try {
			ICSVWriter writer = new CSVWriterBuilder(new FileWriter(fileLocation)).build();
			writer.writeAll(myEntries);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
