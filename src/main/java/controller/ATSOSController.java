package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
	/** GUI for the project */
	private ATSOSGUI gui;
	/** List for the ATSOSItems */
	private ArrayList<ATSOSItem> itemsList;
	/** List for the ATSOSUsers */
	private ArrayList<ATSOSUser> usersList;
	
	/**
	 * Normal Constructor for the Controller
	 */
	public ATSOSController() {
		//0 Differs to Pick File
		//1 Differs to the Set Location
		int fileMode = 1;
		//0 Discord Enabled
		//1 Discord UnEnabled [helps with testing]
		boolean discordMode = true;
		
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
		
		usersList = new ArrayList<ATSOSUser>();
		
		if(optionsData.get("SQL").toLowerCase().equals("false") && (optionsData.get("userDataFileLocation") != null && !optionsData.get("userDataFileLocation").equals(""))) {
			List<String[]> userData = ATSOSIO.readFileCSVData(optionsData.get("userDataFileLocation"));
			
			for(int i = 1; i < userData.size(); i++) {
				ATSOSUser item = new ATSOSUser(userData.get(i));
				usersList.add(item);
			}
		}
		
		//Set up the GUI and initialize the Discord Bot
		gui = new ATSOSGUI(this);
		if(discordMode) {
			discordMessanger = new ATSOSDiscordCommunication(optionsData.get("DiscordAPIToken"), this);
		}
	}
	
	/**
	 * Starts the Program
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

	public List<String> getAllAvailableTechInfo() {
		ArrayList<String> allAvailableTechInfo =  new ArrayList<String>();
		
		for(int i = 0; i < itemsList.size(); i++) {
			String itemData = itemsList.get(i).displayAvailableInfo();
			if(itemData == null) {
				continue;
			}
			allAvailableTechInfo.add(itemData);
		}
		
		return allAvailableTechInfo;
	}

	public void saveData() {
		saveItemDataToCSVFiles();
		saveUserDataToCSVFiles();
	}
	
	public void saveItemDataToCSVFiles() {
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
	
	public void saveUserDataToCSVFiles() {
		ArrayList<String[]> usersListOutput = new ArrayList<String[]>();
		String[] usersListHeader = new String[4];
		usersListHeader[0] = "UserName";
		usersListHeader[1] = "Id";
		usersListHeader[2] = "Password";
		usersListHeader[3] = "AuthLevel";
		usersListOutput.add(usersListHeader);
		
		for(int i = 0; i < usersList.size(); i++) {
			usersListOutput.add(usersList.get(i).outputArray());
		}
		
		ATSOSIO.writeFileCSVData(usersListOutput, optionsData.get("userDataFileLocation"));
	}

	public boolean addUser(String userName, String password, String authLevel) {
		ATSOSUser user = new ATSOSUser(userName, password, Integer.parseInt(authLevel));
		usersList.add(user);
		//@TODO maybe make sure we aren't just duplictating accounts.
		return true;
	}

	public ATSOSUser attemptSignInViaPassword(String unhashedPassword) {
		for(int i = 0; i < usersList.size(); i++) {
			if(usersList.get(i).checkPassword(unhashedPassword)) {
				return usersList.get(i);
			}
		}
		return null;
	}
	
	public void sendMessageToDiscord(String message) {
		discordMessanger.sendMessage(message);
	}

	public ATSOSItem addTech(String message) {
		if(message == null || message.equals("")) {
			throw new IllegalArgumentException("Please add a name for the Item");
		}
		Random r = new Random();
		
		int barcode = -1;
		boolean checkForDupes = true;
		do {
			barcode = r.nextInt(10000000);
			checkForDupes = false;
			
			for(int i = 0; i < itemsList.size(); i++) {
				if(barcode == itemsList.get(i).getBarcode()) {
					//Dupe was found
					checkForDupes = true;
				}
			}
		} while (checkForDupes);
		
		ATSOSItem item = new ATSOSItem(message, barcode);
		itemsList.add(item);
		
		return item;
	}
}
