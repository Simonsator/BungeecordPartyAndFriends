/**
 * Objects of this class are the party, where a player is in
 *
 * @author Simonsator
 * @version 1.0.1
 */
package de.simonsator.partyandfriends.party.playerpartys;

import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;

/**
 * Objects of this class are the party, where a player is in
 *
 * @author Simonsator
 * @version 2.0.0
 */
public abstract class PlayerParty {

	/**
	 * Returns true if the given player is the leader of this party, and it will
	 * returns false if he is not the leader, of this party
	 *
	 * @param player The player
	 * @return Returns a true if the given player is the leader of this party,
	 * and it will returns false if he is not the leader, of this party
	 */
	public abstract boolean isLeader(OnlinePAFPlayer player);

	/**
	 * Returns true if the player is in the party. Returns false if the player
	 * is not in the party.
	 *
	 * @param player The player
	 * @return Returns true if the player is in the party. Returns false if the
	 * player is not in the party.
	 */
	public abstract boolean isInParty(OnlinePAFPlayer player);

	public abstract boolean isNobodyInvited();

	/**
	 * Gets the leader of this party
	 *
	 * @return Returns the party leader
	 */
	public abstract OnlinePAFPlayer getLeader();

	/**
	 * Sets the party leader.
	 *
	 * @param player The player
	 */
	public abstract void setLeader(OnlinePAFPlayer player);

	/**
	 * @return Returns all players in this party (inclusive the leader).
	 */
	public abstract ArrayList<OnlinePAFPlayer> getAllPlayers();

	/**
	 * Returns the "normal" players who are in the party.
	 *
	 * @return Returns the "normal" players who are in the party.
	 */
	public abstract ArrayList<OnlinePAFPlayer> getPlayers();

	/**
	 * Adds a player to the party
	 *
	 * @param pPlayer The player
	 * @return Returns true if the player was added to this party. Returns false
	 * if the player was not added to this party
	 */
	public abstract boolean addPlayer(OnlinePAFPlayer pPlayer);

	public abstract void leaveParty(OnlinePAFPlayer pPlayer);

	public abstract void kickPlayer(OnlinePAFPlayer pPlayer);

	/**
	 * Invites a player into this party
	 *
	 * @param pPlayer The player
	 */
	public abstract void invite(final OnlinePAFPlayer pPlayer);

	/**
	 * Returns the size of the invitation list
	 *
	 * @return Returns the size of the invitation list
	 */
	public abstract int getInviteListSize();

	/**
	 * Returns true if the player is already invited. Returns false if the
	 * player is not invited.
	 *
	 * @param player The player
	 * @return Returns true if the player is already invited. Returns false if
	 * the player is not invited.
	 */
	public abstract boolean isInvited(OnlinePAFPlayer player);

	public abstract void sendMessage(TextComponent pText);

}
