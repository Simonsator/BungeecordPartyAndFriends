package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.main.Main.getPlayerManager;
import static de.simonsator.partyandfriends.utilities.CompilePatter.*;

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
	 * @param friendsAliasMsg The aliases for the command /msg
	 */
	public MSG(String[] friendsAliasMsg) {
		super("msg", getInstance().getConfig().getString("Permissions.FriendPermission"), friendsAliasMsg);
	}

	private static boolean playerExists(OnlinePAFPlayer pPlayer, PAFPlayer pPlayerQuery) {
		if (!pPlayerQuery.doesExist()) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	/**
	 * Executes the command /msg
	 *
	 * @param pCommandSender The sender of the command
	 * @param args           The arguments
	 */
	@Override
	public void execute(CommandSender pCommandSender, String[] args) {
		if (TopCommand.isPlayer(pCommandSender))
			send(getPlayerManager().getPlayer((ProxiedPlayer) pCommandSender), args, 1);
	}

	/**
	 * Send a message from a sender to a receiver
	 *
	 * @param pPlayer Sender
	 * @param args    Arguments
	 * @param type    The type of the used command either 0 if the player used the
	 *                command /friend msg or 1 if the player used the command /msg
	 */
	public void send(OnlinePAFPlayer pPlayer, String[] args, int type) {
		int begin = 1;
		if (type == 1) {
			begin = 0;
		}
		if (!messageGiven(pPlayer, args, begin + 1))
			return;
		PAFPlayer writtenTo = getPlayerManager().getPlayer(args[begin]);
		if (!playerExists(pPlayer, writtenTo))
			return;
		int n = 2;
		if (type == 1) n = 1;
		send(pPlayer, args, writtenTo, n);
	}

	void send(OnlinePAFPlayer pPlayer, String[] args, PAFPlayer pWrittenTo, int n) {
		if (!isFriendOf(pPlayer, pWrittenTo))
			return;
		if (!allowsWriteTo(pPlayer, pWrittenTo))
			return;
		if (isOffline(pPlayer, pWrittenTo, args, n))
			return;
		sendMessage(toMessage(args, n), (OnlinePAFPlayer) pWrittenTo, pPlayer);
		pPlayer.setLastPlayerWroteFrom(pWrittenTo);
	}

	boolean messageGiven(OnlinePAFPlayer pPlayer, String[] args, int n) {
		if (args.length <= n) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
			pPlayer.sendMessage(
					new TextComponent(getInstance().getMessagesYml().getString("Friends.CommandUsage.MSG")));
			return false;
		}
		return true;
	}

	/**
	 * Delivers a message that was send, while a player was offline
	 *
	 * @param pContent   Content of the message
	 * @param pWrittenTo The player which was written to
	 * @param pSender    The name of the sender
	 */
	public void deliverOfflineMessage(String pContent, OnlinePAFPlayer pWrittenTo, PAFPlayer pSender) {
		sendMessage(
				SPACEPATTERN.matcher(pContent).replaceAll(Matcher.quoteReplacement(getInstance().getMessagesYml().getString("Friends.Command.MSG.ColorOfMessage"))),
				pWrittenTo, pSender.getDisplayName(), pWrittenTo.getDisplayName());
	}

	private boolean isOffline(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer, String[] args, int n) {
		if (!pQueryPlayer.isOnline()) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return true;
		}
		return false;
	}

	private boolean allowsWriteTo(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if (pQueryPlayer.getSettingsWorth(2) == 1) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	private boolean isFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if (!pPlayer.isAFriendOf(pQueryPlayer)) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	private void sendMessage(String pContent, OnlinePAFPlayer pPlayer1, OnlinePAFPlayer pPlayer2) {
		sendMessage(pContent, pPlayer1, pPlayer2.getDisplayName(), pPlayer1.getDisplayName());
		sendMessage(pContent, pPlayer2, pPlayer2.getDisplayName(), pPlayer1.getDisplayName());
	}

	private void sendMessage(String pContent, OnlinePAFPlayer pReceiver, String pSenderName, String pReceiverName) {
		pReceiver.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + CONTENTPATTERN.matcher(PLAYERPATTERN.matcher(SENDERNAMEPATTERN.matcher(getInstance()
				.getMessagesYml().getString("Friends.Command.MSG.SentMessage")).replaceAll(Matcher.quoteReplacement(pSenderName))).replaceAll(Matcher.quoteReplacement(pReceiverName))).replaceAll(Matcher.quoteReplacement(pContent))));
	}

	/**
	 * Returns a styled message
	 *
	 * @param args The Arguments The Main.main class
	 * @param n    At which argument the while loop should start
	 * @return Returns a styled message
	 */
	private String toMessage(String[] args, int n) {
		String content = "";
		while (n < args.length) {
			content = content + getInstance().getMessagesYml().getString("Friends.Command.MSG.ColorOfMessage")
					+ args[n];
			n++;
		}
		return content;
	}

	/**
	 * Returns a styled message
	 *
	 * @param args The Arguments The Main.main class
	 * @param n    At which argument the while loob should start
	 * @return Returns a styled message
	 */
	private String toMessageNoColor(String[] args, int n) {
		String content = "";
		while (n < args.length) {
			content = content + args[n];
			n++;
		}
		return content;
	}

}
