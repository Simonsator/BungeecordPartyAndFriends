/**
 * Will be executed on /friend msg
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.freunde.kommandos;

import java.sql.SQLException;
//import java.util.StringTokenizer;

/*import net.md_5.bungee.BungeeCord;
 import net.md_5.bungee.api.ChatColor;
 import net.md_5.bungee.api.chat.TextComponent;*/
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
	 * @throws SQLException
	 *             Can throw a {@link SQLException}
	 */
	public static void callMessageSend(ProxiedPlayer player, String[] args, Main main) throws SQLException {
		nachricht.send(player, args, 0);
	}
}
