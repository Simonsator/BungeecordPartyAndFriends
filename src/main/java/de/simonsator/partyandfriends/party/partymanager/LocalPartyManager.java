package de.simonsator.partyandfriends.party.partymanager;

import java.util.HashMap;
import java.util.UUID;

import de.simonsator.partyandfriends.api.events.party.CreatePartyEvent;
import de.simonsator.partyandfriends.api.events.party.JoinPartyEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.party.playerpartys.LocalPlayerParty;
import net.md_5.bungee.api.ProxyServer;


public class LocalPartyManager extends PartyManager {
	/**
	 * All parties of players in a HashMap
	 */
	private static HashMap<UUID, PlayerParty> parties = new HashMap<>();

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
	public void addPlayerToParty(OnlinePAFPlayer player, PlayerParty party, boolean createParty) {
		if (party.getAllPlayers().size() > 2) {
			ProxyServer.getInstance().getPluginManager().callEvent(new JoinPartyEvent(party, player));
		}else if (party.getAllPlayers().size() == 2) {
			if (createParty) {
				ProxyServer.getInstance().getPluginManager().callEvent(new CreatePartyEvent(party));
			}
		}
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
