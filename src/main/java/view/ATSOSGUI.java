package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.ATSOSController;

public class ATSOSGUI {
	
	private ATSOSController controller;
	
	/** Main Frame for the project, stored for regeneration purposes */
	private JFrame mainFrame;	

	public ATSOSGUI(ATSOSController controller) {
		this.controller = controller;
	}
	
	public void initialize() {
		// TODO Auto-generated method stub
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
		mainFrame.revalidate();
		mainFrame.repaint();
		mainFrame.setVisible(true);
	}

}
