package de.simonsator.partyandfriends.api.party.abstractcommands;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PlayerParty;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * An abstract class for the party commands
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public abstract class PartySubCommand extends SubCommand {

	public PartySubCommand(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	protected boolean isInParty(ProxiedPlayer pPlayer, PlayerParty pParty) {
		if (pParty == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoParty")));
			return false;
		}
		return true;
	}
	

	protected boolean isPlayerGiven(ProxiedPlayer pPlayer, String[] args) {
		if (args.length == 0) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoPlayer")));
			return false;
		}
		return true;

	}

	public abstract boolean hasAccess(int pPermissionHeight);
}
