package controller;

import java.util.HashMap;

import discordbot.ATSOSDiscordCommunication;
import io.ATSOSIO;
import view.ATSOSGUI;

/**
 * 
 * @author Nolan Wright
 *
 */
public class ATSOSController {

	ATSOSDiscordCommunication discordMessanger;
	
	HashMap<String, String> optionsData;
	
	ATSOSGUI gui;
	
	/**
	 * 
	 */
	public ATSOSController() {
		discordMessanger = null;
		
		//0 Differs to Pick File
		//1 Differs to the Set Location
		int mode = 1;
		
		if(mode == 0) {
			optionsData = ATSOSIO.processOptionsFile(ATSOSGUI.pickFile());
		} else {
			optionsData = ATSOSIO.processOptionsFile("src/main/resources/options.properties");
		}
		
		gui = new ATSOSGUI(this);
	}
	
	/**
	 * 
	 */
	public void initialize() {
		discordMessanger = new ATSOSDiscordCommunication(optionsData.get("DiscordAPIToken"));
		gui.initialize();
	}
}
