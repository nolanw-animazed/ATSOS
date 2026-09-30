package view;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ATSOSController;
import data.ATSOSItem;
import data.ATSOSUser;
import uk.org.okapibarcode.backend.Code128;
import uk.org.okapibarcode.backend.HumanReadableLocation;
import uk.org.okapibarcode.graphics.Color;
import uk.org.okapibarcode.output.Java2DRenderer;

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
	/** Houses all the Admin Panel Buttons, allowing them to be enabled and disabled. */
	private ArrayList<JButton> adminButtons;
	/** Current Selected Printer (to print barcodes)*/
	private PrintService selectedPrinter;

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
		
		adminButtons = new ArrayList<JButton>();
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
					if(loggedInUser != null) {
						userSignOut();
					}
					controller.sendMessageToDiscord("System is shutting down.");
					controller.saveData();
					System.exit(0);
				}
			}
		});
		
		buildJMenuBarHeader();
		
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
			if(loggedInUser != null) {
				showError(loggedInUser.getUserName() + " is currently signed in, please sign out first.");
				return;
			}
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
			if(selectedPrinter == null) {
				JOptionPane.showConfirmDialog(null, "Please select a printer from the toolbar.", "Error" , JOptionPane.DEFAULT_OPTION);
				return;
			}
			reprintBarcode();
		});
		
		forceItemSignIn.addActionListener(e -> {
			showError("forceItemSignIn is unemplemented atm.");
		});
		
		adminButtons.add(addTech);
		adminButtons.add(addUser);
		adminButtons.add(removeTech);
		adminButtons.add(removeUser);
		adminButtons.add(reprintBarcode);
		adminButtons.add(forceItemSignIn);
		
		topRightPanel.add(addTech);
		topRightPanel.add(addUser);
		topRightPanel.add(removeTech);
		topRightPanel.add(removeUser);
		topRightPanel.add(reprintBarcode);
		topRightPanel.add(forceItemSignIn);
		
		setAdminButtons(null);
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
		//These need to be redone to take in ATSOSItems and not strings
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
	 * Used to build the upper menu bar for the project
	 * Taken and Edited from AMCTT
	 */
	private void buildJMenuBarHeader() {
		
		JMenuBar header = new JMenuBar();
		
		JMenu themes = new JMenu("Themes");
		
		JMenuItem metal = new JMenuItem("Metal");
		
		JMenuItem nimbus = new JMenuItem("Nimbus");
		
		JMenuItem motif = new JMenuItem("Motif");
		
		JMenuItem windows = new JMenuItem("Windows");
		
		metal.addActionListener(e -> {
			changeUIDesign(UIManager.getCrossPlatformLookAndFeelClassName());
		});
		
		nimbus.addActionListener(e -> {
			changeUIDesign("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		});
		
		motif.addActionListener(e -> {
			changeUIDesign("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		});
		
		windows.addActionListener(e -> {
			changeUIDesign("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		});
		
		themes.add(metal);
		themes.add(nimbus);
		themes.add(motif);
		themes.add(windows);
		
		header.add(themes);
		
		JMenu printers = new JMenu("Printers");
		
		PrintService[] allPrinters = PrintServiceLookup.lookupPrintServices(null, null);
		
		for (PrintService printer : allPrinters) {
			String printerName = printer.getName();
			JMenuItem printerMenuItems = new JMenuItem(printerName);
			
			printerMenuItems.addActionListener(e -> {
				selectedPrinter = printer;
			});
			printers.add(printerMenuItems);
		}
		
		header.add(printers);
		
		mainFrame.setJMenuBar(header);
	}
	
	private void changeUIDesign(String uiDesign) {
		try {
			UIManager.setLookAndFeel(uiDesign);
			SwingUtilities.updateComponentTreeUI(mainFrame);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void setAdminButtons(ATSOSUser user) {
		int authLevel = 0;
		
		if(user != null) {
			authLevel = user.getAuthLevel();
		}
		
		//@TODO maybe granularize this a bit later on
		
		switch(authLevel) {
			case 0 -> {
				for(int i = 0; i < adminButtons.size(); i++) {
					adminButtons.get(i).setEnabled(false);
				}
			}
			case 1 -> {
				for(int i = 0; i < adminButtons.size(); i++) {
					adminButtons.get(i).setEnabled(true);
				}				
			}
			case 2 -> {
				for(int i = 0; i < adminButtons.size(); i++) {
					adminButtons.get(i).setEnabled(true);
				}				
			}
		
		};
		
	}

	private void addTech() {
		//@TODO This needs to ability to select names that are already in the system
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
				setAdminButtons(loggedInUser);
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
		if(loggedInUser == null) {
			return;
		}
		controller.sendMessageToDiscord(loggedInUser.getUserName() + " has signed out of the system.");
		loggedInUser = null;
		setAdminButtons(loggedInUser);
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
		//@TODO Refactor this to remove duplicate code with other similar methods.
		JFrame removeUserFrame = new JFrame("ATSOS Remove User");
		
		removeUserFrame.setSize(400, 400);
		
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		JLabel allTechLabel = new JLabel("All Users in the System", SwingConstants.CENTER);
		
		outerPanel.add(allTechLabel, BorderLayout.PAGE_START);
		
		JPanel innerPanel = new JPanel();
		JScrollPane scrollPanel = new JScrollPane(innerPanel);
		
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		
		outerPanel.add(scrollPanel, BorderLayout.CENTER);
		
		List<ATSOSUser> deletableUsers = controller.getDeletableUsers(loggedInUser);
		
		for(int i = 0; i < deletableUsers.size(); i++) {
			ATSOSUser user = deletableUsers.get(i);
			JButton userInfo = new JButton("<html>"+user.toString()+"</html>");
			
			userInfo.addActionListener(e -> {
				//Not going to do another Window for this
				//deleteUserInfoWindow(user);
				if(loggedInUser.getAuthLevel() <= user.getAuthLevel()) {
					showError("This user is of Equal or Higher AuthLevel, so they cannot be deleted.");
					return;
				}
				int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Confrimation" , JOptionPane.YES_NO_OPTION);
				if(answer == 0) {
					userInfo.setEnabled(false);
					controller.deleteUser(user, loggedInUser);
					controller.sendMessageToDiscord(loggedInUser.getUserName() + " has deleted " + user.toString() + " from the system");
					removeUserFrame.dispose();
				}
			});
			
			innerPanel.add(userInfo);
		}
		
		removeUserFrame.add(outerPanel, BorderLayout.CENTER);
		
		removeUserFrame.setVisible(true);
	}

	private void reprintBarcode() {
		JFrame barcodeFrame = new JFrame("ATSOS Reprint Barcode");
		
		barcodeFrame.setSize(400, 400);
		
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		JLabel allTechLabel = new JLabel("All Tech in the System", SwingConstants.CENTER);
		
		outerPanel.add(allTechLabel, BorderLayout.PAGE_START);
		
		JPanel innerPanel = new JPanel();
		JScrollPane scrollPanel = new JScrollPane(innerPanel);
		
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		
		outerPanel.add(scrollPanel, BorderLayout.CENTER);
		
		List<ATSOSItem> allTechInfo = controller.getAllTechInfo();
		
		for(int i = 0; i < allTechInfo.size(); i++) {
			ATSOSItem item = allTechInfo.get(i);
			JButton techInfo = new JButton("<html>"+item.displayItemData()+"</html>");
			
			techInfo.addActionListener(e -> {
				printBarcodeForItem(Integer.toString(item.getBarcode()));
				controller.sendMessageToDiscord("A Barcode has been printed for " + item.displayItemData());
			});
			
			innerPanel.add(techInfo);
		}
		
		barcodeFrame.add(outerPanel, BorderLayout.CENTER);
		
		//I don't think I need this
//		innerPanel.revalidate();
//		innerPanel.repaint();
//		
//		scrollPanel.revalidate();
//		scrollPanel.repaint();
//		
//		barcodeFrame.revalidate();
//		barcodeFrame.repaint();
		
		barcodeFrame.setVisible(true);
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
	
	/**
	 * Print Barcodes for the Items
	 * Taken from AMCTT with very light editing.
	 * This is practically made solely for Rollo Printer 2x1 Labels.
	 * @TODO maybe edit it so it's not?
	 * @param barcodeNumberForItem Barcode Given for Printing
	 */
	private void printBarcodeForItem(String barcodeNumberForItem) {
		Code128 barcode = new Code128();
		barcode.setFontName("Monospaced");
		barcode.setFontSize(16);
		barcode.setModuleWidth(1);
		barcode.setBarHeight(40);
		barcode.setHumanReadableLocation(HumanReadableLocation.BOTTOM);
		barcode.setContent(barcodeNumberForItem);

		int width = barcode.getWidth();
		int height = barcode.getHeight();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2d = image.createGraphics();
		Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
		renderer.render(barcode);

		try {
			ImageIO.write(image, "png", new File("code128.png"));
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Issue saving the barcode for printing. Restart the app.", "Error" , JOptionPane.DEFAULT_OPTION);
			return;
		}
		
		File file = new File("code128.png");
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (selectedPrinter != null) {
			DocPrintJob job = selectedPrinter.createPrintJob();
			SimpleDoc doc = new SimpleDoc(inputStream, DocFlavor.INPUT_STREAM.PNG, null);
			
			PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
			
			attributes.add(OrientationRequested.PORTRAIT);
			attributes.add(new PrinterResolution(203, 203, PrinterResolution.DPI));
			attributes.add(new MediaPrintableArea(23, 0, 51, 25, MediaPrintableArea.MM));
			try {
				job.print(doc, attributes);
			} catch (PrintException e) {
				JOptionPane.showConfirmDialog(null, "Selected Printer does not work.", "Error" , JOptionPane.DEFAULT_OPTION);
			}
			
			file.delete();
		} else {
			JOptionPane.showConfirmDialog(null, "Please select a printer from the toolbar.", "Error" , JOptionPane.DEFAULT_OPTION);
		}
		
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
