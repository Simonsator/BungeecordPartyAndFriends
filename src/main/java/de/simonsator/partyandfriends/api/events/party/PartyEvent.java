package de.simonsator.partyandfriends.api.events.party;

import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.plugin.Event;

public abstract class PartyEvent extends Event {
	private final PlayerParty PLAYER_PARTY;

	public PartyEvent(PlayerParty pParty) {
		PLAYER_PARTY = pParty;
	}

	public PlayerParty getParty() {
		return PLAYER_PARTY;
	}
}
