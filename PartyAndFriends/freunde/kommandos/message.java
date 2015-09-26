/**
 * Will be executed on /friend msg
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.freunde.kommandos;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.freunde.nachricht;
import partyAndFriends.main.Main;

/**
 * Will be executed on /friend msg
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class message {
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
		nachricht.send(player, args, 0);
	}
}
