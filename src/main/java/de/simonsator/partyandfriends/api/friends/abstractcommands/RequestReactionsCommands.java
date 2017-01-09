package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

public abstract class RequestReactionsCommands extends FriendSubCommand {
	private Matcher playerMatcher = PLAYER_PATTERN.matcher(Main.getInstance().getMessagesYml()
			.getString("Friends.Command.Accept.ErrorNoFriendShipInvitation"));

	protected RequestReactionsCommands(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	protected boolean hasNoRequest(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if ((!pPlayer.hasRequestFrom(pQueryPlayer))) {
			sendError(pPlayer, new TextComponent(Friends.getInstance().getPrefix() + playerMatcher.replaceFirst(pQueryPlayer.getName())));
			return true;
		}
		return false;
	}

}
