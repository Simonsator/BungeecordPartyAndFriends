package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;

import static de.simonsator.partyandfriends.main.Main.getInstance;

public abstract class FriendSubCommand extends SubCommand implements Comparable<SubCommand> {

	protected FriendSubCommand(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, new TextComponent(pHelp), getInstance().getFriendsPrefix());
	}

	protected boolean isPlayerGiven(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length < 2) {
			sendError(pPlayer, "Friends.General.NoPlayerGiven");
			return false;
		}
		return true;
	}

	protected boolean isAFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer) {
		if (!pPlayer.isAFriendOf(pGivenPlayer)) {
			sendError(pPlayer, "Friends.General.PlayerIsOffline");
			return false;
		}
		return true;
	}


	@Override
	protected void sendError(OnlinePAFPlayer pPlayer, TextComponent pMessage) {
		pPlayer.sendMessage(pMessage);
		printOutHelp(pPlayer, Friends.getInstance().getName());
	}
}
