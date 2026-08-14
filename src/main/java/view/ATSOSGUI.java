package view;

import java.awt.GridLayout;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.ATSOSController;

public class ATSOSGUI {
	
	private ATSOSController controller;
	
	private JPanel innerBottomRightPanel;
	
	private JScrollPane bottomRightPanel;
	
	private JPanel innerBottomLeftPanel;
	
	private JScrollPane bottomLeftPanel;
	
	private JPanel topLeftPanel;
	
	private JPanel topRightPanel;
	
	/** Main Frame for the project, stored for regeneration purposes */
	private JFrame mainFrame;	

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
	
	public void initialize() {
		mainFrame = new JFrame("ATSOS");
		
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainFrame.setSize(800, 600);
		
		mainFrame.setLayout(new GridLayout(2,2));
		
		topLeftPanel = new JPanel();
		
		topLeftPanel.setLayout((new GridLayout(3,2)));
		
		JButton userSignIn = new JButton("<html>User Sign In</html>");
		JButton userSignOut = new JButton("<html>User Sign Out</html>");
		JButton userDetailsButton = new JButton("<html>User Details</html>");
		JButton techSignIn = new JButton("<html>Tech Sign In</html>");
		JButton techSignOut = new JButton("<html>Tech Sign Out</html>");
		
		JLabel userNameLabel = new JLabel("<html>No User is Signed In</html>");
		
		userSignIn.addActionListener(e -> {
			showError("userSignIn is unemplemented atm.");
		});
		
		userSignOut.addActionListener(e -> {
			showError("userSignOut is unemplemented atm.");
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
		
		topLeftPanel.add(userSignIn);
		topLeftPanel.add(userSignOut);
		topLeftPanel.add(userNameLabel);
		topLeftPanel.add(userDetailsButton);
		topLeftPanel.add(techSignIn);
		topLeftPanel.add(techSignOut);
		
		topRightPanel = new JPanel();
		
		topRightPanel.setLayout((new GridLayout(2,3)));
		
		//Set up the ScrollPanes for Data
		innerBottomRightPanel = new JPanel();
		bottomRightPanel = new JScrollPane(innerBottomRightPanel);
		
		bottomRightPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		innerBottomLeftPanel = new JPanel();
		bottomLeftPanel = new JScrollPane(innerBottomLeftPanel);
		
		bottomLeftPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		setUpBottomPanels();
		
		mainFrame.add(topLeftPanel);
		mainFrame.add(topRightPanel);
		mainFrame.add(bottomLeftPanel);
		mainFrame.add(bottomRightPanel);
		
		regenerateWindow();
	}
	
	/**
	 * Add all the Location Buttons to the Right Scrolling Panel
	 */
	private void setUpBottomPanels() {
		innerBottomRightPanel.setLayout(new BoxLayout(innerBottomRightPanel, BoxLayout.Y_AXIS));
		innerBottomLeftPanel.setLayout(new BoxLayout(innerBottomLeftPanel, BoxLayout.Y_AXIS));
		
		//@TODO This will need to be changed for click compatability
		List<String> allTechInfo = controller.getAllTechInfo();
		
		for(int i = 0; i < allTechInfo.size(); i++) {
			JButton techInfo = new JButton(allTechInfo.get(i));
			
			techInfo.addActionListener(e -> {
				showError("Item Info is Unavailable ATM.");
			});
			innerBottomLeftPanel.add(techInfo);
		}
		
		//@TODO This will need to be changed for click compatability
		List<String> signedInInfo = controller.getSignedInInfo();
		
		for(int i = 0; i < allTechInfo.size(); i++) {
			JButton techInfo = new JButton(allTechInfo.get(i));
			
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
