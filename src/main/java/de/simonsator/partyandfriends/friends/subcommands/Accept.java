/**
 * The command accept
 *
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.RequestReactionsCommands;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.main.Main.getPlayerManager;
import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

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
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		PAFPlayer playerQuery = getPlayerManager().getPlayer(args[1]);
		if (hasNoRequest(pPlayer, playerQuery))
			return;
		pPlayer.addFriend(playerQuery);
		pPlayer.denyRequest(playerQuery);
		pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PLAYER_PATTERN.matcher(getInstance()
				.getMessagesYml().getString("Friends.Command.Accept.NowFriends")).replaceAll(Matcher.quoteReplacement(args[1]))));
		if (!playerQuery.isOnline())
			return;
		OnlinePAFPlayer friend = (OnlinePAFPlayer) playerQuery;
		friend.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PLAYER_PATTERN.matcher(getInstance().getMessagesYml()
				.getString("Friends.Command.Accept.NowFriends")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
		friend.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PLAYER_PATTERN.matcher(getInstance().getMessagesYml()
				.getString("Friends.General.PlayerIsNowOnline")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
		pPlayer.sendMessage(
				new TextComponent(getInstance().getFriendsPrefix() + PLAYER_PATTERN.matcher(getInstance().getMessagesYml()
						.getString("Friends.General.PlayerIsNowOnline")).replaceAll(Matcher.quoteReplacement(friend.getDisplayName()))));
	}

}
