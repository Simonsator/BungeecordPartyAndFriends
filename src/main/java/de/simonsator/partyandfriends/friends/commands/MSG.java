package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Will be executed on /msg
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class MSG extends Command {
	/**
	 * Initials the command
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param friendsAliasMsg
	 *            The aliases for the command /msg
	 */
	public MSG(String[] friendsAliasMsg) {
		super("msg", Main.getInstance().getConfig().getString("Permissions.FriendPermission"), friendsAliasMsg);
	}

	/**
	 * Executes the command /msg
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param commandSender
	 *            The sender of the command
	 * @param args
	 *            The arguments
	 */
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof ProxiedPlayer)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
				Main.getInstance().loadConfiguration();
				commandSender.sendMessage(
						new TextComponent(Main.getInstance().getFriendsPrefix() + "Config and MessagesYML reloaded!"));
			} else {
				Main.getInstance().loadConfiguration();
				commandSender.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + "Config reloaded"));
			}
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) commandSender;
		send(player, args, 1);
	}

	/**
	 * Send a message from a sender to a receiver
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pPlayer
	 *            Sender
	 * @param args
	 *            Arguments
	 * @param type
	 *            The type of the used command either 0 if the player used the
	 *            command /friend msg or 1 if the player used the command /msg
	 */
	public void send(ProxiedPlayer pPlayer, String[] args, int type) {
		int begin = 1;
		if (type == 1) {
			begin = 0;
		}
		if (!messageGiven(pPlayer, args, begin + 1))
			return;
		int writtenToID = Main.getInstance().getConnection().getPlayerID(args[begin]);
		if (!playerExists(pPlayer, writtenToID))
			return;
		int playerID = Main.getInstance().getConnection().getPlayerID(pPlayer.getName());
		int n = 2;
		if (type == 1) {
			n = 1;
		}
		send(pPlayer, args, playerID, writtenToID, n, args[n - 1]);
	}

	public void send(ProxiedPlayer pPlayer, String[] args, int pPlayerID, int pWrittenToID, int n, String pQueryName) {
		if (!isFriendOf(pPlayer, pPlayerID, pWrittenToID))
			return;
		if (!allowsWriteTo(pPlayer, pWrittenToID))
			return;
		ProxiedPlayer writtenTo = ProxyServer.getInstance().getPlayer(pQueryName);
		if (isOffline(pPlayer, writtenTo, pPlayerID, pWrittenToID, args, n))
			return;
		sendMessage(toMessage(args, n), writtenTo, pPlayer);
		Main.getInstance().getConnection().setLastPlayerWroteTo(pPlayerID, pWrittenToID, 0);
	}

	public boolean messageGiven(ProxiedPlayer pPlayer, String[] args, int n) {
		if (args.length <= n) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.MSG")));
			return false;
		}
		return true;
	}

	/**
	 * Delivers a message that was send, while a player was offline
	 * 
	 * @param pContent
	 *            Content of the message
	 * @param pWrittenTo
	 *            The player which was written to
	 * @param pSenderName
	 *            The name of the sender
	 */
	public void deliverOfflineMessage(String pContent, ProxiedPlayer pWrittenTo, String pSenderName) {
		sendMessage(
				pContent.replace(" ",
						Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.ColorOfMessage")),
				pWrittenTo, pSenderName, pWrittenTo.getDisplayName());
	}

	private static boolean playerExists(ProxiedPlayer pPlayer, int pPlayerID) {
		if (pPlayerID == -1) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	private boolean isOffline(ProxiedPlayer pPlayer, ProxiedPlayer pQueryPlayer, int pPlayerID, int pQueryID,
			String[] args, int n) {
		if (pQueryPlayer == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return true;
		}
		return false;
	}

	private boolean allowsWriteTo(ProxiedPlayer pPlayer, int pQueryID) {
		if (Main.getInstance().getConnection().getSettingsWorth(pQueryID, 2) == 1) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	private boolean isFriendOf(ProxiedPlayer pPlayer, int pPlayerID, int pQueryID) {
		if (!Main.getInstance().getConnection().isAFriendOf(pPlayerID, pQueryID)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	/**
	 * Sends a message to a given player from an other player
	 * 
	 * @param pContent
	 *            The content to send
	 * @param pPlayer1
	 *            The sender
	 * @param pPlayer2
	 *            The receiver
	 */
	private void sendMessage(String pContent, ProxiedPlayer pPlayer1, ProxiedPlayer pPlayer2) {
		sendMessage(pContent, pPlayer1, pPlayer2.getDisplayName(), pPlayer1.getDisplayName());
		sendMessage(pContent, pPlayer2, pPlayer2.getDisplayName(), pPlayer1.getDisplayName());
	}

	private void sendMessage(String pContent, ProxiedPlayer pReceiver, String pSenderName, String pReceiverName) {
		pReceiver.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
				.getMessagesYml().getString("Friends.Command.MSG.SendedMessage").replace("[SENDER]", pSenderName)
				.replace("[PLAYER]", pReceiverName).replace("[CONTENT]", pContent)));
	}

	/**
	 * Returns a styled message
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param args
	 *            The Arguments The Main.main class
	 * @param n
	 *            At which argument the while loop should start
	 * @return Returns a styled message
	 */
	private String toMessage(String[] args, int n) {
		String content = "";
		while (n < args.length) {
			content = content + Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.ColorOfMessage")
					+ args[n];
			n++;
		}
		return content;
	}

}
