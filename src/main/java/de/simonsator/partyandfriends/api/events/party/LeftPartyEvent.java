package de.simonsator.partyandfriends.api.events.party;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;

/**
 * This is event is called when a player left a party (no matter if he disconnects from the server, gets kicked or just uses /party leave)
 */
public class LeftPartyEvent extends PartyEvent {
	private final PAFPlayer PLAYER;

	public LeftPartyEvent(PlayerParty pParty, PAFPlayer pPlayer) {
		super(pParty);
		PLAYER = pPlayer;
	}

	public PAFPlayer getPlayer() {
		return PLAYER;
	}
}
