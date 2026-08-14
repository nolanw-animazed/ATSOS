package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	/** Options data for the Program*/
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
		int fileMode = 1;
		//0 Discord Enabled
		//1 Discord UnEnabled [helps with testing]
		boolean discordMode = false;
		
		if(fileMode == 0) {
			optionsData = ATSOSIO.processOptionsFile(ATSOSGUI.pickFile());
		} else {
			optionsData = ATSOSIO.processOptionsFile("src/main/resources/options.properties");
		}
		
		itemsList = new ArrayList<ATSOSItem>();
		
		if(optionsData.get("SQL").toLowerCase().equals("false") && (optionsData.get("itemDataFileLocation") != null && !optionsData.get("itemDataFileLocation").equals(""))) {
			List<String[]> itemData = ATSOSIO.readFileCSVData(optionsData.get("itemDataFileLocation"));
			
			for(int i = 1; i < itemData.size(); i++) {
				ATSOSItem item = new ATSOSItem(itemData.get(i));
				itemsList.add(item);
			}
		}
		
		gui = new ATSOSGUI(this);
		if(discordMode) {
			discordMessanger = new ATSOSDiscordCommunication(optionsData.get("DiscordAPIToken"), this);
		}
	}
	
	/**
	 * 
	 */
	public void initialize() {
		gui.initialize();
	}

	public String signedOutTech() {
		String output = "";
		
		if(itemsList.size() == 0) {
			return "There are no items in the system. Please add Items via the GUI.";
		}
		
		for(int i = 0; i < itemsList.size(); i++) {
			if(itemsList.get(i).isSignedOut()) {
				output = output + itemsList.get(i).displayItemData();
			}
		}
		
		if(output.equals("")) {
			return "No Items are currently Signed Out.";
		}
		
		return output;
	}

	public String allTech() {
		String output = "";
		
		if(itemsList.size() == 0) {
			return "There are no items in the system. Please add Items via the GUI.";
		}
		
		for(int i = 0; i < itemsList.size(); i++) {
			output = output + itemsList.get(i).displayItemData() + "\n";
		}
		
		return output;
	}
	
	public void saveDataToCSVFiles() {
		ArrayList<String[]> output = new ArrayList<String[]>();
		
		output.add(getItemHeader());
		
		for(int i = 0; i < itemsList.size(); i++) {
			output.add(itemsList.get(i).outputString());
		}
		
		ATSOSIO.writeFileCSVData(output, optionsData.get("itemDataFileLocation"));
	}

	private String[] getItemHeader() {
		String[] output = new String[10];
		
		output[0] = "ItemName";
		output[1] = "Barcode";
		output[2] = "AuthLevel";
		output[3] = "SignedOut";
		output[4] = "location";
		output[5] = "SignedOutPerson";
		output[6] = "SignedOutPersonID";
		output[7] = "SignedOutTime";
		output[8] = "AssignedSignInTime";
		output[9] = "SignedOutDetails";
		
		return output;
	}

	public List<String> getAllTechInfo() {
		ArrayList<String> allTechInfo =  new ArrayList<String>();
		
		for(int i = 0; i < itemsList.size(); i++) {
			allTechInfo.add(itemsList.get(i).displayItemData());
		}
		
		return allTechInfo;
	}

	public List<String> getSignedInInfo() {
		ArrayList<String> signedInTechInfo =  new ArrayList<String>();
		
		for(int i = 0; i < itemsList.size(); i++) {
			String itemData = itemsList.get(i).displaySignedOutInfo();
			if(itemData == null) {
				continue;
			}
			signedInTechInfo.add(itemData);
		}
		return signedInTechInfo;
	}
}
