/**
 * 
 */
package discordbot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Nolan Wright
 *
 */
public class SlashCommandListener extends ListenerAdapter {
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		switch (event.getName()) {
			case "signedout" -> {
				String content = "This ain't done yet bitch";
				event.reply(content).queue();
			}
			
			case "alltech" -> {
				String content = "This ain't done yet bitch x2";
				event.reply(content).queue();
			}
		}
	}
}
