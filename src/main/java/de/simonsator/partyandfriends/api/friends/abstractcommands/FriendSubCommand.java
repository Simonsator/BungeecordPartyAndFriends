package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

public abstract class FriendSubCommand extends SubCommand implements Comparable<SubCommand> {

	protected FriendSubCommand(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, new TextComponent(pHelp));
	}

	protected boolean isPlayerGiven(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length < 2) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
			pPlayer.sendMessage(new TextComponent(HELP));
			return false;
		}
		return true;
	}

	protected boolean isAFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer) {
		if (!pPlayer.isAFriendOf(pGivenPlayer)) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PLAYER_PATTERN.matcher(getInstance()
					.getMessagesYml().getString("Friends.General.PlayerIsOffline")).replaceAll(Matcher.quoteReplacement(pGivenPlayer.getName()))));
			pPlayer.sendMessage(new TextComponent(HELP));
			return false;
		}
		return true;
	}


}
