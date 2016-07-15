package de.simonsator.partyandfriends.api.party.abstractcommands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/**
 * An abstract class for the party commands
 *
 * @author Simonsator
 * @version 1.0.0
 */
public abstract class PartySubCommand extends SubCommand {

	protected PartySubCommand(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, new TextComponent(pHelpText));
	}

	protected boolean isInParty(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (pParty == null) {
			pPlayer.sendMessage(new TextComponent(getInstance().getPartyPrefix()
					+ getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoParty")));
			return false;
		}
		return true;
	}

	public abstract boolean hasAccess(int pPermissionHeight);

	protected boolean isPlayerGiven(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length == 0) {
			pPlayer.sendMessage(new TextComponent(getInstance().getPartyPrefix()
					+ getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoPlayer")));
			return false;
		}
		return true;

	}

}
