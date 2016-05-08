/**
 * This class manages the partys.
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.party;

import java.util.HashMap;

import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This class manages the partys.
 * 
 * @author Simonsator
 * @version 1.0.1
 */
public class PartyManager {
	/**
	 * All partys of players in an List
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 */
	private static HashMap<ProxiedPlayer, PlayerParty> partys = new HashMap<ProxiedPlayer, PlayerParty>();

	/**
	 * Returns the party in which a player is
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player
	 * @return Returns the party in which a player is or null if he isn't in a
	 *         party.
	 */
	public static PlayerParty getParty(ProxiedPlayer player) {
		return partys.get(player);
	}

	/**
	 * Creates a party if the player is not already in a party.
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 * @param player
	 *            The player
	 * @return Returns true if the party was created or false if not
	 */
	public static PlayerParty createParty(ProxiedPlayer player) {
		PlayerParty party = new PlayerParty(player);
		partys.put(player, party);
		return party;
	}

	/**
	 * Deletes a party which is given
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 * @param party
	 *            The party
	 */
	public static void deleteParty(PlayerParty party) {
		if (party != null) {
			for (int i = 0; i < party.getPlayers().size(); i++) {
				if (party.getPlayers().get(i) != null) {
					partys.remove(party.getPlayers().get(i));
				}
			}
			partys.remove(party.getLeader());
		}
	}

	/**
	 * Puts the player with the party in the {@link HashMap}
	 * 
	 * @param player
	 *            The Player
	 * @param party
	 *            The Party
	 */
	public static void addPlayerToParty(ProxiedPlayer player, PlayerParty party) {
		partys.put(player, party);
	}

	/**
	 * Removes the player party link from the {@link HashMap}
	 * 
	 * @param player
	 *            The Player
	 */
	public static void removePlayerFromParty(ProxiedPlayer player) {
		partys.remove(player);
	}
}
