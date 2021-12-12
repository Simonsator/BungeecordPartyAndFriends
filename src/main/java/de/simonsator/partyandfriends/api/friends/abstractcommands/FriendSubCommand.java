package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

public abstract class FriendSubCommand extends SubCommand implements Comparable<SubCommand> {

	protected FriendSubCommand(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp, Friends.getInstance().getPrefix());
	}

	protected FriendSubCommand(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, Friends.getInstance().getPrefix(), pPermission);
	}

	protected boolean isPlayerGiven(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length < 2) {
			sendError(pPlayer, "Friends.General.NoPlayerGiven");
			return false;
		}
		return true;
	}

	protected boolean isAFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer, String[] args) {
		if (!pPlayer.isAFriendOf(pGivenPlayer)) {
			sendError(pPlayer, new TextComponent(TextComponent.fromLegacyText(PREFIX + Main.getInstance().getMessages().getString("Friends.General.PlayerIsOffline").replace("[PLAYER]", args[1]))));
			return false;
		}
		return true;
	}

	@Deprecated
	protected boolean isAFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer) {
		if (!pPlayer.isAFriendOf(pGivenPlayer)) {
			sendError(pPlayer, new TextComponent(TextComponent.fromLegacyText(PREFIX + Main.getInstance().getMessages().getString("Friends.General.PlayerIsOffline").replace("[PLAYER]", pGivenPlayer.getDisplayName()))));
			return false;
		}
		return true;
	}

	@Override
	public void sendError(OnlinePAFPlayer pPlayer, TextComponent pMessage) {
		pPlayer.sendMessage(pMessage);
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.General.PrintOutHelpOnError"))
			printOutHelp(pPlayer, Friends.getInstance().getName());
	}
}
