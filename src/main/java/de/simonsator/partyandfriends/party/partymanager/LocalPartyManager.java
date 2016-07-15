package de.simonsator.partyandfriends.party.partymanager;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.party.playerpartys.LocalPlayerParty;
import de.simonsator.partyandfriends.api.party.PlayerParty;

import java.util.HashMap;
import java.util.UUID;


public class LocalPartyManager extends PartyManager {
	/**
	 * All partys of players in a HashMap
	 */
	private static HashMap<UUID, PlayerParty> partys = new HashMap<>();

	@Override
	public PlayerParty getParty(OnlinePAFPlayer player) {
		return partys.get(player.getUniqueId());
	}

	/**
	 * Creates a party if the player is not already in a party.
	 *
	 * @param player The player
	 * @return Returns true if the party was created or false if not
	 */
	@Override
	public PlayerParty createParty(OnlinePAFPlayer player) {
		PlayerParty party = new LocalPlayerParty(player);
		partys.put(player.getUniqueId(), party);
		return party;
	}

	@Override
	public void deleteAllParties() {
		partys = new HashMap<>();
	}

	/**
	 * Deletes a party which is given
	 *
	 * @param party The party
	 */
	@Override
	public void deleteParty(PlayerParty party) {
		if (party != null) {
			for (int i = 0; i < party.getPlayers().size(); i++) {
				if (party.getPlayers().get(i) != null) {
					partys.remove(party.getPlayers().get(i).getUniqueId());
				}
			}
			if (party.getLeader() != null)
				partys.remove(party.getLeader().getUniqueId());
		}
	}

	/**
	 * Puts the player with the party in the {@link HashMap}
	 *
	 * @param player The Player
	 * @param party  The Party
	 */
	@Override
	public void addPlayerToParty(OnlinePAFPlayer player, PlayerParty party) {
		partys.put(player.getUniqueId(), party);
	}

	/**
	 * Removes the player party link from the {@link HashMap}
	 *
	 * @param player The Player
	 */
	@Override
	public void removePlayerFromParty(OnlinePAFPlayer player) {
		partys.remove(player.getUniqueId());
	}
}
