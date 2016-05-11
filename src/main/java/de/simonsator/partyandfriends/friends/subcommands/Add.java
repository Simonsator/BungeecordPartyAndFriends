/**
 * The command add
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

/**
 * The command add
 * 
 * @author Simonsator
 * @version 1.0.1
 */
public class Add extends FriendSubCommand {
	public Add(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		if (givenPlayerEqualsSender(pPlayer, args[1]))
			return;
		int idQuery = Main.getInstance().getConnection().getPlayerID(args[1]);
		if (!doesPlayerExist(pPlayer, idQuery, args[1]))
			return;
		int idSender = Main.getInstance().getConnection().getPlayerID(pPlayer.getName());
		if (isAFriendOf(pPlayer, args[1], idSender, idQuery))
			return;
		if (!hasNoRequestFrom(pPlayer, args[1], idSender, idQuery))
			return;
		if ((Main.getInstance().getConnection().hasRequestFrom(idSender, idQuery))) {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
							.getString("Friends.Command.Add.FriendRequestFromreceiver").replace("[PLAYER]", args[1])));
			pPlayer.unsafe()
					.sendPacket(new Chat("{\"text\":\"" + Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.Command.Add.HowToAccept").replace(
									"[PLAYER]", args[1])
							+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
							+ Main.getInstance().getFriendsCommand().getName() + " accept " + args[1]
							+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
							+ Main.getInstance().getMessagesYml().getString("Friends.Command.Add.ClickHere")
							+ "\"}]}}}"));
			return;
		}
		if (!allowsFriendRequests(pPlayer, args[1], idQuery))
			return;
		Main.getInstance().getConnection().sendFriendRequest(idSender, idQuery);
		sendRequest(pPlayer, args[1]);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
				.getMessagesYml().getString("Friends.Command.Add.SendedAFriendRequest").replace("[PLAYER]", args[1])));
	}

	private void sendRequest(ProxiedPlayer pPlayer, String pQueryName) {
		ProxiedPlayer receiver = ProxyServer.getInstance().getPlayer(pQueryName);
		if (receiver != null) {
			receiver.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.Add.FriendRequestreceived")
							.replace("[PLAYER]", pPlayer.getDisplayName())));
			receiver.unsafe()
					.sendPacket(new Chat("{\"text\":\"" + Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.Command.Add.HowToAccept")
									.replace("[PLAYER]", pPlayer.getName())
					+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/"
					+ Main.getInstance().getFriendsCommand().getName() + " accept " + pPlayer.getName()
					+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.Add.ClickHere") + "\"}]}}}"));
		}
	}

	private boolean hasNoRequestFrom(ProxiedPlayer pPlayer, String pQueryPlayer, int pIDSender, int pIDQuery) {
		if ((Main.getInstance().getConnection().getRequests(pIDQuery).contains(pIDSender))) {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
							.getString("Friends.Command.Accept.ErrorAlreadySend").replace("[PLAYER]", pQueryPlayer)));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return false;
		}
		return true;
	}

	@Override
	protected boolean isAFriendOf(ProxiedPlayer pPlayer, String pGivenPlayer, int pIDSender, int pIDQuery) {
		if (Main.getInstance().getConnection().isAFriendOf(pIDSender, pIDQuery)) {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
							.getString("Friends.Command.Add.AlreadyFriends").replace("[PLAYER]", pGivenPlayer)));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return true;
		}
		return false;
	}

	private boolean givenPlayerEqualsSender(ProxiedPlayer pPlayer, String pGivenPlayer) {
		if (pPlayer.getName().equalsIgnoreCase(pGivenPlayer)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
					.getMessagesYml().getString("Friends.Command.Accept.ErrorSenderEqualsReceiver")));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return true;
		}
		return false;
	}

	private boolean doesPlayerExist(ProxiedPlayer pPlayer, int pQueryID, String pQueryPlayer) {
		if (pQueryID == -1) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
					.getMessagesYml().getString("Friends.General.DoesNotExist").replace("[PLAYER]", pQueryPlayer)));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return false;
		}
		return true;
	}

	private boolean allowsFriendRequests(ProxiedPlayer pPlayer, String pQueryName, int pQueryID) {
		if (Main.getInstance().getConnection().getSettingsWorth(pQueryID, 0) == 0) {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
							.getString("Friends.Command.Add.CanNotSendThisPlayer").replace("[PLAYER]", pQueryName)));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return false;
		}
		return true;
	}
}
