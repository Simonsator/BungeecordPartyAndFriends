package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.command.FriendshipCommandEvent;
import de.simonsator.partyandfriends.api.friends.abstractcommands.RequestReactionsCommands;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The command accept
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Accept extends RequestReactionsCommands {


	public Accept(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		PAFPlayer playerQuery = PAFPlayerManager.getInstance().getPlayer(args[1]);
		if (!playerQuery.doesExist()) {
			sendError(pPlayer, new TextComponent(TextComponent.fromLegacyText(Friends.getInstance().getPrefix() + PLAYER_MATCHER.replaceFirst(args[1]))));
			return;
		}
		if (hasNoRequest(pPlayer, playerQuery))
			return;
		FriendshipCommandEvent event = new FriendshipCommandEvent(pPlayer, playerQuery, args, this);
		BukkitBungeeAdapter.getInstance().callEvent(event);
		if (event.isCancelled())
			return;
		pPlayer.addFriend(playerQuery);
		pPlayer.denyRequest(playerQuery);
		pPlayer.sendMessage(PREFIX + PLAYER_PATTERN.matcher(getInstance()
				.getMessages().getString("Friends.Command.Accept.NowFriends")).replaceAll(Matcher.quoteReplacement(playerQuery.getDisplayName())));
		if (!playerQuery.isOnline() || !Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Accept.SendTextIsNowOnline"))
			return;
		OnlinePAFPlayer friend = (OnlinePAFPlayer) playerQuery;
		friend.sendMessage(PREFIX + PLAYER_PATTERN.matcher(getInstance().getMessages()
				.getString("Friends.Command.Accept.NowFriends")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName())));
		friend.sendMessage(PREFIX + PLAYER_PATTERN.matcher(getInstance().getMessages()
				.getString("Friends.General.PlayerIsNowOnline")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName())));
		pPlayer.sendMessage(
				PREFIX + PLAYER_PATTERN.matcher(getInstance().getMessages()
						.getString("Friends.General.PlayerIsNowOnline")).replaceAll(Matcher.quoteReplacement(friend.getDisplayName())));
	}

}
