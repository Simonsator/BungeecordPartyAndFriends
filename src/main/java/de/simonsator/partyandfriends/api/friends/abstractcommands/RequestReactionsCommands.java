package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class RequestReactionsCommands extends FriendSubCommand {

	public RequestReactionsCommands(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	protected boolean hasNoRequest(ProxiedPlayer pPlayer, String pQueryPlayer, int pSenderID, int pQueryID) {
		if ((!Main.getInstance().getConnection().hasRequestFrom(pSenderID, pQueryID))) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
					.getMessagesYml().getString("Friends.Command.Accept.ErrorNoFriendShipInvitation")
					.replace("[PLAYER]", pQueryPlayer)));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return true;
		}
		return false;
	}

}
