package discordbot;

import java.util.Collections;
import java.util.List;

import controller.ATSOSController;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ATSOSDiscordCommunication {

	private JDA jda; 
	
	public ATSOSDiscordCommunication(String discordAPIToken, ATSOSController controller) {
		jda = JDABuilder.createLight(discordAPIToken, Collections.emptyList())
	      .addEventListeners(new SlashCommandListener(controller))
	      .build();
//		  
//		  jda.retrieveCommands().queue(commands -> {
//			  for (Command cmd : commands) {
//				  System.out.println("Global Command: " + cmd.getName());
//			  }
//		  });	  
//		  CommandListUpdateAction commands = jda.updateCommands();
//		  
//		  commands.addCommands(
//			Commands.slash("signedout", "See What Tech is Currently Signed Out"),
//			Commands.slash("alltech", "See a List of All Tech and Locations")	
//          );
//		  
//		  commands.queue();
	}

	public void sendMessage(String message) {
		List<TextChannel> channels = jda.getTextChannelsByName("bot-notices", true);
		
		for(int i = 0; i < channels.size(); i++) {
			channels.get(i).sendMessage(message).complete();
		}
	}
	
}


