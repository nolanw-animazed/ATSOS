package data;

/**
 * Item Container Class for Items within the Signout System
 * @author Nolan Wright
 *
 */
public class ATSOSItem {

	/** Name of the Item within the System */
	private String itemName;
	/** Barcode for the Item [Printed Barcode on the Item]
	    It's a string because there could be letters or numbers */
	private String barcode;
	/** Auth Level is used to establish if a user can grab the item */
	private int authLevel;
	/** Current Location of the Item */
	private String location;
	/** Used to show if the Item is signedOut*/
	private boolean signedOut;
	/** Person that Signed it Out */
	private String signedOutPerson;
	/** ID of Person that SignedOut the Item */
	private String signedOutPersonID;
	/** Time at which the Item was Signed Out */
	private String signedOutTime;
	/** Time when the Item should be back by */
	private String assignedSignInTime;
	/** Details on the Signout for the Item */
	private String signedOutDetails;
	
	public ATSOSItem(String itemName, String barcode) {
		this.itemName = itemName;
		this.barcode = barcode;
		location = "Unassigned";
		signedOutPerson = "Nobody";
		signedOutTime = "N/A";
		assignedSignInTime = "N/A";
		signedOutDetails = "N/A";
		signedOut = false;
		//Unimplemented as of now
		authLevel = 0;
	}
	
	/**
	 * Used to apply a specific barcode to an item
	 * @TODO [I may just move this into the constructor, I'll have to see on implementation]
	 * @param barcode
	 */
	public void barcodeItem(String barcode) {
		this.barcode = barcode;
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
	public String getBarcode() {
		return barcode;
	}

	/**
	 * Set Barcode
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
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
	public String getSignedOutPersonID() {
		return signedOutPersonID;
	}

	/**
	 * Setter for the SignedOutPersonID
	 * @param signedOutPersonID the signedOutPersonID to set
	 */
	public void setSignedOutPersonID(String signedOutPersonID) {
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
