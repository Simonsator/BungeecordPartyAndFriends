/**
 * This class manages the partys.
 *
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.api.party;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.utilities.disable.Deactivated;
import de.simonsator.partyandfriends.utilities.disable.Disabler;

import java.util.HashMap;

/**
 * This class manages the partys.
 *
 * @author Simonsator
 * @version 1.0.1
 */
public abstract class PartyManager implements Deactivated {
	private static PartyManager instance;

	protected PartyManager() {
		Disabler.getInstance().registerDeactivated(this);
		instance = this;
	}

	public static PartyManager getInstance() {
		return instance;
	}

	/**
	 * Returns the party in which a player is
	 *
	 * @param player The player
	 * @return Returns the party in which a player is or null if he isn't in a
	 * party.
	 */
	public abstract PlayerParty getParty(OnlinePAFPlayer player);

	/**
	 * Creates a party if the player is not already in a party.
	 *
	 * @param player The player
	 * @return Returns true if the party was created or false if not
	 */
	public abstract PlayerParty createParty(OnlinePAFPlayer player);

	protected abstract void deleteAllParties();

	/**
	 * Deletes a party which is given
	 *
	 * @param party The party
	 */
	public abstract void deleteParty(PlayerParty party);

	/**
	 * Puts the player with the party in the {@link HashMap}
	 *
	 * @param player The Player
	 * @param party  The Party
	 */
	public abstract void addPlayerToParty(OnlinePAFPlayer player, PlayerParty party);

	/**
	 * Removes the player party link from the {@link HashMap}
	 *
	 * @param player The Player
	 */
	public abstract void removePlayerFromParty(OnlinePAFPlayer player);

	@Override
	public void onDisable() {
		deleteAllParties();
	}
}
