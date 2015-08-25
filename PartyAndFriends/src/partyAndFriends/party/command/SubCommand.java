/**
 * An abstract class for the party commands
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.party.command;

import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * An abstract class for the party commands
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public abstract class SubCommand {
	/**
	 * The aliases for the command
	 */
	private String[] aliases;

	/**
	 * Initials the objects
	 * 
	 * @param aliases
	 *            The alias of the command
	 */
	public SubCommand(String[] aliases) {
		this.aliases = aliases;
	}

	/**
	 * An abstract command which will be executed, when the given command was
	 * send
	 * 
	 * @param p
	 *            The player
	 * @param agrs
	 *            The Arguments
	 */
	public abstract void onCommand(ProxiedPlayer p, String[] agrs);

	/**
	 * Returns the aliases of the object
	 * 
	 * @return Returns the aliases of the object
	 */
	public final String[] getAliases() {
		return this.aliases;
	}

}
