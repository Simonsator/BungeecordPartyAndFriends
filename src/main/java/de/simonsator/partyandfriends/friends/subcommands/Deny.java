/**
 * The command deny
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
 * The command deny
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Deny extends RequestReactionsCommands {

	public Deny(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		PAFPlayer playerQuery = getPlayerManager().getPlayer(args[1]);
		if (hasNoRequest(pPlayer, playerQuery))
			return;
		pPlayer.denyRequest(playerQuery);
		pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PLAYER_PATTERN.matcher(getInstance()
				.getMessagesYml().getString("Friends.Command.Deny.HasDenied")).replaceAll(Matcher.quoteReplacement(args[1]))));
	}

}
