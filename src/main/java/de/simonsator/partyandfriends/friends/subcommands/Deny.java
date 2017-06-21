package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.RequestReactionsCommands;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;

import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The command deny
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Deny extends RequestReactionsCommands {

	public Deny(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		PAFPlayer playerQuery = PAFPlayerManager.getInstance().getPlayer(args[1]);
		if (hasNoRequest(pPlayer, playerQuery))
			return;
		pPlayer.denyRequest(playerQuery);
		pPlayer.sendMessage((PREFIX + PLAYER_PATTERN.matcher(getInstance()
				.getMessages().getString("Friends.Command.Deny.HasDenied")).replaceAll(Matcher.quoteReplacement(args[1]))));
	}

}
