package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.utilities.CompilePatter.PLAYERPATTERN;

public abstract class RequestReactionsCommands extends FriendSubCommand {

	public RequestReactionsCommands(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	protected boolean hasNoRequest(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if ((!pPlayer.hasRequestFrom(pQueryPlayer))) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PLAYERPATTERN.matcher(getInstance()
					.getMessagesYml().getString("Friends.Command.Accept.ErrorNoFriendShipInvitation")).replaceAll(Matcher.quoteReplacement(pQueryPlayer.getName()))));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return true;
		}
		return false;
	}

}
