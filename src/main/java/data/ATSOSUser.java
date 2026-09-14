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
	 */
	public ATSOSUser(String userName, String unHashedPassword, int authLevel) {
		setUserName(userName);
		setUpPassword(unHashedPassword);
		this.authLevel = authLevel;
	}
	
	/**
	 * Constructor used for ATSOSUser when Data is being pulled from a File
	 * @param userData UserData from the File
	 */
	public ATSOSUser(String[] userData) {
		setUserName(userData[0]);
		id = Integer.parseInt(userData[1]);
		hashedPassword = userData[2];
		authLevel = Integer.parseInt(userData[3]);
	}
	
	/**
	 * Used to setup the Password for the User. It will take in a hashed password, and hash it if it's an acceptable password.
	 * @throws Throws an Exception if the Password is null, empty, or less than 4 characters.
	 * @param unHashedPassword Given Password by the User
	 */
	private void setUpPassword(String unHashedPassword) {
		if(unHashedPassword == null || unHashedPassword.equals("")) {
			throw new IllegalArgumentException("Password cannot be empty or null");
		}
		if(unHashedPassword.length() < 4) {
			throw new IllegalArgumentException("Please make your password 4 characters or more");
		}
		hashedPassword = BCrypt.hashpw(unHashedPassword, BCrypt.gensalt());
	}
	
	/**
	 * Output Array for the User (Used for Data Output and List Views)
	 * @return Returns an Array of [UserName, ID, HashedPW, and AuthLevel]
	 */
	public String[] outputArray() {
		String[] userOutputList = new String[4];
		userOutputList[0] = userName;
		userOutputList[1] = Integer.toString(id);
		userOutputList[2] = hashedPassword;
		userOutputList[3] = Integer.toString(authLevel);
		return userOutputList;
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
		if(userName == null || userName.equals("")) {
			throw new IllegalArgumentException("Username cannot be empty or null");
		}
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
	public void setAuthLevel(int authLevel, ATSOSUser higherUser, String unHashedPassword) {
		if(authLevel == 0) {
			this.authLevel = 0;
		} else if(higherUser.getAuthLevel() < authLevel) {
			throw new IllegalArgumentException("User does not have access to authorize this.");
		} else if(higherUser.checkPassword(unHashedPassword)) {
			throw new IllegalArgumentException("The user password seems to be incorrect.");
		}
		this.authLevel = authLevel;
	}

	/**
	 * Getter for Auth Level
	 * @return authLevel for the User
	 */
	public int getAuthLevel() {
		return authLevel;
	}

}
