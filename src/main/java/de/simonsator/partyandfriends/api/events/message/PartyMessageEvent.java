package de.simonsator.partyandfriends.api.events.message;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;

/**
 * @author simonbrungs
 * @version 1.0.0 17.11.16
 */
public class PartyMessageEvent extends SimpleMessageEvent {
	private final PlayerParty PARTY;

	public PartyMessageEvent(OnlinePAFPlayer sender, String message, PlayerParty pParty) {
		super(sender, message);
		PARTY = pParty;
	}

	public PlayerParty getParty() {
		return PARTY;
	}

}
