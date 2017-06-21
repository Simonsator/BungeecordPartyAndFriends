package de.simonsator.partyandfriends.friends.commands;

import com.google.gson.JsonObject;
import de.simonsator.partyandfriends.api.OnlyTopCommand;
import de.simonsator.partyandfriends.api.events.message.FriendMessageEvent;
import de.simonsator.partyandfriends.api.events.message.FriendOnlineMessageEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.*;

/**
 * Will be executed on /msg
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class MSG extends OnlyTopCommand {
	private final String REPLY_COMMAND = Main.getInstance().getConfig().getString("Commands.Friends.SubCommands.MSG.ReplyCommand");
	private static MSG instance;

	/**
	 * Initials the command
	 *
	 * @param friendsAliasMsg The aliases for the command /msg
	 */
	public MSG(String[] friendsAliasMsg, String pPrefix) {
		super(friendsAliasMsg, Main.getInstance().getConfig().getString("Permissions.FriendPermission"), pPrefix);
		instance = this;
	}

	private static boolean playerExists(OnlinePAFPlayer pPlayer, PAFPlayer pPlayerQuery) {
		if (!pPlayerQuery.doesExist()) {
			pPlayer.sendMessage((Friends.getInstance().getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	@Override
	protected void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		send(pPlayer, args, 1);
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
		PAFPlayer writtenTo = PAFPlayerManager.getInstance().getPlayer(args[begin]);
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
		if (isOffline(pPlayer, pWrittenTo))
			return;
		FriendMessageEvent friendMessageEvent = new FriendOnlineMessageEvent((OnlinePAFPlayer) pWrittenTo, pPlayer, toMessageNoColor(args, n));
		ProxyServer.getInstance().getPluginManager().callEvent(friendMessageEvent);
		if (friendMessageEvent.isCancelled())
			return;
		sendMessage(toMessage(args, n), (OnlinePAFPlayer) pWrittenTo, pPlayer);
		pPlayer.setLastPlayerWroteFrom(pWrittenTo);
	}

	boolean messageGiven(OnlinePAFPlayer pPlayer, String[] args, int n) {
		if (args.length <= n) {
			pPlayer.sendMessage((getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.General.NoPlayerGiven")));
			if (Main.getInstance().getConfig().getBoolean("Commands.Friends.General.PrintOutHelpOnError"))
				pPlayer.sendMessage(
						(Main.getInstance().getMessages().getString("Friends.CommandUsage.MSG")));
			return false;
		}
		return true;
	}

	private boolean isOffline(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if (!pQueryPlayer.isOnline()) {
			pPlayer.sendMessage((getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return true;
		}
		return false;
	}

	private boolean allowsWriteTo(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if (pQueryPlayer.getSettingsWorth(2) == 1) {
			pPlayer.sendMessage((getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	private boolean isFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if (!pPlayer.isAFriendOf(pQueryPlayer)) {
			pPlayer.sendMessage((getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	private void sendMessage(String pContent, OnlinePAFPlayer pPlayer1, OnlinePAFPlayer pPlayer2) {
		sendMessage(pContent, pPlayer1, pPlayer2.getDisplayName(), pPlayer1.getDisplayName(), pPlayer2.getName());
		sendMessage(pContent, pPlayer2, pPlayer2.getDisplayName(), pPlayer1.getDisplayName(), pPlayer1.getName());
	}

	private void sendMessage(String pContent, OnlinePAFPlayer pReceiver, String pSenderDisplayName, String pReceiverName, String pSenderName) {
		String message = (getPrefix() + CONTENT_PATTERN.matcher(PLAYER_PATTERN.matcher(SENDER_NAME_PATTERN.matcher(Main.getInstance()
				.getMessages().getString("Friends.Command.MSG.SentMessage")).replaceAll(Matcher.quoteReplacement(pSenderDisplayName))).replaceAll(Matcher.quoteReplacement(pReceiverName))).replaceAll(Matcher.quoteReplacement(pContent)));
		JsonObject jsonObject = Main.getGson().fromJson("{\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + REPLY_COMMAND.replace("[PLAYER]", pSenderName) + "\"}}", JsonObject.class);
		jsonObject.addProperty("text", message);

		pReceiver.sendPacket(new Chat(jsonObject.toString()));
	}

	/**
	 * Returns a styled message
	 *
	 * @param args The Arguments The Main.main class
	 * @param n    At which argument the while loop should start
	 * @return Returns a styled message
	 */
	private String toMessage(String[] args, int n) {
		StringBuilder content;
		for (content = new StringBuilder(); n < args.length; n++) {
			content.append(Main.getInstance().getMessages().getString("Friends.Command.MSG.ColorOfMessage"));
			content.append(args[n]);
		}
		return content.toString();
	}

	/**
	 * Returns a styled message
	 *
	 * @param args The Arguments The Main.main class
	 * @param n    At which argument the while loop should start
	 * @return Returns a styled message
	 */
	private String toMessageNoColor(String[] args, int n) {
		StringBuilder content;
		for (content = new StringBuilder(); n < args.length; n++) {
			content.append(" ");
			content.append(args[n]);
		}
		return content.toString();
	}

	@EventHandler
	public void onTabComplete(TabCompleteEvent pEvent) {
		tabComplete(pEvent);
	}

	public static MSG getInstance() {
		return instance;
	}
}
