package de.simonsator.partyandfriends.api.party.abstractcommands;

import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class LeaderNeededCommand extends PartySubCommand {

	public LeaderNeededCommand(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return pPermissionHeight == PartyAPI.LEADER_PERMISSION_HEIGHT;
	}

	protected boolean checkIsInParty(ProxiedPlayer pPlayer, ProxiedPlayer pSearched, PlayerParty pParty,
			String[] args) {
		if (pSearched == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
					.getMessagesYml().getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty")
					.replace("[PLAYER]", args[0])));
			return false;
		}
		if (!pParty.getPlayers().contains(pSearched)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
					.getMessagesYml().getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty")
					.replace("[PLAYER]", args[0])));
			return false;
		}
		if (pSearched.equals(pPlayer)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Leader.SenderEqualsGivenPlayer")));
			return false;
		}
		return true;
	}

	protected boolean standartCheck(ProxiedPlayer pPlayer, PlayerParty pParty, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return false;
		if (!isInParty(pPlayer, pParty))
			return false;

		if (!pParty.isLeader(pPlayer)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNotPartyLeader")));
			return false;
		}
		return true;
	}

}
