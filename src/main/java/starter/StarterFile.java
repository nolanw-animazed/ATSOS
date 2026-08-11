package starter;

import controller.ATSOSController;

/**
 * 
 * @author Nolan Wright
 *
 */
public class StarterFile {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ATSOSController controller = new ATSOSController();
		controller.initialize();
	}
}
