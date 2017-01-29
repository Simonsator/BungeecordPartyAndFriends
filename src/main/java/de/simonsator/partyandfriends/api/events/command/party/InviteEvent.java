package de.simonsator.partyandfriends.api.events.command.party;

import de.simonsator.partyandfriends.api.events.command.PAFSubCommandEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.party.subcommand.Invite;

/**
 * @author simonbrungs
 * @version 1.0.0 09.01.17
 */
public class InviteEvent extends PAFSubCommandEvent<Invite> {
	private final OnlinePAFPlayer INTERACT_PLAYER;

	public InviteEvent(OnlinePAFPlayer pExecutor, OnlinePAFPlayer pInteractPlayer, String[] args, Invite pCaller) {
		super(pExecutor, args, pCaller);
		INTERACT_PLAYER = pInteractPlayer;
	}

	public PAFPlayer getInteractPlayer() {
		return INTERACT_PLAYER;
	}

}
