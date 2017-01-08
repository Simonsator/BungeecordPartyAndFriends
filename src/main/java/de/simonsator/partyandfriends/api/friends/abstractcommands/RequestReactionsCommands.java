package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;

public abstract class RequestReactionsCommands extends FriendSubCommand {

	protected RequestReactionsCommands(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	protected boolean hasNoRequest(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if ((!pPlayer.hasRequestFrom(pQueryPlayer))) {
			sendError(pPlayer, "Friends.Command.Accept.ErrorNoFriendShipInvitation");
			return true;
		}
		return false;
	}

}
