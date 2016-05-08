package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class FriendSubCommand extends SubCommand {

	public FriendSubCommand(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	protected boolean isPlayerGiven(ProxiedPlayer pPlayer, String[] args) {
		if (args.length < 2) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return false;
		}
		return true;
	}

	protected boolean isAFriendOf(ProxiedPlayer pPlayer, String pGivenPlayer, int pIDSender, int pIDQuery) {
		if (!Main.getInstance().getConnection().isAFriendOf(pIDSender, pIDQuery)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
					.getMessagesYml().getString("Friends.General.PlayerIsOffline").replace("[PLAYER]", pGivenPlayer)));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return false;
		}
		return true;
	}

}
