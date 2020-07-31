package de.simonsator.partyandfriends.api.events.party;

import de.simonsator.partyandfriends.api.party.PlayerParty;

public class PartyCreatedEvent extends PartyEvent {
	public PartyCreatedEvent(PlayerParty pParty) {
		super(pParty);
	}
}
