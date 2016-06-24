package de.simonsator.partyandfriends.api.friends.abstractcommands;

import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.utilities.CompilePatter.PLAYERPATTERN;

public abstract class FriendSubCommand extends SubCommand implements Comparable<SubCommand> {

	public FriendSubCommand(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	protected boolean isPlayerGiven(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length < 2) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return false;
		}
		return true;
	}

	protected boolean isAFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer) {
		if (!pPlayer.isAFriendOf(pGivenPlayer)) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PLAYERPATTERN.matcher(getInstance()
					.getMessagesYml().getString("Friends.General.PlayerIsOffline")).replaceAll(Matcher.quoteReplacement(pGivenPlayer.getName()))));
			pPlayer.sendMessage(new TextComponent(getHelp()));
			return false;
		}
		return true;
	}


}
