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
		
		usersList.sort(null);
		
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

	/**
	 * Used by the Discord Bot to Request allTech Info from the System
	 * @return Returns a concated String of all the Tech Info for the System
	 */
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
	
	/**
	 * Used to get all the Tech, so Full Lists can be Made
	 * @return Returns an Array of all ATSOSItems.
	 */
	public List<ATSOSItem> getAllTechInfo() {
		ArrayList<ATSOSItem> allTech =  new ArrayList<ATSOSItem>();
		
		for(int i = 0; i < itemsList.size(); i++) {
			allTech.add(itemsList.get(i));
		}
		
		return allTech;
	}

	/**
	 * Used by the GUI to fulfill the SignedIn Tech ScrollBar.
	 * @return Returns an Array of all SignedIn Items with their Info
	 */
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

	/**
	 * Saves the Data for the Project [Currently just to a File]
	 * @TODO ?if I do SQL?
	 */
	public void saveData() {
		saveItemDataToCSVFiles();
		saveUserDataToCSVFiles();
	}
	
	/**
	 * Saves Item Data to a CSV File based off the OptionsFile
	 */
	public void saveItemDataToCSVFiles() {
		ArrayList<String[]> output = new ArrayList<String[]>();
		
		//Create the Header than fill the List
		output.add(getItemHeader());
		
		for(int i = 0; i < itemsList.size(); i++) {
			output.add(itemsList.get(i).outputString());
		}
		
		ATSOSIO.writeFileCSVData(output, optionsData.get("itemDataFileLocation"));
	}

	/**
	 * Creates a header for the Item CSV Output, used for alignment.
	 * @return Header for Item CSV
	 */
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
	
	/**
	 * Used to Save User Data to a CSVFile given the location in the optionsFile
	 */
	public void saveUserDataToCSVFiles() {
		ArrayList<String[]> usersListOutput = new ArrayList<String[]>();
		
		//Header is created in method 
		//[it's shorter than the item one by over half so I'm not refactoring it for now]
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

	/**
	 * Used to add Users to the System, Called from the GUI
	 * @param userName UserName for the User
	 * @param password UnHashedPassword for the User [Given by the User]
	 * @param authLevel AuthLevel for the User [Given by Admin]
	 * @return Returns true if the User was Added, and False if the user was rejected
	 * @TODO needs to do some checking
	 */
	public boolean addUser(String userName, String password, String authLevel) {
		ATSOSUser user = new ATSOSUser(userName, password, Integer.parseInt(authLevel));
		usersList.add(user);
		usersList.sort(null);
		//@TODO maybe make sure we aren't just duplictating accounts.
		return true;
	}

	/**
	 * Used to Attempt a User Signin Given a Password [Only the Password is given, the system tries to find the user]
	 * @param unhashedPassword Password given by the User for their account
	 * @return Returns the User if found, but if they aren't found it returns null.
	 */
	public ATSOSUser attemptSignInViaPassword(String unhashedPassword) {
		for(int i = 0; i < usersList.size(); i++) {
			if(usersList.get(i).checkPassword(unhashedPassword)) {
				return usersList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Used to Directly send a message through the discord.
	 * Called by the GUI to send sign in and sign out notices.
	 * @param message
	 */
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

	public List<ATSOSUser> getDeletableUsers(ATSOSUser loggedInUser) {
		ArrayList<ATSOSUser> deletableUsers = new ArrayList<ATSOSUser>();
		
		for(int i = 0; i < usersList.size(); i++) {
			ATSOSUser user = usersList.get(i);
			if(user.getAuthLevel() > loggedInUser.getAuthLevel()) {
				continue;
			}
			deletableUsers.add(user);
		}
		
		return deletableUsers;
	}

	public void deleteUser(ATSOSUser user, ATSOSUser loggedInUser) {
		
		if(loggedInUser.getAuthLevel() <= user.getAuthLevel()) {
			// @TODO Throw an Error Maybe
			return;
		}
		
		usersList.remove(user);
	}
}
