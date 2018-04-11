package de.simonsator.partyandfriends.api.events.party;

import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.plugin.Event;

/**
 * This is event is called when a player join a party
 */
public class CreatePartyEvent extends Event {
	private final PlayerParty PARTY;

	public CreatePartyEvent(PlayerParty pParty) {
		PARTY = pParty;
	}
	public PlayerParty getParty() {
		return PARTY;
	}
}
