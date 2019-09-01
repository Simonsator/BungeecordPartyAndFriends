package de.simonsator.partyandfriends.api.events.party;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;

public class PartyLeaderChangedEvent extends PartyEvent {
	private final OnlinePAFPlayer NEW_LEADER;

	public PartyLeaderChangedEvent(PlayerParty pParty, OnlinePAFPlayer pNewLeader) {
		super(pParty);
		NEW_LEADER = pNewLeader;
	}

	public OnlinePAFPlayer getNewLeader() {
		return NEW_LEADER;
	}
}
