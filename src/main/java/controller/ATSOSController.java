package controller;

import java.util.HashMap;

import discordbot.ATSOSDiscordCommunication;
import io.ATSOSIO;

/**
 * 
 * @author Nolan Wright
 *
 */
public class ATSOSController {

	ATSOSDiscordCommunication discordMessanger;
	
	HashMap<String, String> optionsData;
	
	/**
	 * 
	 */
	public ATSOSController() {
		discordMessanger = null;
		optionsData = ATSOSIO.processOptionsFile("src/main/resources/options.properties");
		//@TODO
	}
	
	/**
	 * 
	 */
	public void initialize() {
		discordMessanger = new ATSOSDiscordCommunication(optionsData.get("DiscordAPIToken"));
	}
}
