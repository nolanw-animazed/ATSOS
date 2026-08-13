package starter;

import controller.ATSOSController;

/**
 * Starter Class used to initiate the project
 * @author Nolan Wright
 *
 */
public class StarterFile {

	/**
	 * Used to start the program from a seperate file for clarity
	 * @param args
	 */
	public static void main(String[] args) {
		ATSOSController controller = new ATSOSController();
		controller.initialize();
	}
}
