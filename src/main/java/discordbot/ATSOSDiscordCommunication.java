package discordbot;

import java.util.Collections;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.Command;

public class ATSOSDiscordCommunication {

	public ATSOSDiscordCommunication(String discordAPIToken) {
		System.out.println(discordAPIToken);
		  JDA jda = JDABuilder.createLight(discordAPIToken, Collections.emptyList())
	      .addEventListeners(new SlashCommandListener())
	      .build();
		  
		  jda.retrieveCommands().queue(commands -> {
			  for (Command cmd : commands) {
				  System.out.println("Global Command: " + cmd.getName());
			  }
		  });
		  
//		  CommandListUpdateAction commands = jda.updateCommands();
//		  
//		  commands.addCommands(
//			Commands.slash("signedout", "See What Tech is Currently Signed Out"),
//			Commands.slash("alltech", "See a List of All Tech and Locations")	
//          );
//		  
//		  commands.queue();
	}
}
