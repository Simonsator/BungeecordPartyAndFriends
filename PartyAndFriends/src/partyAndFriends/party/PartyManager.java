/**
 * This class manages the partys.
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.party;

import java.util.ArrayList;

import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This class manages the partys.
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class PartyManager {
	/**
	 * All partys of players in an List
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private static ArrayList<PlayerParty> partys = new ArrayList<PlayerParty>();

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
		for (PlayerParty party : partys) {
			if (party.isinParty(player)) {
				return party;
			}
		}
		return null;
	}

	/**
	 * Creates a party if the player is not already in a party.
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player
	 * @return Returns true if the party was created or false if not
	 */
	public static PlayerParty createParty(ProxiedPlayer player) {
		PlayerParty party = new PlayerParty(player);
		partys.add(party);
		return party;
	}

	/**
	 * Deletes a party which is given
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param party
	 *            The party
	 */
	public static void deleteParty(PlayerParty party) {
		if (party != null) {
			for (int i = 0; i < party.getPlayer().size(); i++) {
				if (party.getPlayer().get(i) != null) {
					party.getPlayer().remove(i);
				}
			}
			partys.remove(party);
		}
	}
}
