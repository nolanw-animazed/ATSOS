package data;

/**
 * Item Container Class for Items within the Signout System
 * @author Nolan Wright
 *
 */
public class ATSOSItem {

	/** Name of the Item within the System */
	private String itemName;
	/** Barcode for the Item [Printed Barcode on the Item] **/
	private int barcode;
	/** Auth Level is used to establish if a user can grab the item */
	private int authLevel;
	/** Current Location of the Item */
	private String location;
	/** Used to show if the Item is signedOut*/
	private boolean signedOut;
	/** Person that Signed it Out */
	private String signedOutPerson;
	/** ID of Person that SignedOut the Item */
	private int signedOutPersonID;
	/** Time at which the Item was Signed Out */
	private String signedOutTime;
	/** Time when the Item should be back by */
	private String assignedSignInTime;
	/** Details on the Signout for the Item */
	private String signedOutDetails;
	
	public ATSOSItem(String itemName, int barcode) {
		this.itemName = itemName;
		this.barcode = barcode;
		location = "Unassigned";
		signedOutPerson = "Nobody";
		signedOutPersonID = 0;
		signedOutTime = "N/A";
		assignedSignInTime = "N/A";
		signedOutDetails = "N/A";
		signedOut = false;
		//Unimplemented as of now
		authLevel = 0;
	}
	
	public ATSOSItem(String[] inputArray) {
		itemName = inputArray[0];
		barcode = Integer.parseInt(inputArray[1]);
		authLevel = Integer.parseInt(inputArray[2]);
		signedOut = Boolean.parseBoolean(inputArray[3]);
		location = inputArray[4];
		signedOutPerson = inputArray[5];
		signedOutPersonID = Integer.parseInt(inputArray[6]);
		signedOutTime = inputArray[7];
		assignedSignInTime = inputArray[8];
		signedOutDetails = inputArray[9];
	}
	
	/**
	 * Used to display very basic item data for GUI
	 * @return String with Item Name, Barcode, and Location if Needed
	 */
	public String displayItemData() {
		String output = itemName + " | #" + barcode;
		if(!signedOut) {
			output = output + " | Item is not Signed Out";
		} else {
			output = output + " | [" +location + "] ";
		}
		return output;
	}
	
	//@TODO yea this isn't done
	public String displayAvailableInfo() {
		if(signedOut) {
			return null;
		}
		String output = itemName + " | #" + barcode + " | Item is not Signed Out";
		return output;
	}
	

	//@TODO yea this isn't done
	public String displaySignedOutInfo() {
		if(!signedOut) {
			return null;
		}
		String output = itemName + "| #" + barcode;
		output = output + "| [" +location + "] ";
		return output;
	}
	
	/**
	 * Used for File Saving of Items.
	 * @return A String Array Version of the Item for CSV Writing
	 */
	public String[] outputString() {
		String[] output = new String[10];
		
		output[0] = itemName;
		output[1] = Integer.toString(barcode);
		output[2] = Integer.toString(authLevel);
		output[3] = Boolean.toString(signedOut);
		output[4] = location;
		output[5] = signedOutPerson;
		output[6] = Integer.toString(signedOutPersonID);
		output[7] = signedOutTime;
		output[8] = assignedSignInTime;
		output[9] = signedOutDetails;
		
		return output;
	}
	
	/**
	 * Used to Signout an Item from the System, given a User's Auth Status
	 * @param signOutUser User wanting to SignOut Item
	 * @param signInTime Time Given to Sign the Item Back in
	 * @param details Details for Item Sign Out
	 * @return True or False if the Item can be checkedOut
	 */
	public boolean signOutItem(ATSOSUser signOutUser, String signInTime, String details) {
		if(authLevel > signOutUser.getAuthLevel()) {
			return false;
		}
		return true;
	}

	/**
	 * Get Item Name
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Set Item Name
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Get Barcode
	 * @return the barcode
	 */
	public int getBarcode() {
		return barcode;
	}

	/**
	 * Set Barcode
	 * @param barcode the barcode to set
	 */
	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}

	/**
	 * Get Location
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Set Location
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the SignedOut Boolean
	 * @return the signedOut
	 */
	public boolean isSignedOut() {
		return signedOut;
	}

	/**
	 * Sets the SignedOut Boolean
	 * @param signedOut the signedOut to set
	 */
	public void setSignedOut(boolean signedOut) {
		this.signedOut = signedOut;
	}

	/**
	 * Gets the Signed Out Person
	 * @return the signedOutPerson
	 */
	public String getSignedOutPerson() {
		return signedOutPerson;
	}

	/**
	 * Sets the Signed Out Person
	 * @param signedOutPerson the signedOutPerson to set
	 */
	public void setSignedOutPerson(String signedOutPerson) {
		this.signedOutPerson = signedOutPerson;
	}

	/**
	 * Gets the Signed Out Time
	 * @return the signedOutTime
	 */
	public String getSignedOutTime() {
		return signedOutTime;
	}

	/**
	 * Sets the Signed Out Time
	 * @param signedOutTime the signedOutTime to set
	 */
	public void setSignedOutTime(String signedOutTime) {
		this.signedOutTime = signedOutTime;
	}

	/**
	 * Gets the Assigned Return Time
	 * @return the assignedSignInTime
	 */
	public String getAssignedSignInTime() {
		return assignedSignInTime;
	}

	/**
	 * Sets the Assigned Return Time
	 * @param assignedSignInTime the assignedSignInTime to set
	 */
	public void setAssignedSignInTime(String assignedSignInTime) {
		this.assignedSignInTime = assignedSignInTime;
	}

	/**
	 * Gets the Signed Out Details
	 * @return the signedOutDetails
	 */
	public String getSignedOutDetails() {
		return signedOutDetails;
	}

	/**
	 * Sets the Signed Out Details
	 * @param signedOutDetails the signedOutDetails to set
	 */
	public void setSignedOutDetails(String signedOutDetails) {
		this.signedOutDetails = signedOutDetails;
	}

	/**
	 * Getter for the SignedOutPersonID
	 * @return the signedOutPersonID
	 */
	public int getSignedOutPersonID() {
		return signedOutPersonID;
	}

	/**
	 * Setter for the SignedOutPersonID
	 * @param signedOutPersonID the signedOutPersonID to set
	 */
	public void setSignedOutPersonID(int signedOutPersonID) {
		this.signedOutPersonID = signedOutPersonID;
	}

	/**
	 * Getter for AuthLevel on Item
	 * @return the authLevel
	 */
	public int getAuthLevel() {
		return authLevel;
	}
	
}
