package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.api.OnlyTopCommand;
import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.message.FriendMessageEvent;
import de.simonsator.partyandfriends.api.events.message.FriendOnlineMessageEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.friends.settings.PMSetting;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.*;

/**
 * Will be executed on /msg
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class MSG extends OnlyTopCommand {
	private static MSG instance;
	private final String DEFAULT_MESSAGE_COLOR;
	private final boolean ALLOW_PLAYER_CHAT_FORMATTING;

	/**
	 * Initials the command
	 *
	 * @param friendsAliasMsg The aliases for the command /msg
	 */
	public MSG(String[] friendsAliasMsg, String pPrefix) {
		super(friendsAliasMsg, Main.getInstance().getGeneralConfig().getString("Commands.Friends.TopCommands.MSG.Permission"), pPrefix);
		instance = this;
		DEFAULT_MESSAGE_COLOR = SPACE_PATTERN.matcher(Main.getInstance().getMessages().getString("Friends.Command.MSG.ColorOfMessage")).replaceAll("");
		ALLOW_PLAYER_CHAT_FORMATTING = Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.TopCommands.MSG.AllowPlayersToUseChatFormatting");
	}

	private static boolean playerExists(OnlinePAFPlayer pPlayer, PAFPlayer pPlayerQuery) {
		if (!pPlayerQuery.doesExist() || pPlayer.equals(pPlayerQuery)) {
			pPlayer.sendMessage((Friends.getInstance().getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	public static MSG getInstance() {
		return instance;
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
		if (isOffline(pPlayer, pWrittenTo, args, n))
			return;
		FriendMessageEvent friendMessageEvent = new FriendOnlineMessageEvent((OnlinePAFPlayer) pWrittenTo, pPlayer, toMessageNoColor(args, n));
		BukkitBungeeAdapter.getInstance().callEvent(friendMessageEvent);
		if (friendMessageEvent.isCancelled())
			return;
		sendMessage(friendMessageEvent.getMessage(), (OnlinePAFPlayer) pWrittenTo, pPlayer);
		pPlayer.setLastPlayerWroteFrom(pWrittenTo);
	}

	boolean messageGiven(OnlinePAFPlayer pPlayer, String[] args, int n) {
		if (args.length <= n) {
			if (args.length != n) {
				pPlayer.sendMessage((getPrefix()
						+ Main.getInstance().getMessages().getString("Friends.General.NoPlayerGiven")));
			} else {
				pPlayer.sendMessage((getPrefix()
						+ Main.getInstance().getMessages().getString("Friends.Command.MSG.MessageMissing")));
			}
			if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.General.PrintOutHelpOnError"))
				pPlayer.sendMessage(
						(Main.getInstance().getMessages().getString("Friends.CommandUsage.MSG")));
			return false;
		}
		return true;
	}

	private boolean isOffline(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer, String[] args, int n) {
		if (!pQueryPlayer.isOnline()) {
			pPlayer.sendMessage((getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return true;
		}
		return false;
	}

	private boolean allowsWriteTo(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.PM.Enabled") && pQueryPlayer.getSettingsWorth(PMSetting.SETTINGS_ID) == PMSetting.PLAYER_DOES_NOT_RECEIVES_PM_STATE) {
			pPlayer.sendMessage((getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	private boolean isFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if (!pPlayer.isAFriendOf(pQueryPlayer) && !pPlayer.hasPermission(Main.getInstance().getGeneralConfig().getString("Commands.Friends.TopCommands.MSG.MSGNonFriendsPermission"))) {
			pPlayer.sendMessage((getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.MSG.CanNotWriteToHim")));
			return false;
		}
		return true;
	}

	private void sendMessage(String pContent, OnlinePAFPlayer pPlayer1, OnlinePAFPlayer pPlayer2) {
		TextComponent formattedMessage = formatMessage(pContent, pPlayer2.getDisplayName(), pPlayer1.getDisplayName());
		sendMessage(formattedMessage, pPlayer1, pPlayer2.getName());
		sendMessage(formattedMessage, pPlayer2, pPlayer1.getName());
	}

	private TextComponent formatMessage(String pContent, String pSenderDisplayName, String pReceiverName) {
		if (ALLOW_PLAYER_CHAT_FORMATTING)
			return new TextComponent(TextComponent.fromLegacyText(getPrefix() + CONTENT_PATTERN.matcher(PLAYER_PATTERN.matcher(SENDER_NAME_PATTERN.matcher(Main.getInstance()
							.getMessages().getString("Friends.Command.MSG.SentMessage")).replaceAll(Matcher.quoteReplacement(pSenderDisplayName))).
					replaceAll(Matcher.quoteReplacement(pReceiverName))).replaceAll(Matcher.quoteReplacement(DEFAULT_MESSAGE_COLOR + ChatColor.translateAlternateColorCodes('&', pContent) + " §r"))));
		return new TextComponent(getPrefix() + CONTENT_PATTERN.matcher(PLAYER_PATTERN.matcher(SENDER_NAME_PATTERN.matcher(Main.getInstance()
						.getMessages().getString("Friends.Command.MSG.SentMessage")).replaceAll(Matcher.quoteReplacement(pSenderDisplayName))).
				replaceAll(Matcher.quoteReplacement(pReceiverName))).replaceAll(Matcher.quoteReplacement(SPACE_PATTERN.matcher(pContent).replaceAll(" " + DEFAULT_MESSAGE_COLOR))));
	}


	private void sendMessage(TextComponent pMessage, OnlinePAFPlayer pReceiver, String pSenderName) {
		pReceiver.sendPacket(pMessage);
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
}