package de.simonsator.partyandfriends.party.partymanager;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.party.PartyCreatedEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.party.playerpartys.LocalPlayerParty;

import java.util.HashMap;
import java.util.UUID;


public class LocalPartyManager extends PartyManager {
	/**
	 * All parties of players in a HashMap
	 */
	private static HashMap<UUID, PlayerParty> parties = new HashMap<>();

	@Deprecated
	public LocalPartyManager() {
		super(60);
	}

	public LocalPartyManager(long pInvitationTimeoutTime) {
		super(pInvitationTimeoutTime);
	}

	@Override
	public PlayerParty getParty(UUID pUUID) {
		return parties.get(pUUID);
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
		parties.put(player.getUniqueId(), party);
		BukkitBungeeAdapter.getInstance().callEvent(new PartyCreatedEvent(party));
		return party;
	}

	@Override
	public void deleteAllParties() {
		parties = new HashMap<>();
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
					parties.remove(party.getPlayers().get(i).getUniqueId());
				}
			}
			if (party.getLeader() != null)
				parties.remove(party.getLeader().getUniqueId());
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
		parties.put(player.getUniqueId(), party);
	}

	/**
	 * Removes the player party link from the {@link HashMap}
	 *
	 * @param player The Player
	 */
	@Override
	public void removePlayerFromParty(PAFPlayer player) {
		parties.remove(player.getUniqueId());
	}
}
