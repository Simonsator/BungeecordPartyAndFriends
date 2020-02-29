package de.simonsator.partyandfriends.api.events.party;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;

/**
 * This is event is called when a player left a party (no matter if he disconnects from the server, gets kicked or just uses /party leave).
 * At this point no new leader was chosen yet. If the leader left the party the leader of the party will now be null.
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
