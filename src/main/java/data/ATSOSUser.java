package data;

import org.mindrot.jbcrypt.BCrypt;

public class ATSOSUser {
	
	/** UserName for the User */
	private String userName;
	/** Generated ID for the User */
	private int id;
	/** Hashed Password for the User, Original Password isn't stored */
	private String hashedPassword;
	/** Auth Level is used to establish if a user can grab an item */
	private int authLevel;
	
	/**
	 * Standard Constructor for the User Class
	 * @param userName Username for the User
	 * @param unHashedPassword Password for the User [Will be Hashed]
	 * @param authLevel AuthLevel for the User
	 */
	public ATSOSUser(String userName, String unHashedPassword, int authLevel) {
		this.userName = userName;
		this.authLevel = authLevel;
		hashedPassword = BCrypt.hashpw(unHashedPassword, BCrypt.gensalt());
	}
	
	/**
	 * Used to check the password for the User against the HashedPassword
	 * @param unHashedPassword Given Password from the User
	 * @return T/F If the Password fails or not.
	 */
	public boolean checkPassword(String unHashedPassword) {
		return BCrypt.checkpw(unHashedPassword, hashedPassword);
	}

	/**
	 * Get User Name
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set User Name
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Get ID
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set ID
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Set Auth Level for User
	 * @param authLevel the authLevel to set
	 */
	public boolean setAuthLevel(int authLevel, ATSOSUser higherUser, String unHashedPassword) {
		if(higherUser.getAuthLevel() < authLevel) {
			return false;
		}
		if(higherUser.checkPassword(unHashedPassword)) {
			return false;
		}
		this.authLevel = authLevel;
		return true;
	}

	/**
	 * Getter for Auth Level
	 * @return authLevel for the User
	 */
	public int getAuthLevel() {
		return authLevel;
	}

}
