package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.command.FriendshipCommandEvent;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.friends.settings.FriendRequestSetting;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The command add
 *
 * @author Simonsator
 * @version 1.0.1
 */
public class Add extends FriendSubCommand {
	private final String FRIEND_ACCEPT_COMMAND_NAME;
	private final BaseComponent[] CLICK_HERE_MESSAGE = new TextComponent(TextComponent.fromLegacyText(Main.getInstance().getMessages().getString("Friends.Command.Add.ClickHere"))).getExtra().toArray(new BaseComponent[0]);

	public Add(List<String> pCommands, int pPriority, String pHelp, String pAcceptCommandName, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
		FRIEND_ACCEPT_COMMAND_NAME = "/" + Friends.getInstance().getName() + " " + pAcceptCommandName + " ";
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		if (givenPlayerEqualsSender(pPlayer, args[1]))
			return;
		PAFPlayer playerQuery = PAFPlayerManager.getInstance().getPlayer(args[1]);
		if (!doesPlayerExist(pPlayer, playerQuery))
			return;
		if (isAFriendOf(pPlayer, playerQuery))
			return;
		if (!hasNoRequestFrom(pPlayer, playerQuery))
			return;
		if (pPlayer.hasRequestFrom(playerQuery)) {
			pPlayer.sendMessage(
					(PREFIX + PLAYER_PATTERN.matcher(Main.getInstance().getMessages()
							.getString("Friends.Command.Add.FriendRequestFromReceiver")).replaceAll(Matcher.quoteReplacement(args[1]))));
			TextComponent packet = new TextComponent(TextComponent.fromLegacyText(PREFIX
					+ PLAYER_PATTERN.matcher(Main.getInstance().getMessages().getString("Friends.Command.Add.HowToAccept")).replaceAll(Matcher.quoteReplacement(args[1]))));
			packet.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, FRIEND_ACCEPT_COMMAND_NAME + args[1]));
			packet.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, CLICK_HERE_MESSAGE));
			pPlayer.sendPacket(packet);
			return;
		}
		if (!allowsFriendRequests(pPlayer, playerQuery))
			return;
		sendFriendRequest(pPlayer, playerQuery, args);
		if (Main.getInstance().getGeneralConfig().getInt("Commands.Friends.SubCommands.Add.FriendRequestTimeout") > 0) {
			BukkitBungeeAdapter.getInstance().schedule(Main.getInstance(), () -> {
				if (playerQuery.getRequests().contains(pPlayer)) {
					playerQuery.denyRequest(pPlayer);
					playerQuery.sendMessage((PREFIX + PLAYER_PATTERN.matcher(Main.getInstance()
							.getMessages().getString("Friends.Command.Add.FriendRequestTimedOut")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
				}
			}, Main.getInstance().getGeneralConfig().getInt("Commands.Friends.SubCommands.Add.FriendRequestTimeout"));
		}
	}

	private void sendFriendRequest(OnlinePAFPlayer pSender, PAFPlayer pReceiver, String[] args) {
		FriendshipCommandEvent event = new FriendshipCommandEvent(pSender, pReceiver, args, this);
		BukkitBungeeAdapter.getInstance().callEvent(event);
		if (event.isCancelled())
			return;
		pReceiver.sendFriendRequest(pSender);
		sendRequest(pSender, pReceiver);
		pSender.sendMessage((PREFIX + PLAYER_PATTERN.matcher(Main.getInstance()
				.getMessages().getString("Friends.Command.Add.SentAFriendRequest")).replaceAll(Matcher.quoteReplacement(pReceiver.getDisplayName()))));
	}

	private void sendRequest(OnlinePAFPlayer pPlayer, PAFPlayer pPlayerQuery) {
		pPlayerQuery.sendMessage((PREFIX
				+ PLAYER_PATTERN.matcher(Main.getInstance().getMessages().getString("Friends.Command.Add.FriendRequestReceived")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
		TextComponent packet = new TextComponent(TextComponent.fromLegacyText(PREFIX
				+ PLAYER_PATTERN.matcher(Main.getInstance().getMessages().getString("Friends.Command.Add.HowToAccept")).replaceAll(Matcher.quoteReplacement(pPlayer.getName()))));
		packet.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, FRIEND_ACCEPT_COMMAND_NAME + pPlayer.getName()));
		packet.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, CLICK_HERE_MESSAGE));
		pPlayerQuery.sendPacket(packet);
	}

	private boolean hasNoRequestFrom(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if (pQueryPlayer.hasRequestFrom(pPlayer)) {
			sendError(pPlayer, new TextComponent(TextComponent.fromLegacyText(PREFIX + Main.getInstance().getMessages().getString("Friends.Command.Accept.ErrorAlreadySend").replace("[PLAYER]", pQueryPlayer.getDisplayName()))));
			return false;
		}
		return true;
	}

	@Override
	protected boolean isAFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer) {
		if (pPlayer.isAFriendOf(pGivenPlayer)) {
			sendError(pPlayer, (new TextComponent(TextComponent.fromLegacyText(PREFIX + Main.getInstance().getMessages().getString("Friends.Command.Add.AlreadyFriends").replace("[PLAYER]", pGivenPlayer.getDisplayName())))));
			return true;
		}
		return false;
	}

	private boolean givenPlayerEqualsSender(OnlinePAFPlayer pPlayer, String pGivenPlayer) {
		if (pPlayer.getName().equalsIgnoreCase(pGivenPlayer)) {
			sendError(pPlayer, "Friends.Command.Accept.ErrorSenderEqualsReceiver");
			return true;
		}
		return false;
	}

	private boolean doesPlayerExist(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer) {
		if (!pGivenPlayer.doesExist()) {
			sendError(pPlayer, "Friends.General.DoesNotExist");
			return false;
		}
		return true;
	}

	private boolean allowsFriendRequests(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer) {
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Enabled") && pGivenPlayer.getSettingsWorth(FriendRequestSetting.SETTINGS_ID) == FriendRequestSetting.DOES_NOT_RECEIVE_FRIEND_REQUESTS_STATE) {
			sendError(pPlayer, new TextComponent(TextComponent.fromLegacyText(PREFIX + PLAYER_PATTERN.matcher(Main.getInstance().getMessages().getString("Friends.Command.Add.CanNotSendThisPlayer")).replaceFirst(pGivenPlayer.getName()))));
			return false;
		}
		return true;
	}
}