/**
 * 
 */
package discordbot;

import controller.ATSOSController;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Nolan Wright
 *
 */
public class SlashCommandListener extends ListenerAdapter {
	
	private ATSOSController controller;
	
	public SlashCommandListener(ATSOSController controller) {
		this.controller = controller;
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		switch (event.getName()) {
			case "signedout" -> {
				String content = controller.signedOutTech();
				event.reply(content).queue();
			}
			
			case "alltech" -> {
				String content = controller.allTech();
				event.reply(content).queue();
			}
		}
	}
}
