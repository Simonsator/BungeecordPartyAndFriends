/***
 * The command jump
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.ServerConnector;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.StandartConnector;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/***
 * The command jump
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Jump extends FriendSubCommand {
	private static ServerConnector connector = new StandartConnector();

	public Jump(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		ProxiedPlayer friend = ProxyServer.getInstance().getPlayer(args[1]);
		if (!isPlayerOnline(pPlayer, friend, args[1]))
			return;
		int friendID = Main.getInstance().getConnection().getPlayerID(friend.getName());
		int playerID = Main.getInstance().getConnection().getPlayerID(pPlayer);
		if (!isAFriendOf(pPlayer, args[1], friendID, playerID))
			return;
		ServerInfo toJoin = friend.getServer().getInfo();
		if (isAlreadyOnServer(pPlayer, toJoin))
			return;
		if (!allowsJumps(pPlayer, friendID))
			return;
		connector.connect(pPlayer, toJoin);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
				+ Main.getInstance().getMessagesYml().getString("Friends.Command.Jump.JoinedTheServer")
						.replace("[PLAYER]", friend.getDisplayName())));
	}

	private boolean allowsJumps(ProxiedPlayer pPlayer, int pQueryID) {
		if (Main.getInstance().getConnection().getSettingsWorth(pQueryID, 4) == 1) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.Jump.CanNotJump")));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return false;
		}
		return true;
	}

	private boolean isAlreadyOnServer(ProxiedPlayer pPlayer, ServerInfo pToJoin) {
		if (pToJoin.equals(pPlayer.getServer().getInfo())) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.Jump.AlreadyOnTheServer")));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return true;
		}
		return false;
	}

	private boolean isPlayerOnline(ProxiedPlayer pSender, ProxiedPlayer pQueryPlayer, String pQueryPlayerName) {
		if (pQueryPlayer == null) {
			pSender.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
							.getString("Friends.General.PlayerIsOffline").replace("[PLAYER]", pQueryPlayerName)));
			pSender.sendMessage(new TextComponent(getHelp()));
			return false;
		}
		return true;
	}

	/**
	 * Sets the server connector, which will be used to join a server.
	 * 
	 * @param pConnector
	 *            The connector
	 */
	public static void setServerConnector(ServerConnector pConnector) {
		connector = pConnector;
	}
}
