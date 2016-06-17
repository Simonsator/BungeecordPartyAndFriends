package de.simonsator.partyandfriends.api.party.abstractcommands;

import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.party.playerpartys.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.utilities.CompilePatter.PLAYERPATTERN;

public abstract class LeaderNeededCommand extends PartySubCommand {

	public LeaderNeededCommand(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return pPermissionHeight == PartyAPI.LEADER_PERMISSION_HEIGHT;
	}

	protected boolean checkIsInParty(OnlinePAFPlayer pPlayer, PAFPlayer pSearched, PlayerParty pParty, String[] args) {
		if (!pSearched.isOnline()) {
			pPlayer.sendMessage(new TextComponent(getInstance().getPartyPrefix() + PLAYERPATTERN
					.matcher(getInstance().getMessagesYml()
							.getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty"))
					.replaceAll(Matcher.quoteReplacement(args[0]))));
			return false;
		}
		if (!pParty.isInParty((OnlinePAFPlayer) pSearched)) {
			pPlayer.sendMessage(new TextComponent(getInstance().getPartyPrefix() + PLAYERPATTERN
					.matcher(getInstance().getMessagesYml()
							.getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty"))
					.replaceAll(Matcher.quoteReplacement(args[0]))));
			return false;
		}
		if (pSearched.equals(pPlayer)) {
			pPlayer.sendMessage(new TextComponent(getInstance().getPartyPrefix()
					+ getInstance().getMessagesYml().getString("Party.Command.Leader.SenderEqualsGivenPlayer")));
			return false;
		}
		return true;
	}

	protected boolean standartCheck(OnlinePAFPlayer pPlayer, PlayerParty pParty, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return false;
		if (!isInParty(pPlayer, pParty))
			return false;

		if (!pParty.isLeader(pPlayer)) {
			pPlayer.sendMessage(new TextComponent(getInstance().getPartyPrefix()
					+ getInstance().getMessagesYml().getString("Party.Command.General.ErrorNotPartyLeader")));
			return false;
		}
		return true;
	}

}
