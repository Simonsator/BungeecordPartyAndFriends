package de.simonsator.partyandfriends.api.party.abstractcommands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;


/**
 * An abstract class for the party commands
 *
 * @author Simonsator
 * @version 1.0.0
 */
public abstract class PartySubCommand extends SubCommand {

	protected PartySubCommand(List<String> pCommands, int pPriority, String pHelpText, String pPermission) {
		super(pCommands, pPriority, pHelpText, PartyCommand.getInstance().getPrefix(), pPermission);
	}

	/**
	 * @param pCommands The names of the subcommand (The first one in the array is the main name of the subcommand. The others are its aliases)
	 * @param pPriority The priority of the subcommand. The lower the priority the earlier the subcommand will be listed in the help menu.
	 * @param pHelp     The help message of the subcommand. This is displayed in the help menu and when you execute {@link #sendError(OnlinePAFPlayer pPlayer, TextComponent pMessage)}
	 */
	public PartySubCommand(String[] pCommands, int pPriority, TextComponent pHelp) {
		super(pCommands, pPriority, pHelp, PartyCommand.getInstance().getPrefix());
	}

	/**
	 * @param pCommands The names of the subcommand (The first one in the array is the main name of the subcommand. The others are its aliases)
	 * @param pPriority The priority of the subcommand. The lower the priority the earlier the subcommand will be listed in the help menu.
	 * @param pHelp     The help message of the subcommand. This is displayed in the help menu and when you execute {@link #sendError(OnlinePAFPlayer pPlayer, TextComponent pMessage)}
	 */
	public PartySubCommand(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp, PartyCommand.getInstance().getPrefix());
	}

	protected boolean isInParty(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (pParty == null) {
			pPlayer.sendMessage(PartyCommand.getInstance().getPrefix()
					+ Main.getInstance().getMessages().getString("Party.Command.General.ErrorNoParty"));
			return false;
		}
		return true;
	}

	public boolean hasAccess(int pPermissionHeight) {
		return true;
	}

	public boolean hasAccess(PlayerParty pPlayer, int pPermissionHeight) {
		return hasAccess(pPermissionHeight);
	}

	protected boolean isPlayerGiven(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length == 0) {
			pPlayer.sendMessage((PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.General.ErrorNoPlayer")));
			return false;
		}
		return true;
	}

	@Override
	public void sendError(OnlinePAFPlayer pPlayer, TextComponent pMessage) {
		pPlayer.sendMessage(pMessage);
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Party.General.PrintOutHelpOnError"))
			printOutHelp(pPlayer, PartyCommand.getInstance().getName());
	}

}