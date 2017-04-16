package de.simonsator.partyandfriends.api.party;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;

/**
 * The APIs for the party system of Party and Friends
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class PartyAPI {
	public static final int LEADER_PERMISSION_HEIGHT = 2;
	public static final int PARTY_MEMBER_PERMISSION_HEIGHT = 1;
	public static final int NO_PARTY_PERMISSION_HEIGHT = 0;

	/**
	 * Returns the party, if the given player is in a party or null if he is not
	 * in a party
	 *
	 * @param player The player
	 * @return Returns the party, if the given player is in a party or null if
	 * he is not in a party
	 */
	public static PlayerParty getParty(OnlinePAFPlayer player) {
		return PartyManager.getInstance().getParty(player);
	}

}
