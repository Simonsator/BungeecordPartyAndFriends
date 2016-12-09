package de.simonsator.partyandfriends.api.events.command;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;

/**
 * @author simonbrungs
 * @version 1.0.0 09.12.16
 */
public class FriendshipCommandEvent extends PAFSubCommandEvent<FriendSubCommand> {
	private final PAFPlayer INTERACT_PLAYER;

	public FriendshipCommandEvent(OnlinePAFPlayer pExecutor, PAFPlayer pInteractPlayer, String[] args, FriendSubCommand pCaller) {
		super(pExecutor, args, pCaller);
		INTERACT_PLAYER = pInteractPlayer;
	}

	/**
	 * @return Returns either the player to whom it is tried to send the friend request if this event was called by an
	 * instance of the class {@link de.simonsator.partyandfriends.friends.subcommands.Add} or the player who did send the
	 * friend request if the event was called by an instance of the class
	 * {@link de.simonsator.partyandfriends.friends.subcommands.Accept}.
	 */
	public PAFPlayer getInteractPlayer() {
		return INTERACT_PLAYER;
	}
}
