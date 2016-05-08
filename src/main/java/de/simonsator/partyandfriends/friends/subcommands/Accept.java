/**
 * The command accept
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.RequestReactionsCommands;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The command accept
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Accept extends RequestReactionsCommands {
	public Accept(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		int idSender = Main.getInstance().getConnection().getPlayerID(pPlayer.getName());
		int idQuery = Main.getInstance().getConnection().getPlayerID(args[1]);
		if (hasNoRequest(pPlayer, args[1], idSender, idQuery))
			return;
		Main.getInstance().getConnection().addFriend(idSender, idQuery);
		Main.getInstance().getConnection().denyRequest(idSender, idQuery);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
				.getMessagesYml().getString("Friends.Command.Accept.NowFriends").replace("[PLAYER]", args[1])));
		ProxiedPlayer friend = ProxyServer.getInstance().getPlayer(args[1]);
		if (friend == null)
			return;
		friend.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
				.getString("Friends.Command.Accept.NowFriends").replace("[PLAYER]", pPlayer.getDisplayName())));
		friend.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
				.getString("Friends.General.PlayerIsNowOnline").replace("[PLAYER]", pPlayer.getDisplayName())));
		pPlayer.sendMessage(
				new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
						.getString("Friends.General.PlayerIsNowOnline").replace("[PLAYER]", friend.getDisplayName())));
	}

}
