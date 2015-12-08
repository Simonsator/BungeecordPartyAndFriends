/**
 * Will be executed on /friend msg
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.friends.Messages;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Will be executed on /friend msg
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Message {
	/**
	 * Will be executed on /friend msg
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The sender
	 * @param args
	 *            The arguments
	 * @param main
	 *            The main class
	 */
	public static void callMessageSend(ProxiedPlayer player, String[] args, Main main) {
		Messages.send(player, args, 0);
	}
}
