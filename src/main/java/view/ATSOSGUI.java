package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.ModuleLayer.Controller;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ATSOSController;
import data.ATSOSItem;
import data.ATSOSUser;

/**
 * GUI for the ATSOS, mainly in charge of working on the front end.
 * @author Nolan Wright
 *
 */
public class ATSOSGUI {
	
	/** Main Controller for the Program, focused on handling the data for the program*/
	private ATSOSController controller;
	
	/** Panel stored for regeneration purposes */
	private JPanel innerBottomRightPanel;
	/** Scrollable Panel stored for regeneration purposes */
	private JScrollPane bottomRightPanel;
	/** Panel stored for regeneration purposes */
	private JPanel innerBottomLeftPanel;
	/** Scrollable Panel stored for regeneration purposes */
	private JScrollPane bottomLeftPanel;
	/** Panel stored for regeneration purposes */
	private JPanel topLeftPanel;
	/** Panel stored for regeneration purposes */
	private JPanel topRightPanel;
	/** Label for the User Name LoggedIn */
	private JLabel userNameLabel;
	/** User Currently LoggedIn */
	private ATSOSUser loggedInUser;
	/** Main Frame for the project, stored for regeneration purposes */
	private JFrame mainFrame;	

	/**
	 * Main Constructor for the GUI, doesn't initiate anything, just used to start everything to null.
	 */
	public ATSOSGUI(ATSOSController controller) {
		this.controller = controller;
		mainFrame = null;
		innerBottomRightPanel = null;
		bottomRightPanel = null;
		innerBottomLeftPanel = null;
		bottomLeftPanel = null;
		topLeftPanel = null;
		topRightPanel = null;
	}
	
	/**
	 * Initialize Protocol called from the Controller to start the GUI.
	 * This is used to stall the program for any file inputs as needed.
	 */
	public void initialize() {
		mainFrame = new JFrame("ATSOS");
		
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the program?", "Confrimation" , JOptionPane.YES_NO_OPTION);
				if(answer == 0) {
					controller.sendMessageToDiscord("System is shutting down.");
					controller.saveData();
					System.exit(0);
				}
			}
		});
		
		mainFrame.setSize(800, 600);
		
		mainFrame.setLayout(new GridLayout(2,2));
		
		//Used to setup Top Left Panel
		setUpTopLeftPanel();
		
		setUpTopRightPanel();
		
		JPanel outerTopLeftPanel = new JPanel();
		outerTopLeftPanel.setLayout(new BorderLayout());
		JLabel normalUserActions = new JLabel("User Sign In and Actions Area", SwingConstants.CENTER);
		outerTopLeftPanel.add(normalUserActions, BorderLayout.PAGE_START);
		outerTopLeftPanel.add(topLeftPanel, BorderLayout.CENTER);
		
		JPanel outerTopRightPanel = new JPanel();
		outerTopRightPanel.setLayout(new BorderLayout());
		JLabel adminUserActions = new JLabel("Admin Actions Area", SwingConstants.CENTER);
		outerTopRightPanel.add(adminUserActions, BorderLayout.PAGE_START);
		outerTopRightPanel.add(topRightPanel, BorderLayout.CENTER);
		
		
		//Set up the ScrollPanes for Data
		JPanel outerBottomLeftPanel = new JPanel();
		outerBottomLeftPanel.setLayout(new BorderLayout());
		JLabel availableTechTechLabel = new JLabel("Tech Currently Signed In", SwingConstants.CENTER);
				
		outerBottomLeftPanel.add(availableTechTechLabel, BorderLayout.PAGE_START);
		
		innerBottomLeftPanel = new JPanel();
		bottomLeftPanel = new JScrollPane(innerBottomLeftPanel);
		
		outerBottomLeftPanel.add(bottomLeftPanel, BorderLayout.CENTER);
		
		bottomLeftPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Set up the ScrollPanes for Data
		JPanel outerBottomRightPanel = new JPanel();
		outerBottomRightPanel.setLayout(new BorderLayout());
		JLabel signedOutTechLabel = new JLabel("<html>Tech Currently Signed Out</html>", SwingConstants.CENTER);
		
		outerBottomRightPanel.add(signedOutTechLabel, BorderLayout.PAGE_START);
		
		innerBottomRightPanel = new JPanel();
		bottomRightPanel = new JScrollPane(innerBottomRightPanel);
		
		outerBottomRightPanel.add(bottomRightPanel, BorderLayout.CENTER);
		
		bottomRightPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		setUpBottomPanels();
		
		mainFrame.add(outerTopLeftPanel);
		mainFrame.add(outerTopRightPanel);
		mainFrame.add(outerBottomLeftPanel);
		mainFrame.add(outerBottomRightPanel);
		
		regenerateWindow();
		
		controller.sendMessageToDiscord("System has started up.");
	}

	/**
	 * Used to Initialize all the Buttons for the Top Left User Panel
	 */
	private void setUpTopLeftPanel() {
		topLeftPanel = new JPanel();
		
		topLeftPanel.setLayout((new GridLayout(4,2)));
		
		JButton userSignIn = new JButton("<html>User Sign In</html>");
		JButton userSignOut = new JButton("<html>User Sign Out</html>");
		JButton userDetailsButton = new JButton("<html>User Details</html>");
		JButton techSignIn = new JButton("<html>Tech Sign In</html>");
		JButton techSignOut = new JButton("<html>Tech Sign Out</html>");
		JButton viewAllTech = new JButton("<html>View All Tech</html>");
		JButton searchTech = new JButton("<html>Search Tech</html>");
		
		userNameLabel = new JLabel("<html>No User is Signed In</html>", SwingConstants.CENTER);
		
		userSignIn.addActionListener(e -> {
			userSignIn();
		});
		
		userSignOut.addActionListener(e -> {
			userSignOut();
		});
		
		userDetailsButton.addActionListener(e -> {
			showError("userDetailsButton is unemplemented atm.");
		});
		
		techSignIn.addActionListener(e -> {
			showError("techSignIn is unemplemented atm.");
		});
		
		techSignOut.addActionListener(e -> {
			showError("techSignOut is unemplemented atm.");
		});
		
		viewAllTech.addActionListener(e -> {
			showError("viewAllTech is unemplemented atm.");
		});
		
		searchTech.addActionListener(e -> {
			showError("searchTech is unemplemented atm.");
		});
		
		topLeftPanel.add(userSignIn);
		topLeftPanel.add(userSignOut);
		topLeftPanel.add(userNameLabel);
		topLeftPanel.add(userDetailsButton);
		topLeftPanel.add(techSignIn);
		topLeftPanel.add(techSignOut);
		topLeftPanel.add(viewAllTech);
		topLeftPanel.add(searchTech);
	}

	/**
	 * Used to Initialize the Top Right Panel
	 */
	private void setUpTopRightPanel() {
		topRightPanel = new JPanel();
		
		topRightPanel.setLayout((new GridLayout(3,2)));
		
		JButton addTech = new JButton("<html>Add Tech to the System</html>");
		JButton addUser = new JButton("<html>Add User to the System</html>");
		JButton removeTech = new JButton("<html>Remove Tech from the System</html>");
		JButton removeUser = new JButton("<html>Remove User from the System</html>");
		JButton reprintBarcode = new JButton("<html>Reprint Barcodes for an Item</html>");
		JButton forceItemSignIn = new JButton("<html>Forced Tech Sign In</html>");
		
		addTech.addActionListener(e -> {
			addTech();
		});
		
		addUser.addActionListener(e -> {
			addUser();
		});
		
		removeTech.addActionListener(e -> {
			showError("removeTech is unemplemented atm.");
		});
		
		removeUser.addActionListener(e -> {
			removeUser();
		});
		
		reprintBarcode.addActionListener(e -> {
			showError("reprintBarcode is unemplemented atm.");
		});
		
		forceItemSignIn.addActionListener(e -> {
			showError("forceItemSignIn is unemplemented atm.");
		});
		
		topRightPanel.add(addTech);
		topRightPanel.add(addUser);
		topRightPanel.add(removeTech);
		topRightPanel.add(removeUser);
		topRightPanel.add(reprintBarcode);
		topRightPanel.add(forceItemSignIn);
	}

	private void addTech() {
		JFrame addTechFrame = new JFrame("ATSOS Tech Add");
		addTechFrame.setSize(400, 400);
		addTechFrame.setLayout(new GridLayout(3, 2));
		
		JLabel addTechLabel = new JLabel("<html>Add Tech</html>", SwingConstants.CENTER);
		
		JLabel addTechNameLabel = new JLabel("<html>Tech Item Name</html>", SwingConstants.CENTER);
		JTextField addTechNameTextField = new JTextField();
		
		JButton addTechButton = new JButton("<html>Add Tech to System</html>");
		
		addTechButton.addActionListener(e -> {
			try {
				ATSOSItem item = controller.addTech(addTechNameTextField.getText());
				controller.sendMessageToDiscord("A New Piece of Tech was added. Info [" + item.displayItemData() + "]");
				addTechFrame.dispose();
			} catch(IllegalArgumentException exception) {
				showError(exception.getMessage());
			}
		});
		
		addTechFrame.add(addTechLabel);
		addTechFrame.add(addTechButton);
		addTechFrame.add(addTechNameLabel);
		addTechFrame.add(addTechNameTextField);
		
		addTechFrame.setVisible(true);
	}

	private void userSignIn() {
		JFrame userSignInFrame = new JFrame("ATSOS User SignIn");
		userSignInFrame.setSize(300, 300);
		userSignInFrame.setLayout(new GridLayout(2, 2));
		
		JLabel userSignIn = new JLabel("<html>User Signin</html>", SwingConstants.CENTER);
		
		JLabel userSignInPassword = new JLabel("Password for User", SwingConstants.CENTER);
		JTextField userSignInPasswordField = new JTextField();
		
		JButton userSignInButton = new JButton("Sign In User");
		
		userSignInButton.addActionListener(e -> {
			ATSOSUser user = controller.attemptSignInViaPassword(userSignInPasswordField.getText());
			if(user != null) {
				loggedInUser = user;
				userNameLabel.setText("<html>"+user.getUserName()+" is currently signed in.</html>");
				controller.sendMessageToDiscord(user.getUserName() + " has signed into the system.");
				userSignInFrame.dispose();
			} else {
				showError("No User Found with that Password");
			}
		});
		
		userSignInFrame.add(userSignIn);
		userSignInFrame.add(userSignInButton);
		userSignInFrame.add(userSignInPassword);
		userSignInFrame.add(userSignInPasswordField);
		
		userSignInFrame.setVisible(true);
	}
	
	private void userSignOut() {
		controller.sendMessageToDiscord(loggedInUser.getUserName() + " has signed out of the system.");
		loggedInUser = null;
		userNameLabel.setText("<html>No User is Signed In</html>");
	}
	
	private void addUser() {
		JFrame addUserFrame = new JFrame("ATSOS Add User");
		
		addUserFrame.setSize(400, 400);
		
		JPanel addUserPanel = new JPanel();
		addUserPanel.setLayout(new GridLayout(4, 2));
		
		JLabel userCreation = new JLabel("<html>Add User Screen</html>", SwingConstants.CENTER);
		
		JLabel userCreationUserName = new JLabel("Username for User", SwingConstants.CENTER);
		JTextField userCreationUserNameField = new JTextField();
		JLabel userCreationPassword = new JLabel("Password for User", SwingConstants.CENTER);
		JTextField userCreationPasswordField = new JTextField();
		JLabel userCreationAuthLevel = new JLabel("AuthLevel for User", SwingConstants.CENTER);
		JTextField userCreationAuthLevelField = new JTextField();
		
		JButton addUserButton = new JButton("Add User");
		
		addUserButton.addActionListener(e -> {
			try {
				controller.addUser(userCreationUserNameField.getText(), userCreationPasswordField.getText(), userCreationAuthLevelField.getText());
				controller.sendMessageToDiscord("A new user is added to the System. Name - " + userCreationUserNameField.getText() + ".");
				addUserFrame.dispose();
			} catch (IllegalArgumentException exception) {
				showError(exception.getMessage());
			}
		});
		
		addUserPanel.add(userCreation);
		addUserPanel.add(addUserButton);
		addUserPanel.add(userCreationUserName);
		addUserPanel.add(userCreationUserNameField);
		addUserPanel.add(userCreationPassword);
		addUserPanel.add(userCreationPasswordField);
		addUserPanel.add(userCreationAuthLevel);
		addUserPanel.add(userCreationAuthLevelField);
		
		addUserFrame.add(addUserPanel);
		
		addUserFrame.setVisible(true);
		
	}
	
	private void removeUser() {
		JFrame removeUserFrame = new JFrame("ATSOS Remove User");
		
		removeUserFrame.setSize(400, 400);
		
		removeUserFrame.setVisible(true);
	}

	/**
	 * Add all the Location Buttons to the Right Scrolling Panel
	 */
	private void setUpBottomPanels() {
		innerBottomRightPanel.setLayout(new BoxLayout(innerBottomRightPanel, BoxLayout.Y_AXIS));
		innerBottomLeftPanel.setLayout(new BoxLayout(innerBottomLeftPanel, BoxLayout.Y_AXIS));
		
		//@TODO This will need to be changed for click compatability
		List<String> allTechInfo = controller.getAllAvailableTechInfo();
		
		for(int i = 0; i < allTechInfo.size(); i++) {
			JButton techInfo = new JButton("<html>"+allTechInfo.get(i)+"</html>");
			
			techInfo.addActionListener(e -> {
				showError("Item Info is Unavailable ATM.");
			});
			innerBottomLeftPanel.add(techInfo);
		}
		
		//@TODO This will need to be changed for click compatability
		List<String> signedInInfo = controller.getSignedInInfo();
		
		for(int i = 0; i < signedInInfo.size(); i++) {
			JButton techInfo = new JButton("<html>"+signedInInfo.get(i)+"</html>");
			
			techInfo.addActionListener(e -> {
				showError("Item Info is Unavailable ATM.");
			});
			innerBottomRightPanel.add(techInfo);
		}
		
		regenerateWindow();
	}
	
	/**
	 * Used to show any errors needed by the program [Taken from RRT]
	 * @param errorMsg Error Message
	 */
	public void showError(String errorMsg) {
		JOptionPane.showConfirmDialog(null, errorMsg, "Error" , JOptionPane.DEFAULT_OPTION);
	}
	
	/**
	 * PickFile is taken from the MCTT Project but will be explained here
	 * Used to call the FileChooser and Pick the File.
	 * @return Returns the Absolute File Path for a File.
	 */
	public static String pickFile() {
		//Sets the directory of the fileChooser to the current directory
		//Code Taken from MCTT
		JFileChooser fileChooser = new JFileChooser(".");
				
		int response = fileChooser.showOpenDialog(null);
				
		//If a file is given back, it takes that exact path and sends it back, if not system exits.
		if(response == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			return file.getAbsolutePath();
		} else {
			return null;
		}
	}
	
	/**
	 * Used to Regnerate the window if needed by the program.
	 */
	private void regenerateWindow() {
		innerBottomLeftPanel.revalidate();
		innerBottomLeftPanel.repaint();
		
		innerBottomRightPanel.revalidate();
		innerBottomRightPanel.repaint();
		
		bottomLeftPanel.revalidate();
		bottomLeftPanel.repaint();
		
		bottomRightPanel.revalidate();
		bottomRightPanel.repaint();
		
		mainFrame.revalidate();
		mainFrame.repaint();
		mainFrame.setVisible(true);
	}

}
