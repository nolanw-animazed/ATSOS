package controller;

import java.util.ArrayList;
import java.util.HashMap;

import data.ATSOSItem;
import data.ATSOSUser;
import discordbot.ATSOSDiscordCommunication;
import io.ATSOSIO;
import view.ATSOSGUI;

/**
 * 
 * @author Nolan Wright
 *
 */
public class ATSOSController {
	
	/** Used to reply to discord if needed. */
	private ATSOSDiscordCommunication discordMessanger;
	
	private HashMap<String, String> optionsData;
	
	private ATSOSGUI gui;
	
	private ArrayList<ATSOSItem> itemsList;
	
	private ArrayList<ATSOSUser> usersList;
	
	/**
	 * 
	 */
	public ATSOSController() {
		//0 Differs to Pick File
		//1 Differs to the Set Location
		int mode = 1;
		
		if(mode == 0) {
			optionsData = ATSOSIO.processOptionsFile(ATSOSGUI.pickFile());
		} else {
			optionsData = ATSOSIO.processOptionsFile("src/main/resources/options.properties");
		}
		
		gui = new ATSOSGUI(this);
		discordMessanger = new ATSOSDiscordCommunication(optionsData.get("DiscordAPIToken"), this);
	}
	
	/**
	 * 
	 */
	public void initialize() {
		gui.initialize();
	}

	public String signedOutTech() {
		String output = "signedOutTech() is not complete";
		return output;
	}

	public String allTech() {
		String output = "allTech() is not complete";
		return output;
	}
}
