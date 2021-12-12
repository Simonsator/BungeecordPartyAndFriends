package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

public abstract class RequestReactionsCommands extends FriendSubCommand {
	protected final Matcher PLAYER_MATCHER = PLAYER_PATTERN.matcher(Main.getInstance().getMessages()
			.getString("Friends.Command.Accept.ErrorNoFriendShipInvitation"));

	protected RequestReactionsCommands(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	protected RequestReactionsCommands(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
	}

	protected boolean hasNoRequest(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if ((!pPlayer.hasRequestFrom(pQueryPlayer))) {
			sendError(pPlayer, new TextComponent(TextComponent.fromLegacyText(Friends.getInstance().getPrefix() + PLAYER_MATCHER.replaceFirst(pQueryPlayer.getName()))));
			return true;
		}
		return false;
	}

}
